package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModTags;
import com.destinedtowander.common.index.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGen extends FabricTagProvider<Block> {
    public ModBlockTagGen(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, RegistryKeys.BLOCK, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup lookup) {
        getOrCreateTagBuilder(ModTags.)
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
            .add(ModBlocks.CINNAMON_PRESSURE_PLATE);
    }
}
