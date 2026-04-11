package com.destinedtowander.mixin.sculksteel;

import com.destinedtowander.common.index.ModBlocks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkVeinBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SculkVeinBlock.SculkVeinGrowChecker.class)
public abstract class SculkVeinGrowCheckerMixin {
    @ModifyReturnValue(
        method = "canGrow(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/Direction;Lnet/minecraft/block/BlockState;)Z",
        at = @At(value = "RETURN")
    )
    private boolean disallowVeinOnSculkAmalgam(boolean original, BlockView world, BlockPos pos, BlockPos growPos, Direction direction, BlockState state) {
        return original && !world.getBlockState(growPos.offset(direction)).isOf(ModBlocks.SCULK_CHARGED_AMALGAM);
    }
}