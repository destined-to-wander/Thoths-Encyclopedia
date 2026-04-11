package com.destinedtowander.compat.emi;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModItems;
import com.destinedtowander.common.index.ModRecipeTypes;
import com.destinedtowander.common.recipe.FiringRecipe;
import com.destinedtowander.compat.emi.recipes.FiringEMIRecipe;
import com.destinedtowander.compat.emi.recipes.PyrotechnicsEMIRecipe;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class EMIPlugin implements EmiPlugin {
    public static final Identifier MY_SPRITE_SHEET = id("textures/gui/emi/simplified_textures.png");
    public static final EmiStack PYROTECHNICS_TABLE = EmiStack.of(ModBlocks.PYROTECHNICS_TABLE);
    public static final EmiStack KILN = EmiStack.of(ModBlocks.KILN);
    public static final EmiRecipeCategory FIREWORK_STAR_CRAFTING =
        new EmiRecipeCategory(
            id("firework_star_crafting"),
            PYROTECHNICS_TABLE,
            new EmiTexture(MY_SPRITE_SHEET, 0, 0, 16, 16)
        );
    public static final EmiRecipeCategory FIRING =
        new EmiRecipeCategory(
            id("firing"),
            KILN,
            new EmiTexture(MY_SPRITE_SHEET, 16, 0, 16, 16)
        );

    @Override
    public void register(EmiRegistry registry) {
        // Removes wanderer set items
        registry.removeEmiStacks(EmiStack.of(ModItems.WANDERERS_HELMET));
        registry.removeEmiStacks(EmiStack.of(ModItems.WANDERERS_CHESTPLATE));
        registry.removeEmiStacks(EmiStack.of(ModItems.WANDERERS_LEGGINGS));
        registry.removeEmiStacks(EmiStack.of(ModItems.WANDERERS_BOOTS));
        registry.removeEmiStacks(EmiStack.of(ModItems.WANDERERS_SWORD));
        registry.removeEmiStacks(EmiStack.of(ModItems.WAYWARD_CLOTH));
        registry.removeEmiStacks(EmiStack.of(ModItems.WAYWARD_MEDLEY));
        registry.removeEmiStacks(EmiStack.of(ModItems.RAW_SCULKSTEEL));
        registry.removeEmiStacks(EmiStack.of(ModItems.SCULKSTEEL_INGOT));
        registry.removeEmiStacks(EmiStack.of(ModBlocks.SCULK_CHARGED_AMALGAM));

        registry.addCategory(FIREWORK_STAR_CRAFTING);
        registry.addWorkstation(FIREWORK_STAR_CRAFTING, PYROTECHNICS_TABLE);

        registry.addCategory(FIRING);
        registry.addWorkstation(FIRING, KILN);

        for (FiringRecipe recipe : registry.getRecipeManager().listAllOfType(ModRecipeTypes.FIRING)) {
            assert MinecraftClient.getInstance().world != null;
            registry.addRecipe(new FiringEMIRecipe(
                recipe,
                recipe.getIngredients().stream().map(EmiIngredient::of).toList(),
                EmiStack.of(recipe.getOutput(MinecraftClient.getInstance().world.getRegistryManager()))
            ));
        }

        registry.addRecipe(new PyrotechnicsEMIRecipe());
    }
}
