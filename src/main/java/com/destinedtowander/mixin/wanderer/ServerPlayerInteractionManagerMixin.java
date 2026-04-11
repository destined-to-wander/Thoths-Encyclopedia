package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {
    public ServerPlayerInteractionManagerMixin() {
    }

    @ModifyExpressionValue(
        method = "interactItem",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/entity/player/ItemCooldownManager;isCoolingDown(Lnet/minecraft/item/Item;)Z"
        )
    )
    private boolean allowBlockingThroughCooldown(boolean value, ServerPlayerEntity player, World world, ItemStack stack) {
        return !stack.isOf(ModItems.WANDERERS_SWORD) && value;
    }
}
