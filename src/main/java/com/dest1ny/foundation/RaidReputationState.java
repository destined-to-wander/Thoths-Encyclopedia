package com.dest1ny.foundation;

import com.dest1ny.ThothsEncyclopedia;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class RaidReputationState extends PersistentState {

    public static final String RAIDREP_KEY = "raid_reputation";
    public Integer RaidReputation = 0;

    public RaidReputationState(){
        this.markDirty();
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putInt("raidreputation", RaidReputation);
        return nbt;
    }
}