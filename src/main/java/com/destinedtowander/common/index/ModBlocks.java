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
import net.minecraft.util.math.Direction;

import java.util.ArrayList;
import java.util.List;

import static com.destinedtowander.ThothsEncyclopedia.id;
import static net.minecraft.block.Blocks.*;

public class ModBlocks {
    public static final List<Item> BLOCKITEMS = new ArrayList<>();

    // Cinnamon Wood Set
    public static final Block CINNAMON_LOG = registerBlock("cinnamon_log",
        createLogBlock(MapColor.ORANGE, MapColor.SPRUCE_BROWN)
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

    // Lemon Wood Set
    public static final Block LEMON_LOG = registerBlock("lemon_log",
        createLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN)
    );
    public static final Block LEMON_WOOD = registerBlock(
        "lemon_wood",
        new PillarBlock(FabricBlockSettings.copyOf(WARPED_HYPHAE).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block STRIPPED_LEMON_LOG = registerBlock("stripped_lemon_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block STRIPPED_LEMON_WOOD = registerBlock("stripped_lemon_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block LEMON_PLANKS = registerBlock("lemon_planks",
        new Block(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).mapColor(MapColor.LIGHT_BLUE).burnable())
    );
    public static final Block LEMON_STAIRS = registerBlock("lemon_stairs",
        new StairsBlock(LEMON_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(LEMON_PLANKS))
    );
    public static final Block LEMON_SLAB = registerBlock("lemon_slab",
        new SlabBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SLAB).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block LEMON_FENCE = registerBlock("lemon_fence",
        new FenceBlock(
            FabricBlockSettings.copyOf(WARPED_FENCE).mapColor(LEMON_PLANKS.getDefaultMapColor())
        )
    );
    public static final Block LEMON_FENCE_GATE = registerBlock("lemon_fence_gate",
        new FenceGateBlock(
            FabricBlockSettings.copyOf(WARPED_FENCE_GATE).mapColor(LEMON_PLANKS.getDefaultMapColor()),
            ModWoodTypes.LEMON
        )
    );
    public static final Block LEMON_DOOR = registerBlock("lemon_door",
        new DoorBlock(
            FabricBlockSettings.copyOf(WARPED_DOOR).mapColor(LEMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.LEMON
        )
    );
    public static final Block LEMON_TRAPDOOR = registerBlock("lemon_trapdoor",
        new TrapdoorBlock(FabricBlockSettings.copyOf(WARPED_TRAPDOOR).mapColor(LEMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.LEMON
        )
    );
    public static final Block LEMON_PRESSURE_PLATE = registerBlock("lemon_pressure_plate",
        new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            FabricBlockSettings.copyOf(WARPED_PRESSURE_PLATE).mapColor(LEMON_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.LEMON
        )
    );
    public static final Block LEMON_BUTTON = registerBlock("lemon_button", createWoodenButtonBlock(ModBlockSetTypes.LEMON));
    public static final Block LEMON_SAPLING = registerBlock("lemon_sapling",
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
    public static final Block LEMON_LEAVES = registerBlock("lemon_leaves", createLeavesBlock(BlockSoundGroup.GRASS));

    public static final Block LEMON_SIGN = registerNoItemBlock("lemon_sign",
        new SignBlock(FabricBlockSettings.copyOf(WARPED_SIGN).mapColor(LEMON_LOG.getDefaultMapColor()), ModWoodTypes.LEMON)
    );
    public static final Block LEMON_WALL_SIGN = registerNoItemBlock("lemon_wall_sign",
        new WallSignBlock(FabricBlockSettings.copyOf(WARPED_WALL_SIGN).mapColor(LEMON_LOG.getDefaultMapColor()).dropsLike(LEMON_SIGN), ModWoodTypes.LEMON)
    );
    public static final Block LEMON_HANGING_SIGN = registerNoItemBlock("lemon_hanging_sign",
        new HangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_HANGING_SIGN).mapColor(LEMON_LOG.getDefaultMapColor()),
            ModWoodTypes.LEMON
        )
    );
    public static final Block LEMON_WALL_HANGING_SIGN = registerNoItemBlock("lemon_wall_hanging_sign",
        new WallHangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_WALL_HANGING_SIGN).mapColor(LEMON_LOG.getDefaultMapColor()).dropsLike(LEMON_HANGING_SIGN),
            ModWoodTypes.LEMON
        )
    );
    public static final Block POTTED_LEMON_SAPLING = registerNoItemBlock("potted_lemon_sapling", createFlowerPotBlock(LEMON_SAPLING));

    // Amethine Wood Set
    public static final Block AMETHINE_DENDRITE = registerBlock("amethine_dendrite",
        new PillarBlock(
            amethineSettings().mapColor(state -> state.get(PillarBlock.AXIS) == Direction.Axis.Y ? MapColor.PURPLE : MapColor.BLACK)
                .strength(2.0F)
        )
    );
    public static final Block AMETHINE_GEODE = registerBlock(
        "amethine_geode",
        new PillarBlock(amethineSettings().strength(2.0F).mapColor(MapColor.BLACK))
    );
    public static final Block STRIPPED_AMETHINE_DENDRITE = registerBlock("stripped_amethine_dendrite",
        new PillarBlock(amethineSettings().strength(2.0F))
    );
    public static final Block STRIPPED_AMETHINE_GEODE = registerBlock("stripped_amethine_geode",
        new PillarBlock(amethineSettings().strength(2.0F))
    );
    public static final Block AMETHINE_PLANKS = registerBlock("amethine_planks",
        new Block(amethineSettings())
    );
    public static final Block AMETHINE_STAIRS = registerBlock("amethine_stairs",
        new StairsBlock(AMETHINE_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(AMETHINE_PLANKS))
    );
    public static final Block AMETHINE_SLAB = registerBlock("amethine_slab",
        new SlabBlock(amethineSettings())
    );
    public static final Block AMETHINE_FENCE = registerBlock("amethine_fence",
        new FenceBlock(amethineSettings().solid())
    );
    public static final Block AMETHINE_FENCE_GATE = registerBlock("amethine_fence_gate",
        new FenceGateBlock(amethineSettings(), ModWoodTypes.AMETHINE)
    );
    public static final Block AMETHINE_DOOR = registerBlock("amethine_door",
        new DoorBlock(
            amethineSettings().strength(3.0F).nonOpaque().pistonBehavior(PistonBehavior.DESTROY),
            ModBlockSetTypes.AMETHINE
        )
    );
    public static final Block AMETHINE_TRAPDOOR = registerBlock("amethine_trapdoor",
        new TrapdoorBlock(
            amethineSettings().strength(3.0F).nonOpaque().allowsSpawning(Blocks::never),
            ModBlockSetTypes.AMETHINE
        )
    );
    public static final Block AMETHINE_PRESSURE_PLATE = registerBlock("amethine_pressure_plate",
        new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            amethineSettings().solid().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY),
            ModBlockSetTypes.AMETHINE
        )
    );
    public static final Block AMETHINE_BUTTON = registerBlock("amethine_button", createCrystalWoodButtonBlock(ModBlockSetTypes.AMETHINE));
    public static final Block AMETHINE_SAPLING = registerBlock("amethine_sapling",
        new SaplingBlock(
            new CinnamonSaplingGenerator(),
            AbstractBlock.Settings.create()
                .mapColor(MapColor.PURPLE)
                .noCollision()
                .ticksRandomly()
                .breakInstantly()
                .sounds(BlockSoundGroup.AMETHYST_BLOCK)
                .pistonBehavior(PistonBehavior.DESTROY)
        )
    );
    public static final Block AMETHINE_LEAVES = registerBlock("amethine_leaves", createLeavesBlock(BlockSoundGroup.GRASS));

    public static final Block AMETHINE_SIGN = registerNoItemBlock("amethine_sign",
        new SignBlock(FabricBlockSettings.copyOf(WARPED_SIGN).mapColor(AMETHINE_DENDRITE.getDefaultMapColor()), ModWoodTypes.AMETHINE)
    );
    public static final Block AMETHINE_WALL_SIGN = registerNoItemBlock("amethine_wall_sign",
        new WallSignBlock(FabricBlockSettings.copyOf(WARPED_WALL_SIGN).mapColor(AMETHINE_DENDRITE.getDefaultMapColor()).dropsLike(AMETHINE_SIGN), ModWoodTypes.AMETHINE)
    );
    public static final Block AMETHINE_HANGING_SIGN = registerNoItemBlock("amethine_hanging_sign",
        new HangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_HANGING_SIGN).mapColor(AMETHINE_DENDRITE.getDefaultMapColor()),
            ModWoodTypes.AMETHINE
        )
    );
    public static final Block AMETHINE_WALL_HANGING_SIGN = registerNoItemBlock("amethine_wall_hanging_sign",
        new WallHangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_WALL_HANGING_SIGN).mapColor(AMETHINE_DENDRITE.getDefaultMapColor()).dropsLike(AMETHINE_HANGING_SIGN),
            ModWoodTypes.AMETHINE
        )
    );
    public static final Block POTTED_AMETHINE_SAPLING = registerNoItemBlock("potted_amethine_sapling", createFlowerPotBlock(AMETHINE_SAPLING));

    // Frostbark Wood Set
    public static final Block FROSTBARK_LOG = registerBlock("frostbark_log",
        createLogBlock(MapColor.DIRT_BROWN, MapColor.SPRUCE_BROWN)
    );
    public static final Block FROSTBARK_WOOD = registerBlock(
        "frostbark_wood",
        new PillarBlock(FabricBlockSettings.copyOf(WARPED_HYPHAE).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block STRIPPED_FROSTBARK_LOG = registerBlock("stripped_frostbark_log",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block STRIPPED_FROSTBARK_WOOD = registerBlock("stripped_frostbark_wood",
        new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_WARPED_STEM).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block FROSTBARK_PLANKS = registerBlock("frostbark_planks",
        new Block(FabricBlockSettings.copyOf(Blocks.WARPED_PLANKS).mapColor(MapColor.LIGHT_BLUE).burnable())
    );
    public static final Block FROSTBARK_STAIRS = registerBlock("frostbark_stairs",
        new StairsBlock(FROSTBARK_PLANKS.getDefaultState(), AbstractBlock.Settings.copy(FROSTBARK_PLANKS))
    );
    public static final Block FROSTBARK_SLAB = registerBlock("frostbark_slab",
        new SlabBlock(FabricBlockSettings.copyOf(Blocks.WARPED_SLAB).mapColor(MapColor.LIGHT_BLUE))
    );
    public static final Block FROSTBARK_FENCE = registerBlock("frostbark_fence",
        new FenceBlock(
            FabricBlockSettings.copyOf(WARPED_FENCE).mapColor(FROSTBARK_PLANKS.getDefaultMapColor())
        )
    );
    public static final Block FROSTBARK_FENCE_GATE = registerBlock("frostbark_fence_gate",
        new FenceGateBlock(
            FabricBlockSettings.copyOf(WARPED_FENCE_GATE).mapColor(FROSTBARK_PLANKS.getDefaultMapColor()),
            ModWoodTypes.FROSTBARK
        )
    );
    public static final Block FROSTBARK_DOOR = registerBlock("frostbark_door",
        new DoorBlock(
            FabricBlockSettings.copyOf(WARPED_DOOR).mapColor(FROSTBARK_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.FROSTBARK
        )
    );
    public static final Block FROSTBARK_TRAPDOOR = registerBlock("frostbark_trapdoor",
        new TrapdoorBlock(FabricBlockSettings.copyOf(WARPED_TRAPDOOR).mapColor(FROSTBARK_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.FROSTBARK
        )
    );
    public static final Block FROSTBARK_PRESSURE_PLATE = registerBlock("frostbark_pressure_plate",
        new PressurePlateBlock(
            PressurePlateBlock.ActivationRule.EVERYTHING,
            FabricBlockSettings.copyOf(WARPED_PRESSURE_PLATE).mapColor(FROSTBARK_PLANKS.getDefaultMapColor()),
            ModBlockSetTypes.FROSTBARK
        )
    );
    public static final Block FROSTBARK_BUTTON = registerBlock("frostbark_button", createWoodenButtonBlock(ModBlockSetTypes.FROSTBARK));
    public static final Block FROSTBARK_SAPLING = registerBlock("frostbark_sapling",
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
    public static final Block FROSTBARK_LEAVES = registerBlock("frostbark_leaves", createLeavesBlock(BlockSoundGroup.GRASS));

    public static final Block FROSTBARK_SIGN = registerNoItemBlock("frostbark_sign",
        new SignBlock(FabricBlockSettings.copyOf(WARPED_SIGN).mapColor(FROSTBARK_LOG.getDefaultMapColor()), ModWoodTypes.FROSTBARK)
    );
    public static final Block FROSTBARK_WALL_SIGN = registerNoItemBlock("frostbark_wall_sign",
        new WallSignBlock(FabricBlockSettings.copyOf(WARPED_WALL_SIGN).mapColor(FROSTBARK_LOG.getDefaultMapColor()).dropsLike(FROSTBARK_SIGN), ModWoodTypes.FROSTBARK)
    );
    public static final Block FROSTBARK_HANGING_SIGN = registerNoItemBlock("frostbark_hanging_sign",
        new HangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_HANGING_SIGN).mapColor(FROSTBARK_LOG.getDefaultMapColor()),
            ModWoodTypes.FROSTBARK
        )
    );
    public static final Block FROSTBARK_WALL_HANGING_SIGN = registerNoItemBlock("frostbark_wall_hanging_sign",
        new WallHangingSignBlock(
            FabricBlockSettings.copyOf(WARPED_WALL_HANGING_SIGN).mapColor(FROSTBARK_LOG.getDefaultMapColor()).dropsLike(FROSTBARK_HANGING_SIGN),
            ModWoodTypes.FROSTBARK
        )
    );
    public static final Block POTTED_FROSTBARK_SAPLING = registerNoItemBlock("potted_frostbark_sapling", createFlowerPotBlock(FROSTBARK_SAPLING));
    
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

    private static <T extends Block> T registerBlock(String name, T block){
        Item blockItem = new BlockItem(block, new Item.Settings());
        BLOCKITEMS.add(blockItem);
        Item.BLOCK_ITEMS.put(block,blockItem);
        Registry.register(Registries.ITEM, id(name), blockItem);
        return registerNoItemBlock(name, block);
    }

    private static <T extends Block> T registerHiddenBlock(String name, T block){
        Item blockItem = new BlockItem(block , new Item.Settings());
        Item.BLOCK_ITEMS.put(block,blockItem);
        Registry.register(Registries.ITEM, id(name), blockItem);
        return registerNoItemBlock(name, block);
    }

    private static <T extends Block> T registerNoItemBlock(String name, T block){
        return Registry.register(Registries.BLOCK, id(name), block);
    }
    
    private static AbstractBlock.Settings amethineSettings() {
        return AbstractBlock.Settings.create().mapColor(MapColor.PURPLE).instrument(Instrument.CHIME).sounds(BlockSoundGroup.AMETHYST_BLOCK).strength(2.0F, 3.0F);
    }

    private static ButtonBlock createCrystalWoodButtonBlock(BlockSetType blockSetType){
        return new ButtonBlock(AbstractBlock.Settings.create().noCollision().strength(0.5F).pistonBehavior(PistonBehavior.DESTROY), blockSetType, 15, false);
    }

    public static void register(){
    }
}
