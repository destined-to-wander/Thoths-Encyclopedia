package com.destinedtowander.common.items;

import com.destinedtowander.client.ClientUtil;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class WanderersArmorItem extends ArmorItem {

    public WanderersArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (this.type == Type.CHESTPLATE) tooltip.add(Text.translatable("item.thothpedia.wanderer_chestplate_desc").formatted(Formatting.DARK_GRAY));
        ClientUtil.addSetBonus(tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
