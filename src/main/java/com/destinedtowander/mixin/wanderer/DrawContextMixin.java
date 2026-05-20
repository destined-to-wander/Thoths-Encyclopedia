package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.cca.WanderersComponent;
import com.destinedtowander.common.index.ModEnchantments;
import com.destinedtowander.common.index.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3i;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({DrawContext.class})
public abstract class DrawContextMixin {
    public DrawContextMixin() {
    }

    @Shadow
    public abstract void fill(RenderLayer layer, int x1, int y1, int x2, int y2, int color);

    @Inject(
        method = {"drawItemInSlot(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"},
        at = {@At("HEAD")}
    )
    private void thothpedia$wanderersSwordCharge(TextRenderer textRenderer, ItemStack stack, int x, int y, String countOverride, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            if (stack.isOf(ModItems.WANDERERS_SWORD) && MinecraftClient.getInstance().player.getActiveItem().equals(stack)) {
                WanderersComponent wanderersComponent = WanderersComponent.get(MinecraftClient.getInstance().player);
                if (wanderersComponent.isParrying()) {
                    float charge = wanderersComponent.getParryPercent();
                    int width = Math.round(charge * 14.F);
                    this.fill(RenderLayer.getGuiOverlay(), x + 2, y + 13, x + 15, y + 15, 0xFF000000);
                    int color = wanderersComponent.getChargeTint(stack);
                    this.fill(RenderLayer.getGuiOverlay(), x + 1, y + 13, x + 1 + width, y + 14, color | 0xFF000000);
                }
            }
        }
    }
}
