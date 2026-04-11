package com.destinedtowander.client;

import com.destinedtowander.common.index.ModComponents;
import com.destinedtowander.common.items.WanderersArmorItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class ClientUtil {

    public static void addSetBonus(List<Text> tooltip) {
        assert MinecraftClient.getInstance().player != null;
        if (ModComponents.WANDERER_COMPONENT.get(MinecraftClient.getInstance().player).hasSetBonus()) {
            tooltip.add(Text.translatable("item.thothpedia.set_bonus").formatted(Formatting.DARK_AQUA));
            tooltip.add(Text.translatable("item.thothpedia.wanderer_slow_falling").formatted(Formatting.DARK_GRAY));
        }
    }
}
