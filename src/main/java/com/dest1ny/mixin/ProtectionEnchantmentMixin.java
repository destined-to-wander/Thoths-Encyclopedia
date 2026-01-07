package com.dest1ny.mixin;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.enchantment.ProtectionEnchantment;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProtectionEnchantment.class)
public class ProtectionEnchantmentMixin extends Enchantment{


    protected ProtectionEnchantmentMixin(Rarity weight, EnchantmentTarget target, EquipmentSlot[] slotTypes) {
        super(weight, target, slotTypes);
    }

    @Inject(method = "canAccept",at = @At("HEAD"),cancellable = true)
    public void protectionRework(Enchantment other, CallbackInfoReturnable<Boolean> CiR) {
        ProtectionEnchantment enchantment = (ProtectionEnchantment)(Object) this;
        if (other instanceof ProtectionEnchantment protectionEnchantment) {
            CiR.setReturnValue(enchantment.protectionType != protectionEnchantment.protectionType && (
                    enchantment.protectionType == ProtectionEnchantment.Type.FALL ||
                            protectionEnchantment.protectionType == ProtectionEnchantment.Type.FALL ||
                            enchantment.protectionType == ProtectionEnchantment.Type.ALL ||
                            protectionEnchantment.protectionType == ProtectionEnchantment.Type.ALL
                    )
            );
        }
    }

}
