package com.destinedtowander.common.index;

import com.destinedtowander.common.blocks.CinnamonSaplingGenerator;
import com.destinedtowander.common.blocks.KilnBlock;
import com.destinedtowander.common.blocks.PyrotechnicsTableBlock;
import com.destinedtowander.common.blocks.SculkChargedAmalgamBlock;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.enums.Instrument;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.block.sapling.JungleSaplingGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;

import java.util.ArrayList;
import java.util.List;

import static com.destinedtowander.ThothsEncyclopedia.id;
import static net.minecraft.block.Blocks.*;

public class ModBlocks {
    public static final List<Item> BLOCKITEMS = new ArrayList<>();

    // Cinnamon Wood Set
    public static final Block CINNAMON_LOG = registerBlock("cinnamon_log",
        createLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN)
    );
    public static final Block CINNAMON_WOOD = registerBlock(
        "cinnamon_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_WOOD).mapColor(MapColor.ORANGE))
    );
    public static final Block STRIPPED_CINNAMON_LOG = registerBlock("stripped_cinnamon_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_JUNGLE_LOG).mapColor(MapColor.ORANGE))
    );
    public static final Block STRIPPED_CINNAMON_WOOD = registerBlock("stripped_cinnamon_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_JUNGLE_LOG).mapColor(MapColor.ORANGE))
    );
    public static final Block CINNAMON_PLANKS = registerBlock("cinnamon_planks",
        new Block(FabricBlockSettings.copyOf(Blocks.JUNGLE_PLANKS).mapColor(MapColor.ORANGE).burnable())
    );
    public static final Block CINNAMON_STAIRS = registerBlock("cinnamon_stairs",
        new StairsBlock(CINNAMON_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(CINNAMON_PLANKS))
    );
    public static final Block CINNAMON_SLAB = registerBlock("cinnamon_slab",
        new SlabBlock(FabricBlockSettings.copyOf(Blocks.JUNGLE_SLAB).mapColor(MapColor.ORANGE))
    );
    public static final Block CINNAMON_FENCE = registerBlock("cinnamon_fence",
        new FenceBlock(
            FabricBlockSettings.copyOf(JUNGLE_FENCE).mapColor(CINNAMON_PLANKS.getDefaultMapColor())
        )
    );
    public static final Block CINNAMON_FENCE_GATE = registerBlock("cinnamon_fence_gate",
        new FenceGateBlock(
            FabricBlockSettings.copyOf(JUNGLE_FENCE_GATE).mapColor(CINNAMON_PLANKS.getDefaultMapColor()),
            ModWoodTypes.CINNAMON
        )
    );
    public static final Block CINNAMON_DOOR = registerBlock("cinnamon_door",
        new DoorBlock(
            FabricBlockSettings.copyOf(JUNGLE_DOOR).mapColor(CINNAMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.CINNAMON
        )
    );
    public static final Block CINNAMON_TRAPDOOR = registerBlock("cinnamon_trapdoor",
        new TrapdoorBlock(FabricBlockSettings.copyOf(JUNGLE_TRAPDOOR).mapColor(CINNAMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.CINNAMON
        )
    );
    public static final Block CINNAMON_PRESSURE_PLATE = registerBlock("cinnamon_pressure_plate",
        new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            FabricBlockSettings.copyOf(JUNGLE_PRESSURE_PLATE).mapColor(CINNAMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.CINNAMON
        )
    );
    public static final Block CINNAMON_BUTTON = registerBlock("cinnamon_button", createWoodenButtonBlock(ModBlockSetTypes.CINNAMON));

    // Other Cinnamon Stuff
    public static final Block CINNAMON_SAPLING = registerBlock("cinnamon_sapling",
        new SaplingBlock(
            new CinnamonSaplingGenerator(),
            AbstractBlock.Settings.create()
                .mapColor(MapColor.DARK_GREEN)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.GRASS)
                .pistonBehavior(PistonBehavior.DESTROY)
        )
    );
    public static final Block CINNAMON_LEAVES = registerBlock("cinnamon_leaves", createLeavesBlock(BlockSoundGroup.GRASS));

    public static final Block CINNAMON_SIGN = registerNoItemBlock("cinnamon_sign",
        new SignBlock(FabricBlockSettings.copyOf(JUNGLE_SIGN).mapColor(CINNAMON_LOG.getDefaultMapColor()), ModWoodTypes.CINNAMON)
    );
    public static final Block CINNAMON_WALL_SIGN = registerNoItemBlock("cinnamon_wall_sign",
        new WallSignBlock(FabricBlockSettings.copyOf(JUNGLE_WALL_SIGN).mapColor(CINNAMON_LOG.getDefaultMapColor()).dropsLike(CINNAMON_SIGN), ModWoodTypes.CINNAMON)
    );
    public static final Block CINNAMON_HANGING_SIGN = registerNoItemBlock("cinnamon_hanging_sign",
        new HangingSignBlock(
            FabricBlockSettings.copyOf(JUNGLE_HANGING_SIGN).mapColor(CINNAMON_LOG.getDefaultMapColor()),
            ModWoodTypes.CINNAMON
        )
    );
    public static final Block CINNAMON_WALL_HANGING_SIGN = registerNoItemBlock("cinnamon_wall_hanging_sign",
        new WallHangingSignBlock(
            FabricBlockSettings.copyOf(JUNGLE_WALL_HANGING_SIGN).mapColor(CINNAMON_LOG.getDefaultMapColor()).dropsLike(CINNAMON_HANGING_SIGN),
            ModWoodTypes.CINNAMON
        )
    );
    public static final Block POTTED_CINNAMON_SAPLING = registerNoItemBlock("potted_cinnamon_sapling", createFlowerPotBlock(CINNAMON_SAPLING));

    // Functional Blocks
    public static final Block PYROTECHNICS_TABLE = registerBlock("pyrotechnics_table",
            new PyrotechnicsTableBlock(FabricBlockSettings.create().mapColor(MapColor.ORANGE).instrument(Instrument.BASS).strength(2.5F,10F).sounds(BlockSoundGroup.WOOD))
    );

    public static final KilnBlock KILN = registerBlock("kiln",
            new KilnBlock(FabricBlockSettings.copyOf(Blocks.BRICKS).strength(3.5F).luminance(createLightLevelFromLitBlockState(13)))
    );

    // Wanderer set blocks
    public static final Block SCULK_CHARGED_AMALGAM = registerHiddenBlock("sculk_charged_amalgam",
        new SculkChargedAmalgamBlock(FabricBlockSettings.copyOf(Blocks.RAW_IRON_BLOCK))
    );

    public static <T extends Block> T registerBlock(String name, T block){
        Item blockItem = new BlockItem(block , new Item.Settings());
        BLOCKITEMS.add(blockItem);
        Registry.register(Registries.ITEM, id(name), blockItem);
        return registerNoItemBlock(name, block);
    }

    public static <T extends Block> T registerNoItemBlock(String name, T block){
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    public static <T extends Block> T registerHiddenBlock(String name, T block){
        Item blockItem = new BlockItem(block , new Item.Settings());
        Registry.register(Registries.ITEM, id(name), blockItem);
        return Registry.register(Registries.BLOCK, id(name), block);
    }

    public static void register(){
    }
}
