package com.dest1ny.client.gui.screen.ingame;

import com.dest1ny.client.gui.screen.PyrotechnicsTableScreenHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.dest1ny.ThothsEncyclopedia.id;

@Environment(EnvType.CLIENT)
public class PyrotechnicsTableScreen extends HandledScreen<PyrotechnicsTableScreenHandler> {
    private static final Identifier TEXTURE = id("textures/gui/container/pyrotechnics_table.png");

    public PyrotechnicsTableScreen(PyrotechnicsTableScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.titleY -= 2;
        this.backgroundHeight = 179;
        this.playerInventoryTitleY = 84;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(context, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        this.renderBackground(context);
        int i = this.x;
        int j = this.y;
        context.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        Slot gunpowderSlot = this.handler.getGunpowderSlot();
        Slot typeSlot = this.handler.getTypeSlot();
        Slot flickerSlot = this.handler.getFlickerSlot();
        Slot trailSlot = this.handler.getTrailSlot();
        if (!gunpowderSlot.hasStack()) {
            context.drawTexture(TEXTURE, i + gunpowderSlot.x, j + gunpowderSlot.y, this.backgroundWidth+48, 0, 16, 16);
        }
        if (!typeSlot.hasStack()) {
            context.drawTexture(TEXTURE, i + typeSlot.x, j + typeSlot.y, this.backgroundWidth, 0, 16, 16);
        }
        if (!flickerSlot.hasStack()) {
            context.drawTexture(TEXTURE, i + flickerSlot.x, j + flickerSlot.y, this.backgroundWidth+16, 0, 16, 16);
        }
        if (!trailSlot.hasStack()) {
            context.drawTexture(TEXTURE, i + trailSlot.x, j + trailSlot.y, this.backgroundWidth+32, 0, 16, 16);
        }
    }
}
