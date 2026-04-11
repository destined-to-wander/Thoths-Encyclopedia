package com.destinedtowander.common.blocks;

import net.minecraft.block.*;
import net.minecraft.block.entity.SculkSpreadManager;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;

public class SculkChargedAmalgamBlock extends Block implements SculkSpreadable {
    public SculkChargedAmalgamBlock(AbstractBlock.Settings settings) {
        super(settings);
    }

    @Override
    public int spread(
        SculkSpreadManager.Cursor cursor, WorldAccess world, BlockPos catalystPos, Random random, SculkSpreadManager spreadManager, boolean shouldConvertToBlock
    ) {
        int i = cursor.getCharge();
        if (i != 0 && random.nextInt(spreadManager.getSpreadChance()) == 0) {
            BlockPos blockPos = cursor.getPos();
            boolean bl = blockPos.isWithinDistance(catalystPos, spreadManager.getMaxDistance());
            if (!bl && shouldNotDecay(world, blockPos)) return Math.max(0, i-spreadManager.getExtraBlockChance());
            else return random.nextInt(spreadManager.getDecayChance()) != 0 ? i : i - (bl ? 1 : getDecay(spreadManager, blockPos, catalystPos, i));
        } else return i;
    }

    private static int getDecay(SculkSpreadManager spreadManager, BlockPos cursorPos, BlockPos catalystPos, int charge) {
        int i = spreadManager.getMaxDistance();
        float f = MathHelper.square((float)Math.sqrt(cursorPos.getSquaredDistance(catalystPos)) - (float)i);
        int j = MathHelper.square(24 - i);
        float g = Math.min(1.0F, f / (float)j);
        return Math.max(1, (int)((float)charge * g * 0.5F));
    }

    private static boolean shouldNotDecay(WorldAccess world, BlockPos pos) {
        BlockState blockState = world.getBlockState(pos.up());
        if (blockState.isAir() || blockState.isOf(Blocks.WATER) && blockState.getFluidState().isOf(Fluids.WATER)) {
            int i = 0;
            for (BlockPos blockPos : BlockPos.iterate(pos.add(-4, 0, -4), pos.add(4, 2, 4))) {
                BlockState blockState2 = world.getBlockState(blockPos);
                if (blockState2.isOf(Blocks.SCULK_SENSOR) || blockState2.isOf(Blocks.SCULK_SHRIEKER)) i++;

                if (i > 2) return false;
            }

            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldConvertToSpreadable() {
        return false;
    }
}
