package com.dest1ny.index;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

import static com.dest1ny.ThothsEncyclopedia.id;
import static com.dest1ny.index.ModBlocks.BLOCKITEMS;
import static com.dest1ny.index.ModItems.ITEMS;

public class ModItemGroup {
    public static final ItemGroup THOTHPEDIA = Registry.register(Registries.ITEM_GROUP,
            id("thothpedia"),
            FabricItemGroup.builder()
                    .displayName(Text.translatable("itemgroup.thothpedia"))
                    .icon(() -> new ItemStack(ModItems.DIAMOND_RAPIER))
                    .entries((displayContext, entries) -> {
                        entries.addAll(ITEMS.stream().map(Item::getDefaultStack).toList());
                        entries.addAll(BLOCKITEMS.stream().map(Item::getDefaultStack).toList());
                    }).build());

    public static void register(){}
}
