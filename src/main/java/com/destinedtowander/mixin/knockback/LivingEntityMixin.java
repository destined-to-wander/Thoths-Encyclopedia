package com.destinedtowander.mixin.knockback;

import com.destinedtowander.common.cca.WanderersComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {
    @Unique
    private static boolean cancelKnockback = false;

    public LivingEntityMixin() {
    }

    @Inject(
        method = {"damage"},
        at = {@At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V",
            shift = At.Shift.BEFORE
        )}
    )
    private void cancelFireKnockbackBefore(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (((Object)this) instanceof PlayerEntity player && source.isOf(DamageTypes.ON_FIRE)) cancelKnockback = true;
    }

    @Inject(
        method = {"takeKnockback"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private void cancelFireKnockback(double strength, double x, double z, CallbackInfo ci) {
        if (cancelKnockback) {
            cancelKnockback = false;
            ci.cancel();
        }
    }
}
