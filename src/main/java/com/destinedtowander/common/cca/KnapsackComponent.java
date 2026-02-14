package com.destinedtowander.common.cca;

import com.destinedtowander.common.index.ModComponents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class KnapsackComponent extends InventoryExpansionComponent {
    public KnapsackComponent(PlayerEntity player) {
        super(player, 18);
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
        for (int i = 0; i < this.inventory.size(); i++)
            this.inventory.setStack(i, ItemStack.fromNbt(tag.getCompound("knapsack_" + i)));
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        for (int i = 0; i < this.inventory.size(); i++)
            tag.put("knapsack_" + i, this.inventory.getStack(i).writeNbt(new NbtCompound()));
    }

    @Override
    public boolean setStack(ItemStack itemStack, int slot) {
        if (slot > this.inventory.size() || slot < 0) return false;
        this.inventory.setStack(slot, itemStack);
        ModComponents.KNAPSACK_COMPONENT.sync(this.player);
        return true;
    }

    public static ItemStack getStack(PlayerEntity player, int slot) {
        return ModComponents.KNAPSACK_COMPONENT.get(player).getStack(slot);
    }

    public static boolean setStack(PlayerEntity player, ItemStack itemStack, int slot) {
        return ModComponents.KNAPSACK_COMPONENT.get(player).setStack(itemStack, slot);
    }

    public static SimpleInventory getInventory(PlayerEntity player) {
        return ModComponents.KNAPSACK_COMPONENT.get(player).getInventory();
    }
}
