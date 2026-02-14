package com.destinedtowander.common.blocks.entities;

import com.destinedtowander.common.index.ModBlockEntities;
import com.destinedtowander.common.index.ModRecipeTypes;
import com.destinedtowander.common.screens.KilnScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.AbstractFurnaceBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class KilnBlockEntity extends AbstractFurnaceBlockEntity {
    public KilnBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.KILN, pos, state, ModRecipeTypes.FIRING);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("thothpedia.container.kiln");
    }
    
    @Override
    protected int getFuelTime(ItemStack fuel) {
        return super.getFuelTime(fuel) / 2;
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new KilnScreenHandler(syncId, playerInventory, this, this.propertyDelegate);
    }
}
