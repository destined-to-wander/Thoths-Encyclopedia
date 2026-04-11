package com.destinedtowander.common.cca;

import com.destinedtowander.ThothsEncyclopedia;
import com.destinedtowander.common.index.ModComponents;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.items.WanderersArmorItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import dev.onyxstudios.cca.api.v3.component.tick.ServerTickingComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WanderersComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
    private final PlayerEntity player;
    private boolean blocking = false;
    private int parryDuration = 0;

    public WanderersComponent(PlayerEntity player) {
        this.player = player;
    }

    public static WanderersComponent get(@NotNull PlayerEntity player) {
        return ModComponents.WANDERER_COMPONENT.get(player);
    }

    public boolean hasSetBonus() {
        return getEquippedPieces() >= 4;
    }

    public float getEquippedPieces() {
        int equipped = 0;
        for (EquipmentSlot slot : SLOTS) {
            if (this.player.getEquippedStack(slot).getItem() instanceof WanderersArmorItem) equipped++;
        }
        return equipped;
    }

    @Override
    public void tick() {
        ItemStack stack = this.player.getMainHandStack();
        boolean isWanderersSword = this.player.isAlive() && !this.player.isDead() && !this.player.isRemoved() && stack.isOf(ModItems.WANDERERS_SWORD);
        if (this.isParrying()) {
            --this.parryDuration;
            if (this.parryDuration <= 0 || !isWanderersSword) {
                this.parryDuration = 0;
                this.sync();
            }
        }
    }

    public boolean isBlocking() {
        if (!this.player.getMainHandStack().isOf(ModItems.WANDERERS_SWORD) || !this.player.isUsingItem()) this.setBlocking(false);
        return this.blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
        this.sync();
    }

    public boolean isParrying() {
        return this.parryDuration > 0;
    }

    public void startParry() {
        this.parryDuration = 20;
        this.sync();
    }

    public void stopParry() {
        if (this.isParrying()) {
            this.parryDuration = 0;
            this.sync();
        }
    }

    public Entity getEntity() {
        return player;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
        this.blocking = tag.getBoolean("blocking");
        this.parryDuration = tag.getInt("parryDuration");
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        tag.putBoolean("blocking", this.blocking);
        tag.putInt("parryDuration", this.parryDuration);
    }

    public void sync() {
        ModComponents.WANDERER_COMPONENT.sync(this.player);
    }
}
