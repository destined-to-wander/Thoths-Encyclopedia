package com.destinedtowander.common.items;

import com.destinedtowander.common.cca.KnapsackComponent;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketItem;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class KnapsackItem extends TrinketItem {
    public KnapsackItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if (entity instanceof PlayerEntity player && !KnapsackComponent.getInventory(player).isEmpty()) return false;
        return !EnchantmentHelper.hasBindingCurse(stack);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        tooltip.add(Text.translatable("item.thothpedia.knapsack.tooltip").styled(style -> style.withColor(Formatting.AQUA)));
    }
}
