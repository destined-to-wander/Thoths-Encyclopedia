package com.destinedtowander.common.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import static com.destinedtowander.ThothsEncyclopedia.id;
import static com.destinedtowander.common.index.ModBlocks.BLOCKITEMS;
import static com.destinedtowander.common.index.ModItems.INCLUDE_ITEMGROUP;

public class ModItemGroup {
    public static final ItemGroup THOTHPEDIA = Registry.register(Registries.ITEM_GROUP,
            id("thothpedia"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.thothpedia"))
                    .icon(() -> new ItemStack(ModItems.DIAMOND_RAPIER))
                    .entries((displayContext, entries) -> {
                        entries.addAll(INCLUDE_ITEMGROUP.stream().map(Item::getDefaultStack).toList());
                        entries.addAll(BLOCKITEMS.stream().map(Item::getDefaultStack).toList());
                    }).build());

    public static void register(){}
}
