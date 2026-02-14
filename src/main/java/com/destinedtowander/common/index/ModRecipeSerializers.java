package com.destinedtowander.common.index;

import com.destinedtowander.common.recipe.FiringRecipe;
import net.minecraft.recipe.CookingRecipeSerializer;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.destinedtowander.ThothsEncyclopedia.id;

public interface ModRecipeSerializers {
    RecipeSerializer<FiringRecipe> FIRING = register("firing", new CookingRecipeSerializer<>(FiringRecipe::new, 100));

    static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, id(id), serializer);
    }

    static void register(){}
}
