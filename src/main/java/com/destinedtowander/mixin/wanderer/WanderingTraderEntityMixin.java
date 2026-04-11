package com.destinedtowander.mixin.wanderer;

import com.destinedtowander.common.index.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.WanderingTraderEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WanderingTraderEntity.class)
public abstract class WanderingTraderEntityMixin extends MerchantEntity {
    public WanderingTraderEntityMixin(EntityType<? extends MerchantEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(
        method = "afterUsing",
        at = @At("HEAD")
    )
    public void addWaywardClothTrade(TradeOffer offer, CallbackInfo cBInfo) {
        if (this.offers == null) return;
        for (TradeOffer listOffer : this.offers.stream().toList()) if (!listOffer.isDisabled()) return;
        if (this.offers.stream().noneMatch(o -> o.getSellItem().isOf(ModItems.WAYWARD_CLOTH))) {
            offers.add(new TradeOffer(
                new ItemStack(Items.EMERALD, 2),
                new ItemStack(ModItems.WAYWARD_CLOTH, 1),
                5,      // max uses
                1,      // experience
                0.05f   // price multiplier
            ));
        }
    }
}
