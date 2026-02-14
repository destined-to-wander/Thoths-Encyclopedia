package com.destinedtowander.mixin;

import com.destinedtowander.common.misc.RaidTracker;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.raid.RaiderEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RaiderEntity.class)
public abstract class RaiderEntityMixin extends HostileEntity {

    protected RaiderEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "onDeath")
    private void setWasntPartOfRaid(DamageSource damageSource, CallbackInfo ci) {
        if (this.getWorld() instanceof ServerWorld && ((LivingEntity)(Object)this) instanceof EvokerEntity evoker && evoker instanceof RaidTracker raidEntity) {
            raidEntity.setWasPartOfRaid(evoker.hasActiveRaid());
        }
    }
}
