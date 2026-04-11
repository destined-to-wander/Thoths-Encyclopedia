package com.destinedtowander.compat.coffins;

import com.destinedtowander.common.cca.KnapsackComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import powercyphe.coffins.modsupport.ModSupporter;

import java.util.ArrayList;
import java.util.List;

public class ThothsEncyclopediaSupportImpl implements ModSupporter {
    @Override
    public String supportingModId() {
        return "thothpedia";
    }

    @Override
    public void onInit() {}

    @Override
    public List<ItemStack> getDrops(PlayerEntity player) {
        List<ItemStack> items = new ArrayList<>();
        var inv = KnapsackComponent.getInventory(player);
        for (int i = 0; i < inv.size(); i++) {
            ItemStack stack = inv.getStack(i);
            if (!stack.isEmpty()) {
                items.add(stack.copy()); // copy to avoid clearing player inventory
                inv.setStack(i, ItemStack.EMPTY); // clear knapsack after death
            }
        }
        return items;
    }
}
