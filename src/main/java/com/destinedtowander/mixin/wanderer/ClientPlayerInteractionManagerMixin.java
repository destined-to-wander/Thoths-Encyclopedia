package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerInteractionManager.class)
public abstract class ClientPlayerInteractionManagerMixin {
    public ClientPlayerInteractionManagerMixin() {
    }

    @ModifyExpressionValue(
        method = "method_41929",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/ItemCooldownManager;isCoolingDown(Lnet/minecraft/item/Item;)Z"
        )
    )
    private boolean allowBlockingThroughCooldown(boolean value, Hand hand, PlayerEntity player) {
        ItemStack stack = player.getStackInHand(hand);
        return !stack.isOf(ModItems.WANDERERS_SWORD) && value;
    }
}
