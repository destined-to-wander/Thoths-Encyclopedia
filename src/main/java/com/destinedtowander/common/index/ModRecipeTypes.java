package com.destinedtowander.common.index;

import com.destinedtowander.common.recipe.FiringRecipe;
import net.minecraft.recipe.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModRecipeTypes {
    public static final RecipeType<FiringRecipe> FIRING = registerRecipe("firing");

    static <T extends Recipe<?>> RecipeType<T> registerRecipe(String id) {
        return Registry.register(Registries.RECIPE_TYPE, id(id), new RecipeType<T>() {
            public String toString() {
                return id;
            }
        });
    }

    public static void register() {
        FIRING.toString();
    }
}
