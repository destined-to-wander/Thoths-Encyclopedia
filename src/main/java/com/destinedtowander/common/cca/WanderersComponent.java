package com.destinedtowander.common.cca;

import com.destinedtowander.common.index.ModComponents;
import com.destinedtowander.common.index.ModEnchantments;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.items.WanderersArmorItem;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class WanderersComponent implements AutoSyncedComponent, CommonTickingComponent {
    public static final Set<EquipmentSlot> SLOTS = Set.of(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET);
    private final PlayerEntity player;
    private boolean blocking = false;
    private float parryCharge = 20.F;
    public static final Vec3i PARRY_BAR_COLOR = new Vec3i(26, 135, 160);
    public static final Vec3i FOCUS_BAR_COLOR = new Vec3i(53, 200, 136);
    public static final Vec3i FEEDBACK_BAR_COLOR = new Vec3i(86, 110, 239);

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

        boolean hasFocus = EnchantmentHelper.getLevel(ModEnchantments.FOCUS, stack) > 0;
        if (!this.isParrying()) return;

        if (hasFocus) --this.parryCharge;
        if (this.parryCharge <= 0 || !isWanderersSword) stopParry();
    }

    public void absorbDamage(float base) {
        this.parryCharge -= base;
        if (this.parryCharge < 0) stopParry();
        this.sync();
    }

    public boolean isBlocking() {
        if (!this.player.getMainHandStack().isOf(ModItems.WANDERERS_SWORD) || !this.player.isUsingItem()) this.setBlocking(false);
        return this.blocking;
    }

    public void setBlocking(boolean blocking) {
        this.blocking = blocking;
        this.sync();
    }

    public void startParry() {
        this.parryCharge = 20;
        this.sync();
    }

    public void startFeedback() {
        this.parryCharge = 5;
        this.sync();
    }

    public void stopParry() {
        if (this.isParrying()) {
            this.parryCharge = 0;
        }
        this.sync();
    }

    public float getParryPercent() {
        return this.parryCharge / 20.0F;
    }

    public boolean isParrying() {
        return this.parryCharge > 0;
    }

    public int getChargeTint(ItemStack stack) {
        Vec3i color;
        if (EnchantmentHelper.getLevel(ModEnchantments.FOCUS, stack) > 0) {
            color = FOCUS_BAR_COLOR;
        } else if (EnchantmentHelper.getLevel(ModEnchantments.FEEDBACK, stack) > 0) {
            color = FEEDBACK_BAR_COLOR;
        } else {
            color = PARRY_BAR_COLOR;
        }

        return color.getX() << 16 | color.getY() << 8 | color.getZ();
    }

    public Entity getEntity() {
        return player;
    }

    @Override
    public void readFromNbt(@NotNull NbtCompound tag) {
        this.blocking = tag.getBoolean("blocking");
        this.parryCharge = tag.getFloat("parryCharge");
    }

    @Override
    public void writeToNbt(@NotNull NbtCompound tag) {
        tag.putBoolean("blocking", this.blocking);
        tag.putFloat("parryCharge", this.parryCharge);
    }

    public void sync() {
        ModComponents.WANDERER_COMPONENT.sync(this.player);
    }
}
