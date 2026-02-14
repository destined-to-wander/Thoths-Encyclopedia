package com.destinedtowander;

import com.destinedtowander.common.index.ModScreenHandlers;
import com.destinedtowander.common.index.*;
import net.fabricmc.api.ModInitializer;

import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.util.Identifier;

public class ThothsEncyclopedia implements ModInitializer {

	/*
	public class ModCompat {
		public static final boolean IS_SOME_MOD_LOADED = FabricLoader.getInstance().isModLoaded("modid");

		public static void init() {
			if (IS_SOME_MOD_LOADED) {
				SomeModIntegration.init();
			}
		}
	}*/

    //public static final Logger LOGGER = LoggerFactory.getLogger("Thothpedia");
	public static final String MOD_ID = "thothpedia";

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}

	public static final RecipeBookCategory RECIPE_TYPE_FIRING = RecipeBookCategory.valueOf("THOTHPEDIA_FIRING");

	@Override
	public void onInitialize() {
		ModRecipeTypes.register();
		ModRecipeSerializers.register();
		ModScreenHandlers.register();
		ModEnchantments.register();
		ModBlocks.register();
		ModBlockEntities.register();
		ModItems.register();
		ModItemGroup.register();
	}
}