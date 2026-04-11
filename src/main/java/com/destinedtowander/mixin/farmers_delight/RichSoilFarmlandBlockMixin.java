package com.destinedtowander.mixin.farmers_delight;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.lunade.copper.blocks.block_entity.leaking_pipes.LeakingPipeManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import vectorwing.farmersdelight.common.block.RichSoilFarmlandBlock;

@Mixin(RichSoilFarmlandBlock.class)
public class RichSoilFarmlandBlockMixin{

    @ModifyReturnValue(method = "hasWater", at = @At("RETURN"))
    private static boolean isNearLeakingPipe(boolean original, WorldView worldView, BlockPos blockPos) {
        return original || LeakingPipeManager.isWaterPipeNearbyBlockGetter(worldView, blockPos, 6);
    }
}
