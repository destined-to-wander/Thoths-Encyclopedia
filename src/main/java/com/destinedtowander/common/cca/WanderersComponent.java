package com.destinedtowander.common.cca;

import com.destinedtowander.common.index.ModComponents;
import com.destinedtowander.common.items.WanderersArmorItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public class WanderersComponent implements AutoSyncedComponent, ServerTickingComponent, CommonTickingComponent {
    private static final int MAX_TIME_ON_GROUND = 100;
    private final PlayerEntity player;
    private boolean wandererSetBonus = false;

    public WanderersComponent(PlayerEntity player) {
        this.player = player;
    }

    public Entity getEntity() {
        return player;
    }

    public boolean hasSetBonus() {
        return wandererSetBonus;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
        wandererSetBonus = tag.getBoolean("wandererSetBonus");
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        tag.putBoolean("wandererSetBonus", wandererSetBonus);
    }

    @Override
    public void tick() {
        if (WanderersArmorItem.getEquippedPieces(player) >= 4) {

        }
    }

    public void sync() {
        ModComponents.WANDERER_COMPONENT.sync(player);
    }
}
