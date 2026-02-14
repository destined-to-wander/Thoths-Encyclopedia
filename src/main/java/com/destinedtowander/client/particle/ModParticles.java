package com.destinedtowander.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.destinedtowander.ThothsEncyclopedia.id;

public interface ModParticles {
    public static final DefaultParticleType LUNGE_ATTACK = FabricParticleTypes.simple();


    private static void registerParticle(String name, DefaultParticleType particle) {
        Registry.register(Registries.PARTICLE_TYPE, id(name), particle);
    }

    public static void register(){
        registerParticle("lunge_attack",LUNGE_ATTACK);
    };

    public static void registerFactories(){

    }

}
