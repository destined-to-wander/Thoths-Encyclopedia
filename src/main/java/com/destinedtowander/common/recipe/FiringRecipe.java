package com.destinedtowander.common.recipe;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModRecipeSerializers;
import com.destinedtowander.common.index.ModRecipeTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CookingRecipeCategory;
import net.minecraft.util.Identifier;

public class FiringRecipe extends AbstractCookingRecipe {
    public FiringRecipe(Identifier id, String group, CookingRecipeCategory category, Ingredient input, ItemStack output, float experience, int cookTime) {
        super(ModRecipeTypes.FIRING, id, group, category, input, output, experience, cookTime);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.KILN);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.FIRING;
    }

}
