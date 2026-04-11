package com.destinedtowander.common.index;

import net.fabricmc.fabric.api.object.builder.v1.trade.TradeOfferHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffer;

public class ModTrades {
    public static void register(){
        TradeOfferHelper.registerWanderingTraderOffers(2, factories -> {
            factories.add((entity, random) ->
                new TradeOffer(
                    new ItemStack(Items.EMERALD, 1), // cost
                    new ItemStack(Items.LEATHER, 4), // result
                    5,   // max uses
                    1,   // experience
                    0.05f // price multiplier
                )
            );
        });
    }
}
