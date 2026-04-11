package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.RecipeProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.TagKey;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeGen extends FabricRecipeProvider {
    public ModRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> consumer) {
        offerSmelting(consumer, List.of(ModItems.RAW_SCULKSTEEL), RecipeCategory.MISC, ModItems.SCULKSTEEL_INGOT, 0.7F, 200, "thothpedia");
        offerBlasting(consumer, List.of(ModItems.RAW_SCULKSTEEL), RecipeCategory.MISC, ModItems.SCULKSTEEL_INGOT, 0.7F, 100, "thothpedia");
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WAYWARD_MEDLEY).input(ModItems.WAYWARD_CLOTH).input(Items.LEATHER).input(Items.PHANTOM_MEMBRANE)
            .criterion("has_wayward_cloth", conditionsFromItem(ModItems.WAYWARD_CLOTH)).offerTo(consumer);

        offerShapelessRecipe(consumer, ModBlocks.CINNAMON_PLANKS, ModBlocks.CINNAMON_LOG, "thothpedia",4);
        offerShapelessRecipe(consumer, ModBlocks.CINNAMON_PLANKS, ModBlocks.STRIPPED_CINNAMON_LOG, "thothpedia",4);
        offerShapelessRecipe(consumer, ModBlocks.CINNAMON_PLANKS, ModBlocks.CINNAMON_WOOD, "thothpedia",4);
        offerShapelessRecipe(consumer, ModBlocks.CINNAMON_PLANKS, ModBlocks.STRIPPED_CINNAMON_WOOD, "thothpedia",4);

        offerQuadBarkBlockRecipe(consumer, ModBlocks.CINNAMON_WOOD, ModBlocks.CINNAMON_LOG);
        offerQuadBarkBlockRecipe(consumer, ModBlocks.STRIPPED_CINNAMON_WOOD, ModBlocks.STRIPPED_CINNAMON_LOG);

        offerPlanksRecipe(consumer, ModBlocks.CINNAMON_PLANKS, TagKey.of());
    }

    public static void offerQuadBarkBlockRecipe(Consumer<RecipeJsonProvider> exporter, ItemConvertible output, ItemConvertible input) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 4)
            .input('#', input)
            .pattern("##")
            .pattern("##")
            .group("bark")
            .criterion("has_log", conditionsFromItem(input))
            .offerTo(exporter);
    }
}
