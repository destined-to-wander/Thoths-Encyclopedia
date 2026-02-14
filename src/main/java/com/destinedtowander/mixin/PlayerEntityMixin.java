package com.destinedtowander.mixin;

import com.destinedtowander.common.cca.KnapsackComponent;
import com.destinedtowander.common.index.ModComponents;
import com.destinedtowander.common.items.HalberdItem;
import com.destinedtowander.common.items.RapierItem;
import com.destinedtowander.client.particle.ModParticles;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import com.llamalad7.mixinextras.sugar.ref.LocalFloatRef;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.function.Predicate;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public PlayerAbilities abilities;

    @Unique
    private boolean sweepAttack;

    public void manualKnockback(float strength, Entity target, Vec3d originalMovement){
        if (target instanceof LivingEntity livingEntity) {
            strength *= 1.0 - livingEntity.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        }

        Vec3d knockback = new Vec3d(
                MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)),
                0.0,
                (-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)))).normalize().multiply(strength);
        target.setVelocity(
                originalMovement.x / 2.0 - knockback.x,
                target.isOnGround() ? Math.min(0.4, originalMovement.y / 2.0 + strength) : originalMovement.y,
                originalMovement.z / 2.0 - knockback.z
        );
        target.velocityModified = true;
    }

    public void spawnLungeAttackParticles() {
        double d = -MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
        double e = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
        if (this.getWorld() instanceof ServerWorld) {
            ((ServerWorld)this.getWorld()).spawnParticles(
                    ModParticles.LUNGE_ATTACK,
                    this.getX() + d,
                    this.getBodyY(0.5),
                    this.getZ() + e,
                    0,
                    d,
                    0.0,
                    e,
                    0.0);
        }
    }

    @Inject(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/enchantment/EnchantmentHelper;getKnockback(Lnet/minecraft/entity/LivingEntity;)I"
        )
    )
    private void modifiedSprintAttack(Entity target, CallbackInfo ci, @Local(ordinal = 0) LocalFloatRef baseAtkDmg, @Local(ordinal = 0) boolean bl, @Local(ordinal = 1) LocalBooleanRef sprintAttack) {
        PlayerEntity player = ((PlayerEntity)(Object)this);
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);

        if (this.isSprinting() && bl){
            sprintAttack.set(true);
            if ((itemStack.getItem() instanceof RapierItem)){
                this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, this.getSoundCategory(), 1.0F, 1.0F);
                baseAtkDmg.set(baseAtkDmg.get() * 1.3F);
                player.addCritParticles(target);
            } else if ((itemStack.getItem() instanceof HalberdItem)) {
                double maxDistance = 3.0;
                List<LivingEntity> entityTargets  = player.getWorld().getNonSpectatingEntities(
                        LivingEntity.class,
                        player.getBoundingBox().expand(maxDistance * 2, 0.25, maxDistance * 2)
                );

                Vec3d forward = player.getRotationVec(1.0F).normalize();
                Vec3d origin = player.getPos();
                double lineTolerance = 0.1;

                for (LivingEntity livingEntity : entityTargets) {
                    if (livingEntity == player ||
                            livingEntity == target ||
                            player.isTeammate(livingEntity) ||
                            (livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker())
                            || player.squaredDistanceTo(livingEntity) > maxDistance * maxDistance) continue;

                    Vec3d toEntity = livingEntity.getPos().subtract(origin);
                    double distanceAlongForward = toEntity.dotProduct(forward);

                    if (distanceAlongForward <= 0) continue;

                    Vec3d closestPoint = forward.multiply(distanceAlongForward);
                    double perpendicularDistance = toEntity.subtract(closestPoint).length();
                    if (perpendicularDistance > Math.max(lineTolerance, livingEntity.getHeight()/2)) continue;
                    livingEntity.damage(player.getDamageSources().playerAttack(player), baseAtkDmg.get());
                }

                player.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                this.spawnLungeAttackParticles();
            }
        }
    }

    @WrapOperation(
        method = "attack",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;isSprinting()Z",
            ordinal = 0
        )
    )
    private boolean disableVanillaSprintAttack(PlayerEntity thisEntity, Operation<Boolean> original) {
        ItemStack itemStack = thisEntity.getStackInHand(Hand.MAIN_HAND);
        return !(itemStack.getItem() instanceof HalberdItem) && !(itemStack.getItem() instanceof RapierItem) && original.call(thisEntity);
    }

    @ModifyVariable(
            method = "attack",
            at = @At(
                    value = "STORE"
            ),
            index = 8
    )
    private boolean disableJumpAttack(boolean original) {
        ItemStack itemStack = ((PlayerEntity)(Object)this).getStackInHand(Hand.MAIN_HAND);
        return original && !(itemStack.getItem() instanceof RapierItem) && !(itemStack.getItem() instanceof HalberdItem);
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 19,
                    //target = "Lnet/minecraft/entity/player/PlayerEntity;onAttacking(Lnet/minecraft/entity/Entity;)V",
                    shift = At.Shift.BEFORE
            )
    )
    private void modifyDefaultKnockback(Entity target, CallbackInfo ci, @Local(ordinal = 1) boolean sprintAttack, @Local(ordinal = 0) LocalRef<Vec3d> originalMovement) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof HalberdItem) {
            float strength = (float) player.getPos().distanceTo(target.getPos()) * 0.25f;
            manualKnockback(strength, target, originalMovement.get());
        }
        if (player.getStackInHand(Hand.MAIN_HAND).getItem() instanceof RapierItem) {
            if (sprintAttack) {
                manualKnockback(0.35f, target, originalMovement.get());
            } else manualKnockback(0.2f, target, originalMovement.get());
        }
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 14,
                    shift = At.Shift.BEFORE
            )
    )
    private void replaceSweepWithLunge(Entity target, CallbackInfo ci, @Local(ordinal = 0) float baseAtkDmg, @Local(ordinal = 3) LocalBooleanRef isSweep) {
        sweepAttack = false;
        if (!isSweep.get()) return;
        PlayerEntity player = (PlayerEntity)(Object)this;
        ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
        if (!(itemStack.getItem() instanceof RapierItem)) return;
        sweepAttack = true;
        isSweep.set(false);

        float l = 1.0F + EnchantmentHelper.getSweepingMultiplier(player) * baseAtkDmg;

        List<LivingEntity> entityTargets  = player.getWorld().getNonSpectatingEntities(
                LivingEntity.class,
                player.getBoundingBox().expand(1.0, 0.25, 1.0)
        );

        Vec3d forward = player.getRotationVec(1.0F).normalize();
        Vec3d origin = player.getPos();

        double maxDistance = 3.0;
        double cosHalfAngle = Math.cos(Math.toRadians(25));

        for (LivingEntity livingEntity : entityTargets) {
            if (livingEntity == player ||
                livingEntity == target ||
                player.isTeammate(livingEntity) ||
                (livingEntity instanceof ArmorStandEntity && ((ArmorStandEntity)livingEntity).isMarker())
                || player.squaredDistanceTo(livingEntity) > maxDistance * maxDistance) continue;

            Vec3d toEntity = livingEntity.getPos().subtract(origin);
            Vec3d dir = toEntity.normalize();

            // Dot product = how aligned with forward direction
            if (dir.dotProduct(forward) < cosHalfAngle) continue;
            livingEntity.damage(player.getDamageSources().playerAttack(player), l);
        }

        player.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
        this.spawnLungeAttackParticles();
    }

    @Inject(
            method = "attack",
            at = @At(
                    value = "JUMP",
                    opcode = Opcodes.IFEQ,
                    ordinal = 22,
                    shift = At.Shift.BEFORE
            )
    )
    private void replaceSweepValue(Entity target, CallbackInfo ci, @Local(ordinal = 3) LocalBooleanRef isSweep) {
        isSweep.set(sweepAttack);
    }

    @Inject(method = "takeShieldHit", at = @At("HEAD"))
    private void halberdDisableShield(LivingEntity attacker, CallbackInfo ci) {
        if (attacker.getMainHandStack().getItem() instanceof HalberdItem) {
            ((PlayerEntity)(Object)this).disableShield(true);
        }
    }

    @Inject(method = "dropInventory", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"
    ))
    private void dropKnapsack(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        SimpleInventory knapsack = KnapsackComponent.getInventory(player);
        for (int i = 0; i < knapsack.size(); i++) {
            ItemStack itemStack = knapsack.stacks.get(i);
            if (!itemStack.isEmpty()) {
                player.dropItem(itemStack, true, false);
                knapsack.stacks.set(i, ItemStack.EMPTY);
            }
        }
    }

    @Inject(method = "getProjectileType", at = @At("HEAD"))
    public void getProjectileType(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (FabricLoader.getInstance().isModLoaded("enchancement")) return;

        if (!(stack.getItem() instanceof RangedWeaponItem)) {
            cir.setReturnValue(ItemStack.EMPTY);
            cir.cancel();
        } else {
            Predicate<ItemStack> predicate = ((RangedWeaponItem)stack.getItem()).getHeldProjectiles();
            ItemStack itemStack = RangedWeaponItem.getHeldProjectile((PlayerEntity)(Object)this, predicate);
            if (!itemStack.isEmpty()) {
                cir.setReturnValue(itemStack);
            } else {
                predicate = ((RangedWeaponItem)stack.getItem()).getProjectiles();

                for (int i = 0; i < this.inventory.size(); i++) {
                    ItemStack itemStack2 = this.inventory.getStack(i);
                    if (predicate.test(itemStack2)) {
                        cir.setReturnValue(itemStack2);
                        cir.cancel();
                        return;
                    }
                }

                ItemStack returnVal =  this.abilities.creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
                cir.setReturnValue(returnVal);
                cir.cancel();
            }
        }
    }

    @ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    public float airSpeed(float original) {
        if(this.getComponent(ModComponents.WANDERER_COMPONENT).hasSetBonus()) return original * 2.0F;
        return original;
    }
}
