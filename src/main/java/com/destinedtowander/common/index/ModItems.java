package com.destinedtowander.common.index;

import com.destinedtowander.common.items.*;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModItems {
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Item> INCLUDE_ITEMGROUP = new ArrayList<>();

    public static final List<String> COLORS = List.of(
        "red",
        "orange",
        "yellow",
        "white",
        "black",
        "gray",
        "light_gray",
        "brown",
        "green",
        "lime",
        "purple",
        "magenta",
        "pink",
        "blue",
        "light_blue",
        "cyan"
    );

    public static final Item IRON_RAPIER;
    public static final Item GOLD_RAPIER;
    public static final Item DIAMOND_RAPIER;
    public static final Item NETHERITE_RAPIER;
    public static final Item IRON_HALBERD;
    public static final Item GOLD_HALBERD;
    public static final Item DIAMOND_HALBERD;
    public static final Item NETHERITE_HALBERD;
    public static final Item KNAPSACK;
    public static final Item QUIVER;
    public static final Item CRAFTING_EXPANDER;
    public static final Item SCARF;
    public static final Item WANDERERS_HELMET;
    public static final Item WANDERERS_CHESTPLATE;
    public static final Item WANDERERS_LEGGINGS;
    public static final Item WANDERERS_BOOTS;

    static {
        // Weapons
        IRON_RAPIER = registerRapier("iron_rapier", ToolMaterials.IRON, new Item.Settings());
        GOLD_RAPIER = registerRapier("golden_rapier", ToolMaterials.GOLD, new Item.Settings());
        DIAMOND_RAPIER = registerRapier("diamond_rapier", ToolMaterials.DIAMOND, new Item.Settings());
        NETHERITE_RAPIER = registerRapier("netherite_rapier", ToolMaterials.NETHERITE, new Item.Settings().fireproof());
        IRON_HALBERD = registerHalberd("iron_halberd", ToolMaterials.IRON, new Item.Settings());
        GOLD_HALBERD = registerHalberd("golden_halberd", ToolMaterials.GOLD,  new Item.Settings());
        DIAMOND_HALBERD = registerHalberd("diamond_halberd", ToolMaterials.DIAMOND, new Item.Settings());
        NETHERITE_HALBERD = registerHalberd("netherite_halberd", ToolMaterials.NETHERITE, new Item.Settings().fireproof());

        // Armor
        WANDERERS_HELMET = registerItemExcludeGroup("wanderers_helmet",
            new ArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.HELMET, new Item.Settings())
        );
        WANDERERS_CHESTPLATE = registerItemExcludeGroup("wanderers_chestplate",
            new ArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.CHESTPLATE, new Item.Settings())
        );
        WANDERERS_LEGGINGS = registerItemExcludeGroup("wanderers_leggings",
            new ArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.LEGGINGS, new Item.Settings())
        );
        WANDERERS_BOOTS = registerItemExcludeGroup("wanderers_boots",
            new ArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.BOOTS, new Item.Settings())
        );

        // Misc
        KNAPSACK = registerItem("knapsack", new KnapsackItem(new Item.Settings().maxCount(1)));
        QUIVER = registerItem("quiver", new TrinketItem(new Item.Settings().maxCount(1)));
        CRAFTING_EXPANDER = registerItem("crafting_expander", new SlotDisablerItem(new Item.Settings().maxCount(1),0,5));
        SCARF = registerItem("scarf", new ScarfItem(new Item.Settings().maxCount(1)));
    }

    public static HalberdItem registerHalberd(String name, ToolMaterial material, Item.Settings settings) {
        return registerItem(name,new HalberdItem(material, 5, -3f, settings));
    }

    public static RapierItem registerRapier(String name, ToolMaterial material, Item.Settings settings) {
        return registerItem(name,new RapierItem(material, 2, -2f, settings));
    }

    public static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registries.ITEM, id(name), item);
        ITEMS.add(item);
        INCLUDE_ITEMGROUP.add(item);
        return item;
    }

    public static <T extends Item> T registerItemExcludeGroup(String name, T item) {
        Registry.register(Registries.ITEM, id(name), item);
        ITEMS.add(item);
        return item;
    }

    public static void register(){}
}
