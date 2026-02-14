package com.destinedtowander.common.items;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class WanderersArmorItem extends ArmorItem {
    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);

    public WanderersArmorItem(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    public static float getEquippedPieces(LivingEntity owner) {
        if (owner != null) {
            int equipped = 0;
            for (EquipmentSlot slot : SLOTS) {
                if (owner.getEquippedStack(slot).getItem() instanceof WanderersArmorItem) {
                    equipped++;
                }
            }
            return equipped;
        }
        return 0;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(MialeeText.withColor(Text.translatable("item.thothpedia.rat_master_armor.tooltip.resistance"), 10044730));
        RatsMischiefClientHelper.addSetBonus(tooltip);
        super.appendTooltip(stack, world, tooltip, context);
    }
}
