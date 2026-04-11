package com.destinedtowander.mixin.sculksteel;

import com.destinedtowander.common.index.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SculkVeinBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SculkVeinBlock.class)
public abstract class SculkVeinBlockMixin {
    @ModifyVariable(
        method = "convertToBlock",
        at = @At(value = "STORE"),
        ordinal = 2
    )
    private BlockState allowVeinToSpreadAmalgam(BlockState original, @Local(ordinal = 1) BlockState blockstate) {
        return blockstate.isOf(Blocks.RAW_IRON_BLOCK) ? ModBlocks.SCULK_CHARGED_AMALGAM.getDefaultState() : original;
    }

    @ModifyExpressionValue(
        method = "spreadAtSamePosition",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z"
        )
    )
    private boolean disallowVeinOnSculkAmalgam(boolean original, @Local(ordinal = 0, argsOnly = true) WorldAccess world, @Local(ordinal = 0, argsOnly = true) BlockPos pos, @Local(ordinal = 0) Direction direction) {
        return original || world.getBlockState(pos.offset(direction)).isOf(ModBlocks.SCULK_CHARGED_AMALGAM);
    }
}
