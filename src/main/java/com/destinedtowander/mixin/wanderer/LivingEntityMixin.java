package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.cca.WanderersComponent;
import com.destinedtowander.common.index.ModComponents;
import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow
    protected boolean jumping;

    private LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @ModifyVariable(
        method = "travel",
        at = @At(
            value = "STORE",
            ordinal = 0
        ),
        ordinal = 0
    )
    private double controllableFallSpeedPortingLib(double original) {
        if (!FabricLoader.getInstance().isModLoaded("porting_lib_attributes")) return original;
        if (((LivingEntity)(Object)this) instanceof PlayerEntity player &&
            player.hasStatusEffect(StatusEffects.SLOW_FALLING) &&
            player.getComponent(ModComponents.WANDERER_COMPONENT).hasSetBonus() &&
            player.getVelocity().y <= 0.0
            && !jumping
        ) {
            original *= 8; // normal fall
            if (player.isSneaking()) original *= 2; // faster fall
        }
        return original;
    }

    @ModifyVariable(
        method = "travel",
        at = @At(
            value = "STORE",
            ordinal = 1
        ),
        ordinal = 0
    )
    private double controllableFallSpeed(double original) {
        if (((LivingEntity)(Object)this) instanceof PlayerEntity player &&
            player.getComponent(ModComponents.WANDERER_COMPONENT).hasSetBonus() &&
            !jumping
        ) {
            original *= 8; // normal fall
            if (player.isSneaking()) original *= 2; // faster fall
        }
        return original;
    }
}
