package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.index.ModTags;
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

        offerQuadBarkBlockRecipe(consumer, ModBlocks.CINNAMON_WOOD, ModBlocks.CINNAMON_LOG);
        offerQuadBarkBlockRecipe(consumer, ModBlocks.STRIPPED_CINNAMON_WOOD, ModBlocks.STRIPPED_CINNAMON_LOG);
        offerHangingSignRecipe(consumer, ModBlocks.CINNAMON_HANGING_SIGN, ModBlocks.STRIPPED_CINNAMON_LOG);

        offerPlanksRecipe(consumer, ModBlocks.CINNAMON_PLANKS, ModTags.ModItemTags.CINNAMON_LOGS, 4);

        offerQuadBarkBlockRecipe(consumer, ModBlocks.FROSTBARK_WOOD, ModBlocks.FROSTBARK_LOG);
        offerQuadBarkBlockRecipe(consumer, ModBlocks.STRIPPED_FROSTBARK_WOOD, ModBlocks.STRIPPED_FROSTBARK_LOG);
        offerHangingSignRecipe(consumer, ModBlocks.FROSTBARK_HANGING_SIGN, ModBlocks.STRIPPED_FROSTBARK_LOG);

        offerPlanksRecipe(consumer, ModBlocks.FROSTBARK_PLANKS, ModTags.ModItemTags.FROSTBARK_LOGS, 4);
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
