package com.destinedtowander;

import com.destinedtowander.client.gui.screen.recipebook.RecipeBookGroups;
import com.destinedtowander.client.particle.LungeAttackParticle;
import com.destinedtowander.client.particle.ModParticles;
import com.destinedtowander.client.render.entity.ScarfTrinketRenderer;
import com.destinedtowander.client.render.item.HalberdDynamicItemRenderer;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.index.ModScreens;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.item.DyeableItem;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ThothsEncyclopediaClient implements ClientModInitializer {
    public static final EntityModelLayer SCARF_LAYER = new EntityModelLayer(id("scarf"), "main");

    @Override
    public void onInitializeClient() {
        // Built-in Item Renderers
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.DIAMOND_HALBERD, new HalberdDynamicItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.IRON_HALBERD, new HalberdDynamicItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.GOLD_HALBERD, new HalberdDynamicItemRenderer());
        BuiltinItemRendererRegistry.INSTANCE.register(ModItems.NETHERITE_HALBERD, new HalberdDynamicItemRenderer());

        // Force load the weapon models (otherwise since they're never called they wouldn't be loaded by default)
        ModelLoadingPlugin.register(pluginContext -> pluginContext.addModels(HalberdDynamicItemRenderer.MODELS_TO_REGISTER));

        // Recipebook
        RecipeBookGroups.init();

        // Particles
        ModParticles.register();
        ModParticles.registerFactories();
        ParticleFactoryRegistry.getInstance().register(ModParticles.LUNGE_ATTACK, LungeAttackParticle.Factory::new);

        // Pyro table
        ModScreens.register();

        // Scarves
        TrinketRendererRegistry.registerRenderer(ModItems.SCARF, new ScarfTrinketRenderer());
        ColorProviderRegistry.ITEM.register((stack, tintIndex) -> tintIndex > 0 ? -1 : ((DyeableItem) stack.getItem()).getColor(stack), ModItems.SCARF);
    }
}
