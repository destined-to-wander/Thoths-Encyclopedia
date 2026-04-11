package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.cca.WanderersComponent;
import com.destinedtowander.common.index.ModItems;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public class PlayerEntityRendererMixin {

    @Inject(
        method = "getArmPose",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void swordPoses(AbstractClientPlayerEntity player, Hand hand, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack main = player.getMainHandStack();
        WanderersComponent sword = WanderersComponent.get(player);
        if (main.isOf(ModItems.WANDERERS_SWORD)) {
            boolean blocking = sword.isBlocking();
            boolean parrying = sword.isParrying();
            if (hand == Hand.MAIN_HAND) {
                if (parrying) {
                    cir.setReturnValue(BipedEntityModel.ArmPose.BLOCK);
                    cir.cancel();
                } else if (blocking) {
                    cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
                    cir.cancel();
                }
            } else if (parrying) {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
                cir.cancel();
            } else if (blocking) {
                cir.setReturnValue(BipedEntityModel.ArmPose.BOW_AND_ARROW);
                cir.cancel();
            }
        }
    }
}
