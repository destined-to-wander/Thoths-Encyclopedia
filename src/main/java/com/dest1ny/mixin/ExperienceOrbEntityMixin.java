package com.dest1ny.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {

    public ExperienceOrbEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow
    public abstract int getMendingRepairAmount(int experienceAmount);

    @Shadow
    public abstract int getMendingRepairCost(int repairAmount);

    @Shadow
    int amount;

    @Shadow
    public abstract int repairPlayerGears(PlayerEntity player, int amount);

    @Inject(method = "repairPlayerGears", at = @At("HEAD"),cancellable = true)
    private void mendingRewprk(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> cir) {
        cir.cancel();
        Map.Entry<EquipmentSlot, ItemStack> entry = EnchantmentHelper.chooseEquipmentWith(Enchantments.MENDING, player, ItemStack::isDamaged);
        if (entry != null) {
            ItemStack itemStack = (ItemStack)entry.getValue();
            int i = Math.min(this.getMendingRepairAmount(this.amount), itemStack.getDamage());
            if (itemStack.getDamage() > itemStack.getMaxDamage() / 4){
                int repairAmount = Math.max(itemStack.getDamage() - i,itemStack.getMaxDamage() / 4);
                itemStack.setDamage(repairAmount);
                int j = amount - this.getMendingRepairCost(i);
                int returnVal =  j > 0 ? this.repairPlayerGears(player, j) : 0;
                cir.setReturnValue(returnVal);
            } else{
                cir.setReturnValue(amount);
            }
        } else {
            cir.setReturnValue(amount);
        }
    }
}
