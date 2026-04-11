package com.destinedtowander.compat.emi.recipes;

import com.destinedtowander.compat.emi.EMIPlugin;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.recipe.EmiRecipe;
import dev.emi.emi.api.recipe.EmiRecipeCategory;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.WidgetHolder;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class PyrotechnicsEMIRecipe implements EmiRecipe {
    protected final int unique = EmiUtil.RANDOM.nextInt();
    private static final Identifier BACKGROUND = id("textures/gui/container/pyrotechnics_table.png");

    private static final List<DyeItem> DYES = Stream.of(DyeColor.values()).map(DyeItem::byColor).toList();
    private static final List<Item> SHAPES;
    private static final List<Item> EFFECTS;

    static {
        SHAPES = List.of(Items.FIRE_CHARGE, Items.FEATHER, Items.GOLD_NUGGET, Items.SKELETON_SKULL, Items.WITHER_SKELETON_SKULL, Items.CREEPER_HEAD, Items.PLAYER_HEAD, Items.DRAGON_HEAD, Items.ZOMBIE_HEAD);
        EFFECTS = List.of(Items.GLOWSTONE_DUST, Items.DIAMOND);
    }

    protected final List<EmiIngredient> inputs = List.of(
        EmiStack.of(Items.GUNPOWDER),
        EmiIngredient.of(DYES.stream().map(EmiStack::of).collect(Collectors.toList())),
        EmiIngredient.of(SHAPES.stream().map(EmiStack::of).collect(Collectors.toList())),
        EmiIngredient.of(EFFECTS.stream().map(EmiStack::of).collect(Collectors.toList())),
        EmiStack.of(Items.FIREWORK_STAR));
    protected final EmiStack output = EmiStack.of(Items.FIREWORK_STAR);

    @Override
    public void addWidgets(WidgetHolder widgetHolder) {
        widgetHolder.addTexture(BACKGROUND, 0, 0, 160,72, 7, 13);
        int borderSlotSize = 18;

        //gunpowder slot
        widgetHolder.addSlot(EmiStack.of(Items.GUNPOWDER), 0, borderSlotSize);

        //type slot
        AtomicBoolean slotEmpty = new AtomicBoolean(false);
        widgetHolder.add(new GeneratedSlotWidget(r -> {
            Map<String,List<Item>> items = getItems(r);
            if (items.containsKey("Type")) return EmiStack.of(items.get("Type").get(0));
            slotEmpty.set(true);
            return EmiStack.EMPTY;
        }, unique, 22, 40));
        if (slotEmpty.get()) widgetHolder.addTexture(BACKGROUND, 23, 41, 16,16, 176, 0);

        // primary colors
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 2; ++column) {
                int inputIndex = row * 2 + column;
                widgetHolder.add(new GeneratedSlotWidget(r -> {
                    List<Item> dyes = getItems(r).get("Primary");
                    if (inputIndex < dyes.size()) return EmiStack.of(dyes.get(inputIndex));
                    return EmiStack.EMPTY;
                }, unique,44 + column * borderSlotSize, row * borderSlotSize)).drawBack(false);
            }
        }

        // secondary colors
        for (int row = 0; row < 4; ++row) {
            for (int column = 0; column < 2; ++column) {
                int inputIndex = row * 2 + column;
                widgetHolder.add(new GeneratedSlotWidget(r -> {
                    List<Item> dyes = getItems(r).get("Secondary");
                    if (dyes != null && inputIndex < dyes.size()) return EmiStack.of(dyes.get(inputIndex));
                    return EmiStack.EMPTY;
                }, unique,46 + (2 + column) * borderSlotSize, row * borderSlotSize)).drawBack(false);
            }
        }

        // glowstone slot
        slotEmpty.set(false);
        widgetHolder.add(new GeneratedSlotWidget(r -> {
            List<Item> effects = getItems(r).get("Effects");
            if (effects != null && effects.contains(Items.GLOWSTONE_DUST)) return EmiStack.of(Items.GLOWSTONE_DUST);
            slotEmpty.set(true);
            return EmiStack.EMPTY;
        }, unique,124, 16)).drawBack(false);
        if (slotEmpty.get()) widgetHolder.addTexture(BACKGROUND, 125, 17, 16,16, 192, 0);

        // diamond slot
        slotEmpty.set(false);
        widgetHolder.add(new GeneratedSlotWidget(r -> {
            List<Item> effects = getItems(r).get("Effects");
            if (effects != null && effects.contains(Items.DIAMOND)) return EmiStack.of(Items.DIAMOND);
            slotEmpty.set(true);
            return EmiStack.EMPTY;
        }, unique,142, 16)).drawBack(false);
        if (slotEmpty.get()) widgetHolder.addTexture(BACKGROUND, 143, 17, 16,16, 208, 0);

        widgetHolder.add(new GeneratedSlotWidget(this::getFireworkStar, this.unique, 133, 48)).drawBack(false);
    }

    private Map<String,List<Item>> getItems(Random random) {
        Map<String,List<Item>> items = Maps.newHashMap();
        int amount = random.nextInt(4);
        if (amount < 2) items.put("Effects",List.of(EFFECTS.get(amount)));
        else if (amount == 2) items.put("Effects",List.of(EFFECTS.get(0),EFFECTS.get(1)));

        amount = random.nextInt(SHAPES.size() + 1);
        if (amount < SHAPES.size()) items.put("Type",List.of(SHAPES.get(amount)));

        items.put("Primary",this.getDyes(random).stream().map(d -> (Item)d).toList());
        if (random.nextBoolean())
            items.put("Secondary",this.getDyes(random).stream().map(d -> (Item)d).toList());
        return items;
    }

    private List<DyeItem> getDyes(Random random) {
        List<DyeItem> dyes = Lists.newArrayList();
        int amount = 1 + random.nextInt(8);
        for (int i = 0; i < amount; i++) dyes.add(DYES.get(random.nextInt(DYES.size())));
        return dyes;
    }

    private EmiStack getFireworkStar(Random random) {
        ItemStack stack = new ItemStack(Items.FIREWORK_STAR);
        NbtCompound tag = new NbtCompound();
        NbtCompound explosion = new NbtCompound();
        Map<String,List<Item>> items = this.getItems(random);
        byte smallBall = 0;
        byte largeBall = 1;
        byte star = 2;
        byte creeper = 3;
        byte burst = 4;
        List<Integer> colors = Lists.newArrayList();
        List<Integer> fade = Lists.newArrayList();

        if (items.containsKey("Effects")) for(Item item : items.get("Effects")) {
            if (Items.GLOWSTONE_DUST.equals(item))
                explosion.putByte("Flicker", largeBall);
            else if (Items.DIAMOND.equals(item))
                explosion.putByte("Trail", largeBall);
        }

        if (items.containsKey("Type")) for(Item item : items.get("Type")) {
            if (Items.FIRE_CHARGE.equals(item))
                explosion.putByte("Type", largeBall);
            else if (Items.GOLD_NUGGET.equals(item))
                explosion.putByte("Type", star);
            else if (Items.FEATHER.equals(item))
                explosion.putByte("Type", burst);
            else if (SHAPES.contains(item))
                explosion.putByte("Type", creeper);
        } else explosion.putByte("Type", smallBall);

        for(Item item : items.get("Primary")) {
            DyeItem dyeItem = (DyeItem)item;
            colors.add(dyeItem.getColor().getFireworkColor());
        }

        explosion.putIntArray("Colors", colors);

        if (items.containsKey("Secondary")) {
            for (Item item : items.get("Secondary")) {
                DyeItem dyeItem = (DyeItem) item;
                fade.add(dyeItem.getColor().getFireworkColor());
            }
            explosion.putIntArray("FadeColors", fade);
        }

        tag.put("Explosion", explosion);
        stack.setNbt(tag);
        return EmiStack.of(stack);
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return EMIPlugin.FIREWORK_STAR_CRAFTING;
    }

    @Override
    public @Nullable Identifier getId() {
        return id("/firework_star_crafting");
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return inputs;
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(output);
    }

    @Override
    public int getDisplayWidth() {
        return 160;
    }

    @Override
    public int getDisplayHeight() {
        return 72;
    }

    @Override
    public boolean supportsRecipeTree() {
        return false;
    }
}
