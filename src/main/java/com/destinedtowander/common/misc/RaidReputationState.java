package com.destinedtowander.common.misc;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

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