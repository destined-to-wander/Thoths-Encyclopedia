package com.destinedtowander.common.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;

public abstract class InventoryExpansionComponent implements AutoSyncedComponent {
    protected final PlayerEntity player;
    protected final SimpleInventory inventory;

    public InventoryExpansionComponent(PlayerEntity player, int size) {
        this.player = player;
        this.inventory = new SimpleInventory(size);
    }

    public int getSize() {
        return this.inventory.size();
    }

    public ItemStack getStack(int slot) {
        return this.inventory.getStack(slot);
    }

    public SimpleInventory getInventory() {
        return this.inventory;
    }

    public abstract boolean setStack(ItemStack itemStack, int slot);

}
