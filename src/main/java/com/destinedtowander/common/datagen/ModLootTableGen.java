package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.registry.Registries;

public class ModLootTableGen extends FabricBlockLootTableProvider {
    protected ModLootTableGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PYROTECHNICS_TABLE);
        addDrop(ModBlocks.KILN);

        addDrop(ModBlocks.CINNAMON_PLANKS);
        addDrop(ModBlocks.CINNAMON_LOG);
        addDrop(ModBlocks.CINNAMON_WOOD);
        addDrop(ModBlocks.STRIPPED_CINNAMON_LOG);
        addDrop(ModBlocks.STRIPPED_CINNAMON_WOOD);
        addDrop(ModBlocks.CINNAMON_BUTTON);
        addDrop(ModBlocks.CINNAMON_DOOR, doorDrops(ModBlocks.CINNAMON_DOOR));
        addDrop(ModBlocks.CINNAMON_TRAPDOOR);
        addDrop(ModBlocks.CINNAMON_STAIRS);
        addDrop(ModBlocks.CINNAMON_SLAB, slabDrops(ModBlocks.CINNAMON_SLAB));
        addDrop(ModBlocks.CINNAMON_FENCE);
        addDrop(ModBlocks.CINNAMON_FENCE_GATE);
        addDrop(ModBlocks.CINNAMON_SIGN);
        addDrop(ModBlocks.CINNAMON_HANGING_SIGN);
        addDrop(ModBlocks.CINNAMON_SAPLING);
        addDrop(ModBlocks.CINNAMON_PRESSURE_PLATE);
        addDrop(ModBlocks.CINNAMON_LEAVES,
            leavesDrops(ModBlocks.CINNAMON_LEAVES, ModBlocks.CINNAMON_SAPLING, SAPLING_DROP_CHANCE)
        );
        addPottedPlantDrops(ModBlocks.POTTED_CINNAMON_SAPLING);

        LootTable.Builder amalgamTable = dropsWithSilkTouch(
            ModBlocks.SCULK_CHARGED_AMALGAM,
            applyExplosionDecay(
                ModBlocks.SCULK_CHARGED_AMALGAM,
                ItemEntry.builder(ModItems.RAW_SCULKSTEEL)
                    .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(3)))
            )
        );
        addDrop(ModBlocks.SCULK_CHARGED_AMALGAM, amalgamTable);

        System.out.println(Registries.BLOCK.getId(ModBlocks.CINNAMON_PLANKS));
    }
}