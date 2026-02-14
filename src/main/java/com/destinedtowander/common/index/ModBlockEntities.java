package com.destinedtowander.common.index;

import com.destinedtowander.common.blocks.entities.KilnBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModBlockEntities {
    public static final BlockEntityType<KilnBlockEntity> KILN =
            Registry.register(
                    Registries.BLOCK_ENTITY_TYPE,
                    id("kiln"),
                    FabricBlockEntityTypeBuilder.create(KilnBlockEntity::new, ModBlocks.KILN).build());

    public static void register(){}
}
