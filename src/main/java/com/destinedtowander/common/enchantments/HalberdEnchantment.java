package com.destinedtowander.common.enchantments;

import com.destinedtowander.common.items.HalberdItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;

public class HalberdEnchantment extends Enchantment {
    public HalberdEnchantment() {
        super(Rarity.RARE, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    public boolean isAcceptableItem(ItemStack stack) {
        return stack.getItem() instanceof HalberdItem;
    }

    public int getMinPower(int level) {
        return 15;
    }

    public int getMaxPower(int level) {
        return super.getMinPower(level) + 50;
    }

    protected boolean canAccept(Enchantment other) {
        return !(other instanceof HalberdEnchantment) && super.canAccept(other);
    }
}
