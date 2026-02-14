package com.destinedtowander.common.items;

import com.destinedtowander.common.misc.ClearableCraftingInventory;
import com.destinedtowander.common.misc.ToggleableSlot;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;

public class SlotDisablerItem extends TrinketItem {
    int disableStart;
    int disableEnd;
    public SlotDisablerItem(Settings settings, int start, int end) {
        super(settings);
        this.disableStart = start;
        this.disableEnd = end;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            PlayerScreenHandler handler = player.playerScreenHandler;
            for (int i = disableStart; i < disableEnd; i++) ((ToggleableSlot) handler.getSlot(i)).setEnabled(false);
            if (player.currentScreenHandler == handler) ((ClearableCraftingInventory)player.currentScreenHandler).dropCraftingSlots(player);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            PlayerScreenHandler handler = player.playerScreenHandler;
            for (int i = disableStart; i < disableEnd; i++) ((ToggleableSlot) handler.getSlot(i)).setEnabled(true);
            if (player.currentScreenHandler == handler) ((ClearableCraftingInventory)player.currentScreenHandler).dropCraftingSlots(player);
        }
    }
}
