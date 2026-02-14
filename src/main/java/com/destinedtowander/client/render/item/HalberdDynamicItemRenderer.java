package com.destinedtowander.client.render.item;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.items.HalberdItem;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.DiffuseLighting;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterials;
import net.minecraft.util.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class HalberdDynamicItemRenderer implements BuiltinItemRendererRegistry.DynamicItemRenderer {
    public static final List<ModelIdentifier> MODELS_TO_REGISTER = new ArrayList<>();

    public static final Pair<ModelIdentifier, ModelIdentifier> GOLD_MODEL_IDENTIFIER = registerVariantModelPair("golden");
    public static final Pair<ModelIdentifier, ModelIdentifier> IRON_MODEL_IDENTIFIER = registerVariantModelPair("iron");
    public static final Pair<ModelIdentifier, ModelIdentifier> DIAMOND_MODEL_IDENTIFIER = registerVariantModelPair("diamond");
    public static final Pair<ModelIdentifier, ModelIdentifier> NETHERITE_MODEL_IDENTIFIER = registerVariantModelPair("netherite");

    private static @NotNull Pair<ModelIdentifier, ModelIdentifier> registerVariantModelPair(String name) {
        String s = name + (name.isEmpty() ? "" : "_") + "halberd";

        ModelIdentifier inventoryModelIdentifier = new ModelIdentifier(ThothsEncyclopedia.id(s + "_inventory"), "inventory");
        ModelIdentifier inHandModelIdentifier = new ModelIdentifier(ThothsEncyclopedia.id(s + "_in_hand"), "inventory");

        MODELS_TO_REGISTER.add(inventoryModelIdentifier);
        MODELS_TO_REGISTER.add(inHandModelIdentifier);

        return new Pair<>(inventoryModelIdentifier, inHandModelIdentifier);
    }

    private static @NotNull Pair<ModelIdentifier, ModelIdentifier> getModelIdentifierModelIdentifierPair(ItemStack stack) {
        Pair<ModelIdentifier, ModelIdentifier> modelIdentifierPair = IRON_MODEL_IDENTIFIER;

        if(stack.getItem() instanceof HalberdItem halberd){
            if(ToolMaterials.GOLD.equals(halberd.getMaterial())){
                modelIdentifierPair = GOLD_MODEL_IDENTIFIER;
            } else if(ToolMaterials.DIAMOND.equals(halberd.getMaterial())){
                modelIdentifierPair = DIAMOND_MODEL_IDENTIFIER;
            } else if(ToolMaterials.NETHERITE.equals(halberd.getMaterial())){
                modelIdentifierPair = NETHERITE_MODEL_IDENTIFIER;
            }
        }
        return modelIdentifierPair;
    }

    @Override
    public void render(ItemStack stack, ModelTransformationMode mode, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        boolean inHand = mode.isFirstPerson() || mode == ModelTransformationMode.THIRD_PERSON_LEFT_HAND || mode == ModelTransformationMode.THIRD_PERSON_RIGHT_HAND || mode == ModelTransformationMode.HEAD || mode == ModelTransformationMode.FIXED;
        boolean inInventory = mode == ModelTransformationMode.GUI;

        matrices.push();
        matrices.translate(.5, .5, .5);

        Pair<ModelIdentifier, ModelIdentifier> modelIdentifierPair = getModelIdentifierModelIdentifierPair(stack);
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(!inHand ? modelIdentifierPair.getLeft() : modelIdentifierPair.getRight());

        if (inInventory) {
            DiffuseLighting.disableGuiDepthLighting();
        }

        MinecraftClient.getInstance().getItemRenderer().renderItem(stack, mode, false, matrices, vertexConsumers, light, overlay, model);
        if (vertexConsumers instanceof VertexConsumerProvider.Immediate immediate) {
            immediate.draw();
        }

        if (inInventory) {
            DiffuseLighting.enableGuiDepthLighting();
        }

        matrices.pop();
    }
}
