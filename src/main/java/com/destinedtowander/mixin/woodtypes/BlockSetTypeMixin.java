package com.destinedtowander.mixin.woodtypes;

import com.destinedtowander.common.index.ModBlockSetTypes;
import net.minecraft.block.BlockSetType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockSetType.class)
public class BlockSetTypeMixin {

    @Shadow
    private static BlockSetType register(BlockSetType blockSetType) {
        throw new AssertionError();
    }

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void addCustomTypes(CallbackInfo ci) {
        register(ModBlockSetTypes.CINNAMON);
        register(ModBlockSetTypes.LEMON);
    }
}