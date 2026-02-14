package com.destinedtowander.mixin;

import com.destinedtowander.ThothsEncyclopedia;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.mojang.datafixers.util.Pair;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.recipe.book.RecipeBookOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Mixin(RecipeBookOptions.class)
public class RecipeBookOptionsMixin {

    @Shadow
    @Final
    @Mutable
    private static Map<RecipeBookCategory, Pair<String, String>> CATEGORY_OPTION_NAMES;

    @Shadow
    @Final
    private Map<RecipeBookCategory, RecipeBookOptions.CategoryOption> categoryOptions;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void fdrf$modifyTagFields(CallbackInfo ci) {
        Map<RecipeBookCategory, Pair<String, String>> newMap = new HashMap<>(CATEGORY_OPTION_NAMES);
        newMap.put(ThothsEncyclopedia.RECIPE_TYPE_FIRING, Pair.of("isFiringGuiOpen", "isFiringFilteringCraftable"));
        CATEGORY_OPTION_NAMES = Map.copyOf(newMap);
    }

    @Inject(method = "<init>(Ljava/util/Map;)V", at = @At("TAIL"))
    private void fdrf$defaultCookingRecipeBookCategoryStates(CallbackInfo ci) {
        if (!categoryOptions.containsKey(ThothsEncyclopedia.RECIPE_TYPE_FIRING))
            categoryOptions.put(ThothsEncyclopedia.RECIPE_TYPE_FIRING, new RecipeBookOptions.CategoryOption(false, false));
    }

    @ModifyExpressionValue(method = "fromPacket(Lnet/minecraft/network/PacketByteBuf;)Lnet/minecraft/recipe/book/RecipeBookOptions;", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/book/RecipeBookCategory;values()[Lnet/minecraft/recipe/book/RecipeBookCategory;"))
    private static RecipeBookCategory[] fdrf$modifyReadFDRecipeBookOptionsToVanilla(RecipeBookCategory[] original) {
        return Arrays.stream(original).filter(RecipeBookCategory -> RecipeBookCategory != ThothsEncyclopedia.RECIPE_TYPE_FIRING).toArray(RecipeBookCategory[]::new);
    }

    @ModifyExpressionValue(method = "toPacket(Lnet/minecraft/network/PacketByteBuf;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/book/RecipeBookCategory;values()[Lnet/minecraft/recipe/book/RecipeBookCategory;"))
    private RecipeBookCategory[] fdrf$modifyWrittenFDRecipeBookOptionsToVanilla(RecipeBookCategory[] original) {
        return Arrays.stream(original).filter(RecipeBookCategory -> RecipeBookCategory != ThothsEncyclopedia.RECIPE_TYPE_FIRING).toArray(RecipeBookCategory[]::new);
    }
}
