package com.destinedtowander.mixin.inventory;

import com.destinedtowander.compat.coffins.ThothsEncyclopediaSupportImpl;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import powercyphe.coffins.modsupport.ModSupportManager;
import powercyphe.coffins.modsupport.ModSupporter;

import java.util.List;

@Mixin(ModSupportManager.class)
public class ModSupportManagerMixin {
    @Unique
    private static final ModSupporter thothpediaSupport = new ThothsEncyclopediaSupportImpl();

    @Inject(method = "getAllModSupportedDrops", at = @At("RETURN"))
    private static void addKnapsackContents(PlayerEntity player, CallbackInfoReturnable<List<ItemStack>> cir, @Local List<ItemStack> modSupportDrops) {
        modSupportDrops.addAll(thothpediaSupport.getDrops(player));
    }
}
