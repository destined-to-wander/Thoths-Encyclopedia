package com.destinedtowander;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.function.Supplier;

public class ThothsEncyclopediaASM implements Runnable{
    public static final String FIRING_RECIPE_BOOK_TYPE = "THOTHPEDIA_FIRING";
    public static final String FIRING_BLOCKS = "FIRING_BLOCKS";
    public static final String FIRING_MISC = "FIRING_MISC";

    @Override
    public void run() {
        MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();
        String recipeBookTypeTarget = remapper.mapClassName("intermediary", "net.minecraft.class_5421");
        ClassTinkerers.enumBuilder(recipeBookTypeTarget).addEnum(FIRING_RECIPE_BOOK_TYPE).build();


        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
            String recipeBookGroupTarget = remapper.mapClassName("intermediary", "net.minecraft.class_314");
            String itemStackParamType = "[L" + remapper.mapClassName("intermediary", "net.minecraft.class_1799") + ";";
            ClassTinkerers.enumBuilder(recipeBookGroupTarget, itemStackParamType).addEnum(FIRING_BLOCKS, getBlocksGroupStacks()).build();
            ClassTinkerers.enumBuilder(recipeBookGroupTarget, itemStackParamType).addEnum(FIRING_MISC, getMiscGroupStacks()).build();
        }
    }

    public static Supplier<Object[]> getMiscGroupStacks() {
        // Requires a Supplier based workaround to make sure that ItemLike isn't loaded.
        return () -> new Object[]{new ItemStack[]{new ItemStack(Items.BRICK), new ItemStack(Items.POPPED_CHORUS_FRUIT)}};
    }

    public static Supplier<Object[]> getBlocksGroupStacks() {
        return () -> new Object[]{new ItemStack[]{new ItemStack(Items.BLUE_GLAZED_TERRACOTTA)}};
    }
}