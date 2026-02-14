package com.destinedtowander.common.index;

import com.destinedtowander.common.cca.KnapsackComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.entity.player.PlayerEntity;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModComponents implements EntityComponentInitializer {
    public static final ComponentKey<KnapsackComponent> KNAPSACK_COMPONENT = ComponentRegistry.getOrCreate(id("knapsack"), KnapsackComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
        registry.beginRegistration(PlayerEntity.class, KNAPSACK_COMPONENT).respawnStrategy(RespawnCopyStrategy.CHARACTER).end(KnapsackComponent::new);
    }
}
