package com.destinedtowander.mixin;

import com.destinedtowander.common.misc.RaidTracker;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HostileEntity.class)
public class HostileEntityMixin implements RaidTracker {

    // disable evoker raid drops
    @Unique
    boolean wasPartOfRaid = false;

    @Override
    public void thoth_sEncyclopedia$setWasPartOfRaid(boolean value){
        wasPartOfRaid = value;
    }

    @Inject(at = @At("HEAD"), method = "shouldDropLoot", cancellable = true)
    private void disableRaidLoot(CallbackInfoReturnable<Boolean> cir) {
        if (false && ((HostileEntity)(Object)this) instanceof EvokerEntity entity && wasPartOfRaid && entity.getRandom().nextDouble() < 0.01) cir.setReturnValue(false);
    }
}
