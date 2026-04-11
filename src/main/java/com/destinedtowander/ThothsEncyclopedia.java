package com.destinedtowander;

import com.destinedtowander.client.particle.ModParticles;
import com.destinedtowander.common.index.ModScreenHandlers;
import com.destinedtowander.common.index.*;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.block.PowderSnowBlock;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.gen.feature.Feature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThothsEncyclopedia implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("Thothpedia");
	public static final String MOD_ID = "thothpedia";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final RecipeBookCategory RECIPE_TYPE_FIRING = RecipeBookCategory.valueOf("THOTHPEDIA_FIRING");

	@Override
	public void onInitialize() {
		ModBlockSetTypes.register();
		ModWoodTypes.register();
		ModSoundEvents.register();
		ModRecipeTypes.register();
		ModRecipeSerializers.register();
		ModScreenHandlers.register();
		ModEnchantments.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModItems.register();
		ModItemGroup.register();
		ModTrades.register();
		ModParticles.register();
	}
}