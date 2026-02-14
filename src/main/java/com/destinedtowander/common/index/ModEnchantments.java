package com.destinedtowander.common.index;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModEnchantments {
    private static Enchantment registerEnch(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, name, enchantment);
    }

    public static void register(){}

}
