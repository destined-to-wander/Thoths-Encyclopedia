package com.destinedtowander.client.gui.screen.recipebook;

import java.util.Set;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.client.gui.screen.recipebook.AbstractFurnaceRecipeBookScreen;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class KilnRecipeBookScreen extends AbstractFurnaceRecipeBookScreen {
    private static final Text TOGGLE_FIRING_RECIPES_TEXT = Text.translatable("gui.recipebook.toggleRecipes.fired");

    @Override
    protected Text getToggleCraftableButtonText() {
        return TOGGLE_FIRING_RECIPES_TEXT;
    }

    @Override
    protected Set<Item> getAllowedFuels() {
        return AbstractFurnaceBlockEntity.createFuelTimeMap().keySet();
    }
}