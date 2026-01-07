package com.dest1ny.client.particle;

import com.dest1ny.ThothsEncyclopedia;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.dest1ny.ThothsEncyclopedia.id;

public interface ModParticles {
    public static final DefaultParticleType LUNGE_ATTACK = FabricParticleTypes.simple();


    private static void registerParticle(String name, DefaultParticleType particle) {
        Registry.register(Registries.PARTICLE_TYPE, id(name), particle);
    }

    public static void register(){
        registerParticle("lunge_attack",LUNGE_ATTACK);
        ThothsEncyclopedia.LOGGER.info("Registering Particles");
    };

    public static void registerFactories(){

    }

}
