package com.destinedtowander.mixin.inventory;

import com.destinedtowander.common.misc.ToggleableSlot;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Slot.class)
public class SlotMixin implements ToggleableSlot {
    @Unique
    private boolean enabled = true;

    @ModifyReturnValue(method = "isEnabled", at = @At("RETURN"))
    public boolean isEnabled(boolean value) {
        return this.enabled;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public void setEnabled() {
        this.enabled = !this.enabled;
    }
}