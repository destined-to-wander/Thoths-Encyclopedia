package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;

public class ModLootTableGen extends FabricBlockLootTableProvider {
    protected ModLootTableGen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.PYROTECHNICS_TABLE);
        addDrop(ModBlocks.KILN);
    }
}