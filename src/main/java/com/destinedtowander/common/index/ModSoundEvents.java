package com.destinedtowander.common.index;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModSoundEvents {
    public static SoundEvent SWORD_PARRY;
    public static SoundEvent SWORD_BLOCK;

    static {
        SWORD_PARRY = registerSound("item.thothpedia.sword_parry");
        SWORD_BLOCK = registerSound("item.thothpedia.sword_block");
    }

    public static void register() {
    }

    static SoundEvent registerSound(String name) {
        Identifier soundID = id(name);
        return Registry.register(Registries.SOUND_EVENT, soundID, SoundEvent.of(soundID));
    }
}
