package com.destinedtowander.mixin.inventory;

import com.destinedtowander.common.cca.KnapsackComponent;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.misc.ClearableCraftingInventory;
import com.destinedtowander.common.misc.KnapsackSlot;
import com.destinedtowander.common.misc.ToggleableSlot;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import dev.emi.trinkets.TrinketPlayerScreenHandler;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(value = PlayerScreenHandler.class, priority = 999)
public abstract class PlayerScreenHandlerMixin extends AbstractRecipeScreenHandler<CraftingInventory> implements ClearableCraftingInventory {
    public PlayerScreenHandlerMixin(ScreenHandlerType<?> screenHandlerType, int i) {
        super(screenHandlerType, i);
    }
    private final RecipeInputInventory expansionInput = new CraftingInventory(this, 3, 3);
    private final CraftingResultInventory expansionResult = new CraftingResultInventory();

    @Shadow
    PlayerEntity owner;

    @Shadow
    RecipeInputInventory craftingInput;

    @Shadow
    CraftingResultInventory craftingResult;

    int expansionResultSlot;

    @Inject(method = "<init>", at = @At("RETURN"))
    private void addKnapsackSlots(PlayerInventory playerInventory, boolean onServer, PlayerEntity owner, CallbackInfo ci) {
        expansionResultSlot = this.slots.size();
        this.addSlot(new CraftingResultSlot(this.owner, this.expansionInput, this.expansionResult, expansionResultSlot, 164, 31){
            @Override
            public boolean isEnabled() {
                Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(playerInventory.player);
                return component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(false);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.addSlot(new Slot(this.expansionInput, j + i * 3, 98 + j * 18, i * 18) {
                    @Override
                    public boolean isEnabled() {
                        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(playerInventory.player);
                        return component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(false);
                    }
                });
            }
        }

        SimpleInventory knapsack = KnapsackComponent.getInventory(this.owner);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < knapsack.size()/2; j++)
                this.addSlot(new KnapsackSlot(knapsack, j + i * knapsack.size()/2, 8 + j * 18, i * 18 - 46) {
                    @Override
                    public boolean isEnabled() {
                        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(playerInventory.player);
                        return component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.KNAPSACK)).orElse(false);
                    }
                });
    }

    @Override
    public void dropCraftingSlots(PlayerEntity player){
        this.expansionResult.clear();
        this.craftingResult.clear();
        if (!player.getWorld().isClient) {
            this.dropInventory(player, this.expansionInput);
            this.dropInventory(player, this.craftingInput);
        }
    }

    @Inject(method = "clearCraftingSlots", at = @At("RETURN"))
    public void clearExpanderSlots(CallbackInfo ci) {
        this.expansionResult.clear();
        this.expansionInput.clear();
    }

    @Inject(method = "matches", at = @At("HEAD"))
    public void expansionMatches(Recipe<? super RecipeInputInventory> recipe, CallbackInfoReturnable<Boolean> CiR) {
        if (recipe.matches(this.expansionInput, this.owner.getWorld()))
            CiR.setReturnValue(recipe.matches(this.expansionInput, this.owner.getWorld()));
    }

    @Inject(method = "onContentChanged", at = @At("HEAD"))
    public void updateExpansion(Inventory inventory, CallbackInfo ci) {
        CraftingScreenHandler.updateResult(this, this.owner.getWorld(), this.owner, this.expansionInput, this.expansionResult);
    }

    @Inject(method = "onClosed", at = @At("HEAD"))
    public void closeExpansion(PlayerEntity player, CallbackInfo ci) {
        this.expansionResult.clear();
        if (!player.getWorld().isClient) {
            this.dropInventory(player, this.expansionInput);
        }
    }

    @WrapMethod(method = "getCraftingWidth")
    private int returnCraftingWidth(Operation<Integer> original) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.owner);
        if (component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(false)) return this.expansionInput.getWidth();
        return original.call();
    }

    @WrapMethod(method = "getCraftingHeight")
    private int returnCraftingHeight(Operation<Integer> original) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.owner);
        if (component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(false)) return this.expansionInput.getHeight();
        return original.call();
    }

    @WrapMethod(method = "getCraftingInput")
    public RecipeInputInventory returnCraftingInput(Operation<RecipeInputInventory> original) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(this.owner);
        if (component.map(trinketComponent -> trinketComponent.isEquipped(ModItems.CRAFTING_EXPANDER)).orElse(false)) return this.expansionInput;
        return original.call();
    }

    @ModifyReturnValue(method = "canInsertIntoSlot(I)Z", at = @At("RETURN"))
    public boolean disableInsertResultSlot(boolean initial, int index) {
        return initial && index != expansionResultSlot;
    }

    @ModifyReturnValue(method = "canInsertIntoSlot(Lnet/minecraft/item/ItemStack;Lnet/minecraft/screen/slot/Slot;)Z", at = @At("RETURN"))
    public boolean disableInsertResultSlot(boolean initial, ItemStack stack, Slot slot) {
        return slot.inventory != this.expansionResult && initial;
    }
}
