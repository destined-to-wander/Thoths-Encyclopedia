package com.destinedtowander.mixin.woodtypes;

import com.destinedtowander.common.index.ModBlocks;
import com.google.common.collect.ImmutableMap;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.PillarBlock;
import net.minecraft.item.AxeItem;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Map;
import java.util.Optional;

@Mixin(AxeItem.class)
public class AxeItemMixin {

    @Unique
    private static final Map<Block, Block> STRIPPED_BLOCKS = new ImmutableMap.Builder<Block, Block>()
        .put(ModBlocks.CINNAMON_WOOD, ModBlocks.STRIPPED_CINNAMON_WOOD)
        .put(ModBlocks.CINNAMON_LOG, ModBlocks.STRIPPED_CINNAMON_LOG)
        .build();

    @ModifyReturnValue(method = "getStrippedState", at = @At("TAIL"))
    public Optional<BlockState> stripModdedLogs(Optional<BlockState> original, BlockState state){
        return Optional.ofNullable(STRIPPED_BLOCKS.get(state.getBlock()))
            .map(block -> block.getDefaultState().with(PillarBlock.AXIS, state.get(PillarBlock.AXIS)))
            .or(() -> original);
    }
}
