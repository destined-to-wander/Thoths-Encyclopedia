package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.cca.WanderersComponent;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.index.ModSoundEvents;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyReturnValue(method = "getOffGroundSpeed", at = @At("RETURN"))
    public float airSpeed(float original) {
        if (((PlayerEntity)(Object)this).getEquippedStack(EquipmentSlot.CHEST).isOf(ModItems.WANDERERS_CHESTPLATE)) return original * 1.6F;
        return original;
    }

    @WrapOperation(
        method = {"applyDamage"},
        at = {@At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
        )}
    )
    private float swordBlock(PlayerEntity entity, DamageSource source, float amount, Operation<Float> original) {
        float base = original.call(entity, source, amount);
        WanderersComponent wanderersComponent = WanderersComponent.get(entity);
        if (wanderersComponent.isParrying()) {
            Vec3d damagePos = source.getPosition();
            if (damagePos != null) {
                Vec3d rotVec = this.getRotationVec(1.0F);
                Vec3d difference = damagePos.relativize(this.getEyePos()).normalize();
                double angle = difference.dotProduct(rotVec);
                if (angle < (double)-1.0F || angle > (double)1.0F) return base;

                if (angle < -0.35) {
                    World world = this.getWorld();
                    if (world instanceof ServerWorld serverWorld) serverWorld.playSoundFromEntity(null, this,
                        ModSoundEvents.SWORD_PARRY, SoundCategory.HOSTILE, 1.5F, 1.0F + this.getWorld().random.nextFloat() * 0.4F);

                    wanderersComponent.stopParry();
                    return 0;
                }
            }
        }
        if (wanderersComponent.isBlocking()) {
            Vec3d damagePos = source.getPosition();
            if (damagePos != null) {
                Vec3d rotVec = this.getRotationVec(1.0F);
                Vec3d difference = damagePos.relativize(this.getEyePos()).normalize();
                double angle = difference.dotProduct(rotVec);
                if (angle < (double)-1.0F || angle > (double)1.0F) {
                    return base;
                }

                if (angle < -0.35) {
                    World world = this.getWorld();
                    if (world instanceof ServerWorld serverWorld) serverWorld.playSoundFromEntity(null, this,
                        ModSoundEvents.SWORD_BLOCK, SoundCategory.HOSTILE, 2.0F, 2.0F + this.getWorld().random.nextFloat() * 0.2F);

                    return base / 2.0F;
                }
            } else if (source == this.getWorld().getDamageSources().fall()) {
                Vec3d rotVec = this.getRotationVec(1.0F);
                Vec3d difference = new Vec3d(0.0F, 1.0F, 0.0F);
                double angle = difference.dotProduct(rotVec);
                if (angle < -0.35) {
                    World world = this.getWorld();
                    if (world instanceof ServerWorld serverWorld) serverWorld.playSoundFromEntity(null, this,
                        ModSoundEvents.SWORD_BLOCK, SoundCategory.HOSTILE, 2.0F, 2.0F + this.getWorld().random.nextFloat() * 0.2F);

                    return base / 6.0F;
                }
            }
        }

        return base;

    }
}
