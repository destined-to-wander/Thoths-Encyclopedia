package com.destinedtowander.mixin.inventory;

import com.destinedtowander.common.index.ModItems;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

import static com.destinedtowander.ThothsEncyclopedia.id;

@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> {
    @Unique
    private static final Identifier EXPANSIONS_1 = id("textures/gui/container/expansions/extensions_1.png");

    public InventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @WrapWithCondition(
        method = "drawForeground",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawText(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;IIIZ)I")
    )
    private boolean writeTitleIfDisplayed(DrawContext context, TextRenderer textRenderer, Text text, int x, int y, int color, boolean shadow) {
        if (this.client == null) return true;
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.client.player);
        return component.map(trinketComponent -> !trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(true);
    }

    @Inject(method = "drawForeground", at = @At(value = "TAIL"))
    private void drawTextras(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.client != null) {
            Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.client.player);
            boolean trinketEnabled = component.isPresent();
            if (trinketEnabled){
                if (component.get().isEquipped(ModItems.KNAPSACK)) context.drawText(
                    this.textRenderer, Text.translatable("thothpedia.container.knapsack"),
                    7, -59,
                    4210752, false
                );
            }
        }
    }

    @Inject(method = "drawBackground", at = @At(value = "TAIL"))
    private void drawExpansions(DrawContext context, float delta, int mouseX, int mouseY, CallbackInfo ci) {
        if (this.client != null) {
            Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.client.player);
            boolean trinketEnabled = component.isPresent();
            if (trinketEnabled) {
                if (component.get().isEquipped(ModItems.KNAPSACK)) context.drawTexture(EXPANSIONS_1,
                    this.x, this.y - 64,
                    0, 72, 176,60);
                if (component.get().isEquipped(ModItems.CRAFTING_EXPANDER)) context.drawTexture(EXPANSIONS_1,
                    this.x + 88, this.y - 10,
                    0, 0, 98, 72);
            }
        }
    }

    @ModifyReturnValue(method = "isClickOutsideBounds(DDIII)Z", at = @At("RETURN"))
    private boolean checkExpansions(boolean initial, double mouseX, double mouseY, int left, int top, int button) {
        if (!initial) return false;

        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.client.player);
        if (component.isPresent()) {
            if (component.get().isEquipped(ModItems.KNAPSACK)) {
                int knapsackY = top - 64;
                if (mouseX >= left && mouseX < left + 176
                    && mouseY >= knapsackY && mouseY < knapsackY + 60) return false;
            }

            if (component.get().isEquipped(ModItems.CRAFTING_EXPANDER)) {
                int expanderX = left + 88;
                int expanderY = top - 10;
                if (mouseX >= expanderX && mouseX < expanderX + 72 &&
                    mouseY >= expanderY && mouseY < expanderY + 16) return false;
                expanderX += 72;
                expanderY += 16;
                if (mouseX >= expanderX && mouseX < expanderX + 26 &&
                    mouseY >= expanderY && mouseY < expanderY + 40) return false;
                expanderX += 2;
                expanderY += 40;
                if (mouseX >= expanderX && mouseX < expanderX + 20 &&
                    mouseY >= expanderY && mouseY < expanderY + 3) return false;
            }
        }
        return true;
    }
}
