package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModTags;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModTagGen {
    public static class ModItemTagGen extends FabricTagProvider<Item> {
        public ModItemTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.ITEM, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(ModTags.ModItemTags.CINNAMON_LOGS)
                .add(ModBlocks.CINNAMON_LOG.asItem())
                .add(ModBlocks.STRIPPED_CINNAMON_LOG.asItem())
                .add(ModBlocks.CINNAMON_WOOD.asItem())
                .add(ModBlocks.STRIPPED_CINNAMON_WOOD.asItem());
            getOrCreateTagBuilder(ModTags.ModItemTags.FROSTBARK_LOGS)
                .add(ModBlocks.FROSTBARK_LOG.asItem())
                .add(ModBlocks.STRIPPED_FROSTBARK_LOG.asItem())
                .add(ModBlocks.FROSTBARK_WOOD.asItem())
                .add(ModBlocks.STRIPPED_FROSTBARK_WOOD.asItem());
        }
    }

    public static class ModBlockTagGen extends FabricTagProvider<Block> {
        public ModBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
            super(output, RegistryKeys.BLOCK, registriesFuture);
        }

        @Override
        protected void configure(RegistryWrapper.WrapperLookup lookup) {
            getOrCreateTagBuilder(ModTags.ModBlockTags.CINNAMON_LOGS)
                .add(ModBlocks.CINNAMON_LOG)
                .add(ModBlocks.STRIPPED_CINNAMON_LOG)
                .add(ModBlocks.CINNAMON_WOOD)
                .add(ModBlocks.STRIPPED_CINNAMON_WOOD);
            getOrCreateTagBuilder(ModTags.ModBlockTags.FROSTBARK_LOGS)
                .add(ModBlocks.FROSTBARK_LOG)
                .add(ModBlocks.STRIPPED_FROSTBARK_LOG)
                .add(ModBlocks.FROSTBARK_WOOD)
                .add(ModBlocks.STRIPPED_FROSTBARK_WOOD);
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.KILN)
                .add(ModBlocks.SCULK_CHARGED_AMALGAM)
                .add(ModBlocks.POTTED_CINNAMON_SAPLING);
            getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.SCULK_CHARGED_AMALGAM);
            getOrCreateTagBuilder(BlockTags.AXE_MINEABLE)
                .add(ModBlocks.PYROTECHNICS_TABLE)
                .add(ModBlocks.CINNAMON_PLANKS)
                .add(ModBlocks.CINNAMON_LOG)
                .add(ModBlocks.CINNAMON_WOOD)
                .add(ModBlocks.STRIPPED_CINNAMON_LOG)
                .add(ModBlocks.STRIPPED_CINNAMON_WOOD)
                .add(ModBlocks.CINNAMON_BUTTON)
                .add(ModBlocks.CINNAMON_DOOR)
                .add(ModBlocks.CINNAMON_TRAPDOOR)
                .add(ModBlocks.CINNAMON_STAIRS)
                .add(ModBlocks.CINNAMON_SLAB)
                .add(ModBlocks.CINNAMON_FENCE)
                .add(ModBlocks.CINNAMON_FENCE_GATE)
                .add(ModBlocks.CINNAMON_SIGN)
                .add(ModBlocks.CINNAMON_WALL_SIGN)
                .add(ModBlocks.CINNAMON_HANGING_SIGN)
                .add(ModBlocks.CINNAMON_WALL_HANGING_SIGN)
                .add(ModBlocks.CINNAMON_SAPLING)
                .add(ModBlocks.CINNAMON_PRESSURE_PLATE)
                .add(ModBlocks.FROSTBARK_PLANKS)
                .add(ModBlocks.FROSTBARK_LOG)
                .add(ModBlocks.FROSTBARK_WOOD)
                .add(ModBlocks.STRIPPED_FROSTBARK_LOG)
                .add(ModBlocks.STRIPPED_FROSTBARK_WOOD)
                .add(ModBlocks.FROSTBARK_BUTTON)
                .add(ModBlocks.FROSTBARK_DOOR)
                .add(ModBlocks.FROSTBARK_TRAPDOOR)
                .add(ModBlocks.FROSTBARK_STAIRS)
                .add(ModBlocks.FROSTBARK_SLAB)
                .add(ModBlocks.FROSTBARK_FENCE)
                .add(ModBlocks.FROSTBARK_FENCE_GATE)
                .add(ModBlocks.FROSTBARK_SIGN)
                .add(ModBlocks.FROSTBARK_WALL_SIGN)
                .add(ModBlocks.FROSTBARK_HANGING_SIGN)
                .add(ModBlocks.FROSTBARK_WALL_HANGING_SIGN)
                .add(ModBlocks.FROSTBARK_SAPLING)
                .add(ModBlocks.FROSTBARK_PRESSURE_PLATE);
        }
    }
}
