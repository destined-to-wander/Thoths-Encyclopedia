package com.dest1ny.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    public boolean canRepair(ItemStack stack, ItemStack ingredient){
        return stack.isOf((TridentItem) (Object) this) && (ingredient.isOf(Items.PRISMARINE_CRYSTALS) || ingredient.isOf(Items.HEART_OF_THE_SEA) || ingredient.isOf(Items.PRISMARINE_SHARD));
    }
}
