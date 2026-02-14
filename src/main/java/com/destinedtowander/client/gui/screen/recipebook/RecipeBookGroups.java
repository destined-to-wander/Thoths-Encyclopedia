package com.destinedtowander.client.gui.screen.recipebook;

import com.chocohead.mm.api.ClassTinkerers;
import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.ThothsEncyclopediaASM;
import com.destinedtowander.common.index.ModRecipeTypes;
import com.destinedtowander.common.recipe.FiringRecipe;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableList;
import io.github.fabricators_of_create.porting_lib.recipe_book_categories.RecipeBookRegistry;
import net.minecraft.client.recipebook.RecipeBookGroup;
import net.minecraft.recipe.book.CookingRecipeCategory;

import static net.minecraft.client.recipebook.RecipeBookGroup.*;

public class RecipeBookGroups {
    public static final Supplier<RecipeBookGroup> FIRING_BLOCKS = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookGroup.class, ThothsEncyclopediaASM.FIRING_BLOCKS));
    public static final Supplier<RecipeBookGroup> FIRING_MISC = Suppliers.memoize(() -> ClassTinkerers.getEnum(RecipeBookGroup.class, ThothsEncyclopediaASM.FIRING_MISC));

    public static void init() {
        RecipeBookRegistry.registerBookCategories(ThothsEncyclopedia.RECIPE_TYPE_FIRING, ImmutableList.of(FURNACE_SEARCH, FIRING_BLOCKS.get(), FIRING_MISC.get()));
        RecipeBookRegistry.registerAggregateCategory(FURNACE_SEARCH, ImmutableList.of(FIRING_BLOCKS.get(), FIRING_MISC.get()));
        RecipeBookRegistry.registerRecipeCategoryFinder(ModRecipeTypes.FIRING, recipe -> {
            if (recipe instanceof FiringRecipe firingRecipe) {
                CookingRecipeCategory tab = firingRecipe.getCategory();
                if (tab != null) {
                    return switch (tab) {
                        case BLOCKS -> FIRING_BLOCKS.get();
                        case FOOD, MISC -> FIRING_MISC.get();
                    };
                }
            }
            return FIRING_MISC.get();
        });
    }
}
