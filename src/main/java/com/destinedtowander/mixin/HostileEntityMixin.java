package com.destinedtowander.mixin;

import com.destinedtowander.common.misc.RaidTracker;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class HostileEntityMixin implements RaidTracker {

    // disable evoker raid drops
    boolean wasPartOfRaid = false;

    @Override
    public void setWasPartOfRaid(boolean value){
        wasPartOfRaid = value;
    }

    @Inject(at = @At("HEAD"), method = "shouldDropLoot", cancellable = true)
    private void disableRaidLoot(CallbackInfoReturnable<Boolean> cir) {
        if (((HostileEntity)(Object)this) instanceof EvokerEntity && wasPartOfRaid) cir.setReturnValue(false);
    }
}
