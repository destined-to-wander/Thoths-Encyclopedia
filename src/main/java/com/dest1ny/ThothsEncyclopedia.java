package com.dest1ny;

import com.dest1ny.index.ModScreenHandlers;
import com.dest1ny.index.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("Thothpedia");
	public static final String MOD_ID = "thothpedia";

	@Override
	public void onInitialize() {
		ModScreenHandlers.register();
		ModEnchantments.register();
		ModBlocks.register();
		ModItems.register();
		ModItemGroup.register();
	}

	public static Identifier id(String path) {
		return new Identifier(MOD_ID, path);
	}
}