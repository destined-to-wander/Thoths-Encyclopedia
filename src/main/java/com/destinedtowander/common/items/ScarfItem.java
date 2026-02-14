package com.destinedtowander.common.items;

import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

public class ScarfItem extends TrinketItem implements DyeableItem {

    @Override
    public int getColor(ItemStack stack) {
        NbtCompound nbtCompound = stack.getSubNbt("display");
        return nbtCompound != null && nbtCompound.contains("color", NbtElement.NUMBER_TYPE) ? nbtCompound.getInt("color") : 0xFFFFFF;
    }

    public ScarfItem(Settings settings) {
        super(settings);
    }
}
