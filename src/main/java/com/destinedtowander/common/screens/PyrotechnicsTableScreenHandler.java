package com.destinedtowander.common.screens;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModScreenHandlers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.recipe.Ingredient;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.Util;

import java.util.List;
import java.util.Map;

public class PyrotechnicsTableScreenHandler extends ScreenHandler {
    private static final Ingredient TYPE_MODIFIER = Ingredient.ofItems(
            Items.FIRE_CHARGE,
            Items.FEATHER,
            Items.GOLD_NUGGET,
            Items.SKELETON_SKULL,
            Items.WITHER_SKELETON_SKULL,
            Items.CREEPER_HEAD,
            Items.PLAYER_HEAD,
            Items.DRAGON_HEAD,
            Items.ZOMBIE_HEAD,
            Items.PIGLIN_HEAD
    );
    private static final Ingredient TRAIL_MODIFIER = Ingredient.ofItems(Items.DIAMOND);
    private static final Ingredient FLICKER_MODIFIER = Ingredient.ofItems(Items.GLOWSTONE_DUST);
    private static final Map<Item, FireworkRocketItem.Type> TYPE_MODIFIER_MAP = Util.make(Maps.<Item, FireworkRocketItem.Type>newHashMap(), typeModifiers -> {
        typeModifiers.put(Items.FIRE_CHARGE, FireworkRocketItem.Type.LARGE_BALL);
        typeModifiers.put(Items.FEATHER, FireworkRocketItem.Type.BURST);
        typeModifiers.put(Items.GOLD_NUGGET, FireworkRocketItem.Type.STAR);
        typeModifiers.put(Items.SKELETON_SKULL, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.WITHER_SKELETON_SKULL, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.CREEPER_HEAD, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.PLAYER_HEAD, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.DRAGON_HEAD, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.ZOMBIE_HEAD, FireworkRocketItem.Type.CREEPER);
        typeModifiers.put(Items.PIGLIN_HEAD, FireworkRocketItem.Type.CREEPER);
    });
    private final ScreenHandlerContext context;

    private boolean isUpdating;

    final Slot gunpowderSlot;
    final Slot typeSlot;
    final Slot trailSlot;
    final Slot flickerSlot;

    long lastTakeResultTime;
    private final Inventory dyeInput = new SimpleInventory(8){
        @Override
        public void markDirty() {
            PyrotechnicsTableScreenHandler.this.onContentChanged(this);
            super.markDirty();
        }
    };
    private final Inventory fadeInput = new SimpleInventory(8){
        @Override
        public void markDirty() {
            PyrotechnicsTableScreenHandler.this.onContentChanged(this);
            super.markDirty();
        }
    };

