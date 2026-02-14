package com.destinedtowander.client.gui.screen.ingame;

import com.destinedtowander.client.gui.screen.recipebook.KilnRecipeBookScreen;
import com.destinedtowander.common.screens.KilnScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.destinedtowander.ThothsEncyclopedia.id;

@Environment(EnvType.CLIENT)
public class KilnScreen extends AbstractFurnaceScreen<KilnScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/container/kiln.png");

    public KilnScreen(KilnScreenHandler container, PlayerInventory inventory, Text title) {
        super(container, new KilnRecipeBookScreen(), inventory, title, TEXTURE);
    }
}
