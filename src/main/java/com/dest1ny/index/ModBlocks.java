package com.dest1ny.index;

import com.dest1ny.blocks.PyrotechnicsTableBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.List;

import static com.dest1ny.ThothsEncyclopedia.id;

public class ModBlocks {
    public static final List<Item> BLOCKITEMS = new ArrayList<>();

    public static final Block PYROTECHNICS_TABLE = registerBlock("pyrotechnics_table",
            new PyrotechnicsTableBlock(FabricBlockSettings.create().mapColor(MapColor.ORANGE).instrument(Instrument.BASS).strength(2.5F,10F).sounds(BlockSoundGroup.WOOD))
    );

    public static Block registerBlock(String name, Block block){
        Item blockItem = new BlockItem(block , new Item.Settings());
        BLOCKITEMS.add(blockItem);
        Registry.register(Registries.ITEM, id(name), blockItem);
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    public static void register(){}
}
