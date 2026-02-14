package com.destinedtowander.client.render.entity;

import com.destinedtowander.common.items.ScarfItem;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class ScarfTrinketRenderer implements TrinketRenderer {

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel,
                       MatrixStack matrices, VertexConsumerProvider vertexConsumers,
                       int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        if (stack.getItem() instanceof ScarfItem &&
            contextModel instanceof PlayerEntityModel playerModel &&
            entity instanceof AbstractClientPlayerEntity player) {

            ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();

            // neck bits
            matrices.push();
            TrinketRenderer.followBodyRotations(entity, playerModel);
            TrinketRenderer.translateToChest(matrices, playerModel, player);

            // Translate into position
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

            ItemStack renderStack = stack.copy();
            renderStack.getOrCreateNbt().putInt("CustomModelData", 1);

            // Render
            itemRenderer.renderItem(
                renderStack,
                ModelTransformationMode.NONE, // uses display.none
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
            );
            matrices.pop();

            matrices.push();
            TrinketRenderer.followBodyRotations(entity, playerModel);
            TrinketRenderer.translateToFace(matrices, playerModel, player, headYaw, headPitch);

            // Translate into position
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180));

            // render neck part
            renderStack.getOrCreateNbt().putInt("CustomModelData", 2);

            // Render
            itemRenderer.renderItem(
                renderStack,
                ModelTransformationMode.NONE, // uses display.none
                light,
                OverlayTexture.DEFAULT_UV,
                matrices,
                vertexConsumers,
                entity.getWorld(),
                0
            );
            matrices.pop();
        }
    }
}
