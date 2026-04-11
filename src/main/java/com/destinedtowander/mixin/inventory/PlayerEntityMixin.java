package com.destinedtowander.mixin.inventory;

import com.destinedtowander.common.cca.KnapsackComponent;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "dropInventory", at = @At(
        value = "INVOKE",
        target = "Lnet/minecraft/entity/player/PlayerInventory;dropAll()V"
    ))
    private void dropKnapsack(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity)(Object)this;
        SimpleInventory knapsack = KnapsackComponent.getInventory(player);
        if (FabricLoader.getInstance().isModLoaded("coffins") || knapsack.isEmpty()) return;
        for (int i = 0; i < knapsack.size(); i++) {
            ItemStack itemStack = knapsack.stacks.get(i);
            if (!itemStack.isEmpty()) {
                player.dropItem(itemStack, true, false);
                knapsack.stacks.set(i, ItemStack.EMPTY);
            }
        }
    }
}
