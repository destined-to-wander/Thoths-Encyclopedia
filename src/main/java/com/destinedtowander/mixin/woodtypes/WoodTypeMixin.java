package com.destinedtowander.mixin.woodtypes;

import com.destinedtowander.common.index.ModWoodTypes;
import net.minecraft.block.WoodType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WoodType.class)
public class WoodTypeMixin {

    @Shadow
    private static WoodType register(WoodType woodType) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addCustomTypes(CallbackInfo ci) {
        register(ModWoodTypes.CINNAMON);
        register(ModWoodTypes.LEMON);
    }
}
