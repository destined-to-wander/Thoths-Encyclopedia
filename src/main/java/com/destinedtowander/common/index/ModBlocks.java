package com.destinedtowander.common.index;

import com.destinedtowander.common.blocks.KilnBlock;
import com.destinedtowander.common.blocks.PyrotechnicsTableBlock;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
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

import static com.destinedtowander.ThothsEncyclopedia.id;
import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {
    public static final List<Item> BLOCKITEMS = new ArrayList<>();

    public static final Block PYROTECHNICS_TABLE = registerBlock("pyrotechnics_table",
            new PyrotechnicsTableBlock(FabricBlockSettings.create().mapColor(MapColor.ORANGE).instrument(Instrument.BASS).strength(2.5F,10F).sounds(BlockSoundGroup.WOOD))
    );

    public static final KilnBlock KILN = registerBlock("kiln",
            new KilnBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(3.5F).luminance(createLightLevelFromLitBlockState(13)))
    );

    public static <T extends Block> T registerBlock(String name, T block){
        Item blockItem = new BlockItem(block , new Item.Settings());
        BLOCKITEMS.add(blockItem);
        Registry.register(Registries.ITEM, id(name), blockItem);
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    public static void register(){}
}
