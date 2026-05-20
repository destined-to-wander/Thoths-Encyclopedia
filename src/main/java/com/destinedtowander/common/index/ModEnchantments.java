package com.destinedtowander.common.index;

import com.destinedtowander.common.enchantments.HalberdEnchantment;
import com.destinedtowander.common.enchantments.WanderersEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.enchantments.AccumulateEnchantment;
import xyz.amymialee.amarite.enchantments.DoubleDashEnchantment;
import xyz.amymialee.amarite.enchantments.ReboundEnchantment;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModEnchantments {
    public static final Enchantment RAMPANT = registerEnch("rampant", new HalberdEnchantment());
    public static final Enchantment FOCUS = registerEnch("focus", new WanderersEnchantment());
    public static final Enchantment FEEDBACK = registerEnch("feedback", new WanderersEnchantment());

    private static Enchantment registerEnch(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, id(name), enchantment);
    }

    public static void register(){}

}
