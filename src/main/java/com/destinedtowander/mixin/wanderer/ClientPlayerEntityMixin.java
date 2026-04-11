package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin {
    public ClientPlayerEntityMixin() {
    }

    @WrapOperation(
        method = {"tickMovement"},
        at = {@At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
        )}
    )
    private boolean noSwordSlowdown(ClientPlayerEntity player, Operation<Boolean> original) {
        return original.call(player) && !player.getActiveItem().isOf(ModItems.WANDERERS_SWORD);
    }
}
