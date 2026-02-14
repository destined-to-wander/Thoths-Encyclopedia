package com.destinedtowander.common.screens;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.index.ModRecipeTypes;
import com.destinedtowander.common.index.ModScreenHandlers;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.screen.AbstractFurnaceScreenHandler;
import net.minecraft.screen.PropertyDelegate;

public class KilnScreenHandler extends AbstractFurnaceScreenHandler {
    public KilnScreenHandler(int syncId, PlayerInventory playerInventory) {
        super(ModScreenHandlers.KILN, ModRecipeTypes.FIRING, ThothsEncyclopedia.RECIPE_TYPE_FIRING, syncId, playerInventory);
    }

    public KilnScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory, PropertyDelegate propertyDelegate) {
        super(ModScreenHandlers.KILN, ModRecipeTypes.FIRING, ThothsEncyclopedia.RECIPE_TYPE_FIRING, syncId, playerInventory, inventory, propertyDelegate);
    }
}
