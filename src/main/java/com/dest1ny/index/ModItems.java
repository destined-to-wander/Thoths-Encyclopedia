package com.dest1ny.index;

import com.dest1ny.items.HalberdItem;
import com.dest1ny.items.RapierItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.dest1ny.ThothsEncyclopedia.id;

public class ModItems {

    public static final List<Item> ITEMS = new ArrayList<>();

    public static RapierItem IRON_RAPIER;
    public static RapierItem GOLD_RAPIER;
    public static RapierItem DIAMOND_RAPIER;
    public static RapierItem NETHERITE_RAPIER;
    public static HalberdItem DIAMOND_HALBERD;

    static {
    IRON_RAPIER = registerItem("iron_rapier",new RapierItem(
            ToolMaterials.IRON, 2, -2f, new Item.Settings()));

    GOLD_RAPIER = registerItem("gold_rapier",new RapierItem(
            ToolMaterials.GOLD, 2, -2f, new Item.Settings()));

    DIAMOND_RAPIER = registerItem("diamond_rapier",new RapierItem(
            ToolMaterials.DIAMOND, 2, -2f, new Item.Settings()));

    NETHERITE_RAPIER = registerItem("netherite_rapier",new RapierItem(
            ToolMaterials.NETHERITE, 2, -2f, new Item.Settings().fireproof()));

    DIAMOND_HALBERD = registerItem("diamond_halberd",new HalberdItem(
        ToolMaterials.DIAMOND, 6, 2f, 6.0,4.0, new Item.Settings()));
    }

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registries.ITEM, id(name), item);
        ITEMS.add(item);
        return item;
    }

    public static void register(){}
}
