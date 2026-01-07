package com.dest1ny;

import com.dest1ny.client.particle.LungeAttackParticle;
import com.dest1ny.client.particle.ModParticles;
import com.dest1ny.index.ModScreens;
import net.fabricmc.api.ClientModInitializer;
/**/
//import net.fabricmc.client.particle.v1;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

//*/
public class ThothsEncyclopediaClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModScreens.register();
        // Particles
        ModParticles.register();
        ModParticles.registerFactories();
        ParticleFactoryRegistry.getInstance().register(ModParticles.LUNGE_ATTACK, LungeAttackParticle.Factory::new);
    }
}
