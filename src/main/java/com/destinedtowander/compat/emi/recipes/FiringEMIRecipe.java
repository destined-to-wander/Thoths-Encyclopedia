package com.destinedtowander.compat.emi.recipes;

import com.destinedtowander.common.recipe.FiringRecipe;
import com.destinedtowander.compat.emi.EMIPlugin;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.render.EmiTexture;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.util.Identifier;

import java.util.List;

public class FiringEMIRecipe implements EmiRecipe {
    private final Identifier id;
    private final List<EmiIngredient> input;
    private final List<EmiStack> output;

    public FiringEMIRecipe(FiringRecipe recipe, List<EmiIngredient> inputs, EmiStack output) {
        this.id = recipe.getId();
        this.input = inputs;
        this.output = List.of(output);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIPlugin.FIRING;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return input;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return output;
    }

    @Override
    public int getDisplayWidth() {
        return 76;
    }

    @Override
    public int getDisplayHeight() {
        return 18;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        // Add an arrow texture to indicate processing
        widgets.addTexture(EmiTexture.EMPTY_ARROW, 26, 1);

        // Adds an input slot on the left
        widgets.addSlot(input.get(0), 0, 0);

        // Adds an output slot on the right
        // Note that output slots need to call `recipeContext` to inform EMI about their recipe context
        // This includes being able to resolve recipe trees, favorite stacks with recipe context, and more
        widgets.addSlot(output.get(0), 58, 0).recipeContext(this);
    }
}