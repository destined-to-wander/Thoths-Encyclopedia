package com.destinedtowander.common.items;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class WandererToolMaterial implements ToolMaterial {
    public static final ToolMaterial INSTANCE = new WandererToolMaterial();

    public WandererToolMaterial() {
    }

    public int getDurability() {
        return 0;
    }

    public float getMiningSpeedMultiplier() {
        return 10.5F;
    }

    public float getAttackDamage() {
        return 0.0F;
    }

    public int getMiningLevel() {
        return 4;
    }

    public int getEnchantability() {
        return 36;
    }

    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.PHANTOM_MEMBRANE);
    }
}

