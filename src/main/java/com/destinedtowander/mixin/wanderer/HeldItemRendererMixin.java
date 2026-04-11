package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.item.HeldItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
    @Inject(
        method = {"resetEquipProgress"},
        at = {@At("HEAD")},
        cancellable = true
    )
    private void wandererSwordPickup(Hand hand, CallbackInfo ci) {
        if (MinecraftClient.getInstance().player != null) {
            ItemStack stack = MinecraftClient.getInstance().player.getStackInHand(hand);
            if (stack.isOf(ModItems.WANDERERS_SWORD)) ci.cancel();
        }
    }
}
