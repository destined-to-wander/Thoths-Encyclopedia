package com.destinedtowander.common.index;

import com.destinedtowander.common.items.*;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

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
    public static final Item WANDERERS_SWORD;
    public static final Item RAW_SCULKSTEEL;
    public static final Item SCULKSTEEL_INGOT;
    public static final Item WAYWARD_MEDLEY;
    public static final Item WAYWARD_CLOTH;
    public static final Item CINNAMON_HANGING_SIGN;
    public static final Item CINNAMON_SIGN;

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

        // Wood sets
        CINNAMON_HANGING_SIGN = registerItem("cinnamon_hanging_sign",
            new HangingSignItem(ModBlocks.CINNAMON_HANGING_SIGN,ModBlocks.CINNAMON_WALL_HANGING_SIGN, new Item.Settings().maxCount(16))
        );
        CINNAMON_SIGN = registerItem("cinnamon_sign",
            new SignItem(new Item.Settings().maxCount(16),ModBlocks.CINNAMON_SIGN,ModBlocks.CINNAMON_WALL_SIGN)
        );

        // Misc
        KNAPSACK = registerItem("knapsack", new KnapsackItem(new Item.Settings().maxCount(1)));
        QUIVER = registerItem("quiver", new TrinketItem(new Item.Settings().maxCount(1)));
        CRAFTING_EXPANDER = registerItem("crafting_expander", new SlotDisablerItem(new Item.Settings().maxCount(1),0,5));
        SCARF = registerItem("scarf", new ScarfItem(new Item.Settings().maxCount(1)));

        // Hidden stuff

        // Armor
        WANDERERS_HELMET = registerHiddenItem("wanderers_helmet",
            new WanderersArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.HELMET, new Item.Settings())
        );
        WANDERERS_CHESTPLATE = registerHiddenItem("wanderers_chestplate",
            new WanderersArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.CHESTPLATE, new Item.Settings())
        );
        WANDERERS_LEGGINGS = registerHiddenItem("wanderers_leggings",
            new WanderersArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.LEGGINGS, new Item.Settings())
        );
        WANDERERS_BOOTS = registerHiddenItem("wanderers_boots",
            new WanderersArmorItem(ModArmourMaterials.WANDERER, ArmorItem.Type.BOOTS, new Item.Settings())
        );

        WANDERERS_SWORD = registerHiddenItem("wanderers_sword", new WanderersSwordItem(new Item.Settings().rarity(Rarity.EPIC)));

        RAW_SCULKSTEEL = registerHiddenItem("raw_sculksteel", new Item(new Item.Settings()));
        SCULKSTEEL_INGOT = registerHiddenItem("sculksteel_ingot", new Item(new Item.Settings()));
        WAYWARD_MEDLEY = registerHiddenItem("wayward_medley", new Item(new Item.Settings()));
        WAYWARD_CLOTH = registerHiddenItem("wayward_cloth", new Item(new Item.Settings()));
    }

    private static HalberdItem registerHalberd(String name, ToolMaterial material, Item.Settings settings) {
        return registerItem(name,new HalberdItem(material, 5, -3f, settings));
    }

    private static RapierItem registerRapier(String name, ToolMaterial material, Item.Settings settings) {
        return registerItem(name,new RapierItem(material, 2, -2f, settings));
    }

    private static <T extends Item> T registerItem(String name, T item) {
        Registry.register(Registries.ITEM, id(name), item);
        ITEMS.add(item);
        INCLUDE_ITEMGROUP.add(item);
        return item;
    }

    private static <T extends Item> T registerHiddenItem(String name, T item) {
        Registry.register(Registries.ITEM, id(name), item);
        ITEMS.add(item);
        return item;
    }

    public static void register(){}
}