    public final Inventory inventory = new SimpleInventory(4) {
        @Override
        public void markDirty() {
            PyrotechnicsTableScreenHandler.this.onContentChanged(this);
            super.markDirty();
        }
    };
    private final CraftingResultInventory resultInventory = new CraftingResultInventory() {
        @Override
        public void markDirty() {
            PyrotechnicsTableScreenHandler.this.onContentChanged(this);
            super.markDirty();
        }
    };

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory inventory) {
        this(syncId, inventory, ScreenHandlerContext.EMPTY);
    }

    public PyrotechnicsTableScreenHandler(int syncId, PlayerInventory inventory, ScreenHandlerContext context) {
        super(ModScreenHandlers.PYROTECHNICS_TABLE, syncId);
        this.context = context;
        // Gunpowder
        this.gunpowderSlot = this.addSlot(new Slot(this.inventory, 0, 8, 32) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.GUNPOWDER);
            }
        });
        // Shape
        this.typeSlot = this.addSlot(new Slot(this.inventory, 1, 30, 54) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return TYPE_MODIFIER.test(stack) || stack.isOf(Items.FIREWORK_STAR);
            }
        });
        // Flicker
        this.flickerSlot = this.addSlot(new Slot(this.inventory, 2, 132, 30) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return FLICKER_MODIFIER.test(stack);
            }
        });
        // Trail
        this.trailSlot = this.addSlot(new Slot(this.inventory, 3, 150, 30) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return TRAIL_MODIFIER.test(stack);
            }
        });

        // Dye Slots
        int slotIndex = 0;
        for (int x = 0; x < 2 ;x++){
            for (int y = 0; y < 4; y++){
                this.addSlot(new Slot(this.dyeInput, slotIndex++, x*18 + 52, y*18 + 14) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.getItem() instanceof DyeItem;
                    }
                });
            }
        }

        // Fade Slots
        slotIndex = 0;
        for (int x = 0; x < 2 ;x++){
            for (int y = 0; y < 4; y++){
                this.addSlot(new Slot(this.fadeInput, slotIndex++, x*18 + 90, y*18 + 14) {
                    @Override
                    public boolean canInsert(ItemStack stack) {
                        return stack.getItem() instanceof DyeItem;
                    }
                });
            }
        }

        //Output
        this.addSlot(new Slot(this.resultInventory, 20, 141, 62) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return false;
            }

            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                for (int i = 0; i < 20; i++) {
                    if (i == 1 && (gunpowderSlot.hasStack() || TYPE_MODIFIER.test(typeSlot.getStack()))) continue;
                    if (i >= 2 && i <= 3) continue;
                    PyrotechnicsTableScreenHandler.this.slots.get(i).takeStack(1);
                }
                stack.getItem().onCraft(stack, player.getWorld(), player);
                context.run((world, pos) -> {
                    long l = world.getTime();
                    if (PyrotechnicsTableScreenHandler.this.lastTakeResultTime != l) {
                        //world.playSound(null, pos, SoundEvents.UI_CARTOGRAPHY_TABLE_TAKE_RESULT, SoundCategory.BLOCKS, 1.0F, 1.0F);
                        PyrotechnicsTableScreenHandler.this.lastTakeResultTime = l;
                    }
                });
                super.onTakeItem(player, stack);
            }
        });

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlot(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 97 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlot(new Slot(inventory, i, 8 + i * 18, 155));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.PYROTECHNICS_TABLE);
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        if (isUpdating) return;
        isUpdating = true;
        ItemStack gunpowderSlot = this.inventory.getStack(0);
        ItemStack typeSlot = this.inventory.getStack(1);
        ItemStack flicker = this.inventory.getStack(2);
        ItemStack trail = this.inventory.getStack(3);
        ItemStack outputSlot = this.resultInventory.getStack(12);

        try{
            if ((typeSlot.isOf(Items.FIREWORK_STAR) && (!this.fadeInput.isEmpty() || !this.dyeInput.isEmpty() || !gunpowderSlot.isEmpty() || !flicker.isEmpty() || !trail.isEmpty())) || !gunpowderSlot.isEmpty() && !this.dyeInput.isEmpty()) {
                this.updateResult(gunpowderSlot, typeSlot, this.dyeInput, this.fadeInput, outputSlot, flicker, trail);
            } else {
                this.resultInventory.removeStack(20);
            }
        } finally{
            isUpdating = false;
        }
    }

    private void updateResult(ItemStack gunpowder, ItemStack typeModifier, Inventory dyeInput, Inventory fadeInput, ItemStack oldResult, ItemStack flicker, ItemStack trail) {
        ItemStack fireworkStar;
        if (!typeModifier.isOf(Items.FIREWORK_STAR)){
            fireworkStar = new ItemStack(Items.FIREWORK_STAR);
        } else {
            fireworkStar = typeModifier.copy();
            fireworkStar.setCount(1);
        }
        NbtCompound nbtCompound = fireworkStar.getOrCreateSubNbt("Explosion");
        FireworkRocketItem.Type type = FireworkRocketItem.Type.SMALL_BALL;
        List<Integer> colorList = Lists.newArrayList();
        List<Integer> fadeList = Lists.newArrayList();

        for (int i = 0; i < dyeInput.size(); i++){
            ItemStack dye = dyeInput.getStack(i);
            if (!dye.isEmpty()){
                colorList.add(((DyeItem)dye.getItem()).getColor().getFireworkColor());
            }
        }

        for (int i = 0; i < fadeInput.size(); i++){
            ItemStack dye = fadeInput.getStack(i);
            if (!dye.isEmpty()){
                fadeList.add(((DyeItem)dye.getItem()).getColor().getFireworkColor());
            }
        }

        if (!colorList.isEmpty()){
            nbtCompound.putIntArray("Colors", colorList);
        }
        if (!fadeList.isEmpty()){
            nbtCompound.putIntArray("FadeColors", fadeList);
        }

        if (typeModifier.isOf(Items.FIREWORK_STAR)) {
            type = FireworkRocketItem.Type.byId(typeModifier.getNbt().getByte("Type"));
        } else {
            if (TYPE_MODIFIER.test(typeModifier)){
                type = TYPE_MODIFIER_MAP.get(typeModifier.getItem());
            }
        }

        if (FLICKER_MODIFIER.test(flicker)) {
            nbtCompound.putBoolean("Flicker", true);
        } else if (typeModifier.isOf(Items.FIREWORK_STAR) && flicker.isEmpty()){
            nbtCompound.remove("Flicker");
        }
        if (TRAIL_MODIFIER.test(trail)) {
            nbtCompound.putBoolean("Trail", true);
        } else if (typeModifier.isOf(Items.FIREWORK_STAR) && trail.isEmpty()){
            nbtCompound.remove("Trail");
        }

        nbtCompound.putByte("Type", (byte)type.getId());
        if (!gunpowder.isEmpty()){
            fireworkStar.increment(1);
        }
        if (!ItemStack.areEqual(fireworkStar, oldResult)) {
            this.resultInventory.setStack(20, fireworkStar);
            this.sendContentUpdates();
        }
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.resultInventory && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        ItemStack movedItem = ItemStack.EMPTY;
        Slot movedSlot = this.slots.get(slot);

        if (movedSlot.hasStack()) {
            ItemStack stackMoved = movedSlot.getStack();
            movedItem = stackMoved.copy();

            if (slot == 20) {
                stackMoved.getItem().onCraft(stackMoved, player.getWorld(), player);
                if (!this.insertItem(stackMoved, 21, 57, true)) {
                    return ItemStack.EMPTY;
                }

                movedSlot.onQuickTransfer(stackMoved, movedItem);
            }else if (slot >= 21 && slot < 57) {
                // Moving from Player inventory → Pyrotechnics Table
                if (stackMoved.isOf(Items.GUNPOWDER)) {
                    if (!this.insertItem(stackMoved, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TYPE_MODIFIER.test(stackMoved) || stackMoved.isOf(Items.FIREWORK_STAR)) {
                    if (!this.insertItem(stackMoved, 1, 2, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (FLICKER_MODIFIER.test(stackMoved)) {
                    if (!this.insertItem(stackMoved, 2, 3, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (TRAIL_MODIFIER.test(stackMoved)) {
                    if (!this.insertItem(stackMoved, 3, 4, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (stackMoved.getItem() instanceof DyeItem) {
                    if (!this.insertItem(stackMoved, 4, 20, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            }
            else if (slot >= 0 && slot <= 19) {
                // Moving from Pyrotechnics Table → Player inventory
                if (!this.insertItem(stackMoved, 21, 57, false)) {
                    return ItemStack.EMPTY;
                }
            }

            if (stackMoved.isEmpty()) {
                movedSlot.setStack(ItemStack.EMPTY);
            }

            movedSlot.markDirty();
            if (stackMoved.getCount() == movedItem.getCount()) {
                return ItemStack.EMPTY;
            }

            movedSlot.onTakeItem(player, stackMoved);
            this.sendContentUpdates();
        }

        return movedItem;
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.resultInventory.removeStack(20);

        // Drop all relevant inventories using a loop
        List<Inventory> inventories = List.of(this.inventory, this.dyeInput, this.fadeInput);
        this.context.run((world, pos) -> inventories.forEach(inv -> this.dropInventory(player, inv)));
    }


    public Slot getGunpowderSlot() {
        return this.gunpowderSlot;
    }

    public Slot getTypeSlot() {
        return this.typeSlot;
    }

    public Slot getTrailSlot() {
        return this.trailSlot;
    }

    public Slot getFlickerSlot() {
        return this.flickerSlot;
    }
}
