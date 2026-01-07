package com.dest1ny.mixin;

import com.dest1ny.ThothsEncyclopedia;
import com.dest1ny.items.HalberdItem;
import com.dest1ny.items.RapierItem;
import com.dest1ny.client.particle.ModParticles;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityGroup;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.SwordItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity {

    public PlayerEntityMixin(EntityType<? extends LivingEntity> type, World world) {
        super(type, world);
    }

    @Shadow
    public PlayerInventory inventory;

    @Shadow
    public PlayerAbilities abilities;
    /*

    @Inject(method = "tick", at = @At("HEAD"))
    private void attackReachChange(CallbackInfo ci){
        PlayerEntity player = (PlayerEntity)(Object)this;
        ItemStack stackInHand = player.getStackInHand(Hand.MAIN_HAND);
        if (stackInHand.getItem() instanceof HalberdItem){
            continue;
        }
    }

     */

    public void spawnLungeAttackParticles() {
        double d = -MathHelper.sin(this.getYaw() * ((float)Math.PI / 180));
        double e = MathHelper.cos(this.getYaw() * ((float)Math.PI / 180));
        if (this.getWorld() instanceof ServerWorld) {
            ((ServerWorld)this.getWorld()).spawnParticles(
                    ModParticles.LUNGE_ATTACK,
                    //ParticleTypes.SWEEP_ATTACK,
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
            method = "Lnet/minecraft/entity/player/PlayerEntity;attack(Lnet/minecraft/entity/Entity;)V",
            at = @At(value = "HEAD"),
            cancellable = true
    )
    private void attackOverride(Entity target, CallbackInfo ci){
        PlayerEntity player = (PlayerEntity)(Object)this;
        ci.cancel();

        if (target.isAttackable() && !target.handleAttack(player)) {
                float baseAtkDmg = (float)player.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                float enchAtkDmg;
                if (target instanceof LivingEntity) {
                    int impalingLevel = EnchantmentHelper.getLevel(Enchantments.IMPALING,player.getMainHandStack());
                    enchAtkDmg = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), ((LivingEntity)target).getGroup());
                    enchAtkDmg -= Enchantments.IMPALING.getAttackDamage(impalingLevel, ((LivingEntity)target).getGroup());
                    if (impalingLevel > 0 && target.isWet()){
                        enchAtkDmg += impalingLevel;
                    }
                } else {
                    enchAtkDmg = EnchantmentHelper.getAttackDamage(player.getMainHandStack(), EntityGroup.DEFAULT);
                }

                float atkCdProgress = player.getAttackCooldownProgress(0.5F);
                baseAtkDmg *= 0.2F + atkCdProgress * atkCdProgress * 0.8F;
                enchAtkDmg *= atkCdProgress;
                player.resetLastAttackedTicks();

                //if (baseAtkDmg > 0.0F || enchAtkDmg > 0.0F) {
                int swordType = -1;
                ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
                if (itemStack.getItem() instanceof RapierItem){
                    swordType = 1;
                } else if (itemStack.getItem() instanceof SwordItem) {
                    swordType = 0;
                }

                boolean attackOffCooldown = atkCdProgress > 0.9F;
                boolean sprintAttack = false;
                int knockbackEffect = 0;
                knockbackEffect += EnchantmentHelper.getKnockback(player);
                if (player.isSprinting() && attackOffCooldown) {
                    player.getWorld().playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, player.getSoundCategory(), 1.0F, 1.0F);
                    sprintAttack = true;
                    if (swordType == 1){
                        baseAtkDmg *= 1.5F;
                    } else {
                        ++knockbackEffect;
                    }
                }

                boolean jumpAttack = attackOffCooldown && player.fallDistance > 0.0F && !player.isOnGround() && !player.isClimbing() && !player.isTouchingWater() && !player.hasStatusEffect(StatusEffects.BLINDNESS) && !player.hasVehicle() && target instanceof LivingEntity;
                jumpAttack = jumpAttack && !player.isSprinting() && swordType != 1;
                if (jumpAttack) {
                    baseAtkDmg *= 1.5F;
                }

                baseAtkDmg += enchAtkDmg;
                float targetHealth = 0.0F;
                boolean bl5 = false;
                int k = EnchantmentHelper.getFireAspect(player);
                if (target instanceof LivingEntity) {
                    targetHealth = ((LivingEntity)target).getHealth();
                    if (k > 0 && !target.isOnFire()) {
                        bl5 = true;
                        target.setOnFireFor(1);
                    }
                }

                Vec3d vec3d = target.getVelocity();
                boolean wasDamagedByPlayer = target.damage(player.getDamageSources().playerAttack(player), baseAtkDmg);
                if (wasDamagedByPlayer) {
                    if (knockbackEffect > 0) {
                        float knockbackTaken = knockbackEffect;
                        float deltaX = MathHelper.sin(player.getYaw() * 0.017453292F);
                        float deltaY = -MathHelper.cos(player.getYaw() * 0.017453292F);
                        if (swordType == 1){
                            knockbackTaken *= 0.3f;
                        } else {
                            knockbackTaken *= 0.5f;
                        }
                        if (target instanceof LivingEntity) {
                            ((LivingEntity)target).takeKnockback(knockbackTaken, deltaX, deltaY);
                        } else {
                            target.addVelocity(-deltaX * knockbackTaken, 0.1, -deltaY * knockbackTaken);
                        }

                        player.setVelocity(player.getVelocity().multiply(0.6, 1.0, 0.6));
                        player.setSprinting(false);
                    }

                    double d = (double)(player.horizontalSpeed - player.prevHorizontalSpeed);
                    if (attackOffCooldown &&
                            !jumpAttack &&
                            !sprintAttack &&
                            player.isOnGround() &&
                            d < (double) player.getMovementSpeed()) {
                        // Normal Swords
                        if (swordType == 0) {
                            float l = 1.0F + EnchantmentHelper.getSweepingMultiplier(player) * baseAtkDmg;
                            for (LivingEntity livingEntity : player.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
                                if (livingEntity != player
                                        && livingEntity != target
                                        && !player.isTeammate(livingEntity)
                                        && (!(livingEntity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity).isMarker())
                                        && player.squaredDistanceTo(livingEntity) < 9.0) {
                                    livingEntity.takeKnockback(
                                            0.4F, MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)), (double)(-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)))
                                    );
                                    livingEntity.damage(player.getDamageSources().playerAttack(player), l);
                                }
                            }

                            player.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                            player.spawnSweepAttackParticles();

                        // Rapier
                        } else if (swordType == 1) {
                            float l = 1.0F + EnchantmentHelper.getSweepingMultiplier(player) * baseAtkDmg;
                            for (LivingEntity livingEntity : player.getWorld().getNonSpectatingEntities(LivingEntity.class, target.getBoundingBox().expand(1.0, 0.25, 1.0))) {
                                if (livingEntity != player
                                        && livingEntity != target
                                        && !player.isTeammate(livingEntity)
                                        && (!(livingEntity instanceof ArmorStandEntity) || !((ArmorStandEntity)livingEntity).isMarker())
                                        && player.squaredDistanceTo(livingEntity) < 9.0) {
                                    livingEntity.takeKnockback(
                                            0.4F, MathHelper.sin(this.getYaw() * (float) (Math.PI / 180.0)), (double)(-MathHelper.cos(this.getYaw() * (float) (Math.PI / 180.0)))
                                    );
                                    livingEntity.damage(player.getDamageSources().playerAttack(player), l);
                                }
                            }

                            player.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, this.getSoundCategory(), 1.0F, 1.0F);
                            this.spawnLungeAttackParticles();
                        }
                    }

                    if (target instanceof ServerPlayerEntity && target.velocityModified) {
                        ((ServerPlayerEntity)target).networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(target));
                        target.velocityModified = false;
                        target.setVelocity(vec3d);
                    }

                    if (jumpAttack) {
                        player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, player.getSoundCategory(), 1.0F, 1.0F);
                        if (swordType == 1){
                            player.addCritParticles(target);
                        }
                    }

                    if (!jumpAttack && swordType == -1) {
                        if (attackOffCooldown) {
                            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, player.getSoundCategory(), 1.0F, 1.0F);
                        } else {
                            player.getWorld().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, player.getSoundCategory(), 1.0F, 1.0F);
                        }
                    }

                    if (enchAtkDmg > 0.0F) {
                        player.addEnchantedHitParticles(target);
                    }

                    player.onAttacking(target);
                    if (target instanceof LivingEntity) {
                        EnchantmentHelper.onUserDamaged((LivingEntity)target, player);
                    }

                    EnchantmentHelper.onTargetDamaged(player, target);
                    ItemStack itemStack2 = player.getMainHandStack();
                    Entity entity = target;
                    if (target instanceof EnderDragonPart) {
                        entity = ((EnderDragonPart)target).owner;
                    }

                    if (!player.getWorld().isClient && !itemStack2.isEmpty() && entity instanceof LivingEntity) {
                        itemStack2.postHit((LivingEntity)entity, player);
                        if (itemStack2.isEmpty()) {
                            player.setStackInHand(Hand.MAIN_HAND, ItemStack.EMPTY);
                        }
                    }

                    if (target instanceof LivingEntity) {
                        float m = targetHealth - ((LivingEntity)target).getHealth();
                        player.increaseStat(Stats.DAMAGE_DEALT, Math.round(m * 10.0F));
                        if (k > 0) {
                            target.setOnFireFor(k * 4);
                        }

                        if (player.getWorld() instanceof ServerWorld && m > 2.0F) {
                            int n = (int)((double)m * 0.5);
                            ((ServerWorld) player.getWorld()).spawnParticles(ParticleTypes.DAMAGE_INDICATOR, target.getX(), target.getBodyY(0.5), target.getZ(), n, 0.1, 0.0, 0.1, 0.2);
                        }
                    }

                    player.addExhaustion(0.1F);
                } else {
                    player.getWorld().playSound((PlayerEntity)null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, player.getSoundCategory(), 1.0F, 1.0F);
                    if (bl5) {
                        target.extinguish();
                    }
                }
                //}
        }
    }

    @Inject(method = "getProjectileType", at = @At("HEAD"))
    public void getProjectileType(ItemStack stack, CallbackInfoReturnable<ItemStack> cir) {
        if (!(stack.getItem() instanceof RangedWeaponItem)) {
            cir.setReturnValue(ItemStack.EMPTY);
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
                    }
                }

                ItemStack returnVal =  this.abilities.creativeMode ? new ItemStack(Items.ARROW) : ItemStack.EMPTY;
                cir.setReturnValue(returnVal);
            }
        }
    }

}
