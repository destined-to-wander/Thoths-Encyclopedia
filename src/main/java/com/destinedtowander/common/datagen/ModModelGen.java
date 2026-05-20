package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModBlocks;
import com.destinedtowander.common.index.ModItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.*;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.util.Identifier;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModModelGen extends FabricModelProvider {
    public ModModelGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateItemModels(ItemModelGenerator generator) {
        Models.GENERATED.upload(
            id("item/quiver"),
            TextureMap.layer0(id("item/quiver")),
            generator.writer
        );
        generator.register(ModItems.RAW_SCULKSTEEL, Models.GENERATED);
        generator.register(ModItems.SCULKSTEEL_INGOT, Models.GENERATED);
        generator.register(ModItems.WAYWARD_MEDLEY, Models.GENERATED);
        generator.register(ModItems.WAYWARD_CLOTH, Models.GENERATED);
        generator.register(ModItems.WANDERERS_HELMET, Models.GENERATED);
        generator.register(ModItems.WANDERERS_CHESTPLATE, Models.GENERATED);
        generator.register(ModItems.WANDERERS_LEGGINGS, Models.GENERATED);
        generator.register(ModItems.WANDERERS_BOOTS, Models.GENERATED);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator generator) {
        generator.registerSimpleCubeAll(ModBlocks.SCULK_CHARGED_AMALGAM);

        // Cinnamon
        generator.registerSimpleCubeAll(ModBlocks.CINNAMON_PLANKS);
        final TextureMap cinnamonPlanksTexture = TextureMap.all(id("block/cinnamon_planks"))
            .put(TextureKey.PARTICLE,id("block/cinnamon_planks"));
        generator.registerLog(ModBlocks.CINNAMON_LOG)
            .log(ModBlocks.CINNAMON_LOG)
            .wood(ModBlocks.CINNAMON_WOOD);
        generator.registerLog(ModBlocks.STRIPPED_CINNAMON_LOG)
            .log(ModBlocks.STRIPPED_CINNAMON_LOG)
            .wood(ModBlocks.STRIPPED_CINNAMON_WOOD);

        final Identifier cinnamonPressurePlate = Models.PRESSURE_PLATE_UP.upload(ModBlocks.CINNAMON_PRESSURE_PLATE, cinnamonPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(ModBlocks.CINNAMON_PRESSURE_PLATE,
            cinnamonPressurePlate,
            Models.PRESSURE_PLATE_DOWN.upload(ModBlocks.CINNAMON_PRESSURE_PLATE, cinnamonPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_PRESSURE_PLATE, cinnamonPressurePlate);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(ModBlocks.CINNAMON_FENCE,
            Models.FENCE_POST.upload(ModBlocks.CINNAMON_FENCE, cinnamonPlanksTexture, generator.modelCollector),
            Models.FENCE_SIDE.upload(ModBlocks.CINNAMON_FENCE, cinnamonPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_FENCE, Models.FENCE_INVENTORY.upload(ModBlocks.CINNAMON_FENCE, cinnamonPlanksTexture, generator.modelCollector));

        final Identifier cinnamonFenceGateModel = Models.TEMPLATE_FENCE_GATE.upload(ModBlocks.CINNAMON_FENCE_GATE, cinnamonPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(ModBlocks.CINNAMON_FENCE_GATE,
            Models.TEMPLATE_FENCE_GATE_OPEN.upload(ModBlocks.CINNAMON_FENCE_GATE, cinnamonPlanksTexture, generator.modelCollector),
            cinnamonFenceGateModel,
            Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(ModBlocks.CINNAMON_FENCE_GATE, cinnamonPlanksTexture, generator.modelCollector),
            Models.TEMPLATE_FENCE_GATE_WALL.upload(ModBlocks.CINNAMON_FENCE_GATE, cinnamonPlanksTexture, generator.modelCollector),
            false
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_FENCE_GATE, cinnamonFenceGateModel);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(ModBlocks.CINNAMON_BUTTON,
            Models.BUTTON.upload(ModBlocks.CINNAMON_BUTTON, cinnamonPlanksTexture, generator.modelCollector),
            Models.BUTTON_PRESSED.upload(ModBlocks.CINNAMON_BUTTON, cinnamonPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_BUTTON, Models.BUTTON_INVENTORY.upload(ModBlocks.CINNAMON_BUTTON, cinnamonPlanksTexture, generator.modelCollector));

        final Identifier cinnamonStairsModel = Models.STAIRS.upload(ModBlocks.CINNAMON_STAIRS, cinnamonPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(ModBlocks.CINNAMON_STAIRS,
            Models.INNER_STAIRS.upload(ModBlocks.CINNAMON_STAIRS, cinnamonPlanksTexture, generator.modelCollector),
            cinnamonStairsModel,
            Models.OUTER_STAIRS.upload(ModBlocks.CINNAMON_STAIRS, cinnamonPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_STAIRS, cinnamonStairsModel);

        final Identifier cinnamonSlabModel = Models.SLAB.upload(ModBlocks.CINNAMON_SLAB, cinnamonPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(ModBlocks.CINNAMON_SLAB,
            cinnamonSlabModel,
            Models.SLAB_TOP.upload(ModBlocks.CINNAMON_SLAB, cinnamonPlanksTexture, generator.modelCollector),
            id("block/cinnamon_planks")
        ));
        generator.registerParentedItemModel(ModBlocks.CINNAMON_SLAB, cinnamonSlabModel);

        generator.registerDoor(ModBlocks.CINNAMON_DOOR);
        generator.registerOrientableTrapdoor(ModBlocks.CINNAMON_TRAPDOOR);
        generator.registerSingleton(ModBlocks.CINNAMON_LEAVES, TexturedModel.LEAVES);

        generator.registerHangingSign(ModBlocks.STRIPPED_CINNAMON_LOG, ModBlocks.CINNAMON_HANGING_SIGN, ModBlocks.CINNAMON_WALL_HANGING_SIGN);

        Identifier cinnamonSignModel = Models.PARTICLE.upload(ModBlocks.CINNAMON_SIGN, TextureMap.particle(ModBlocks.CINNAMON_PLANKS), generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.CINNAMON_SIGN, cinnamonSignModel));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.CINNAMON_WALL_SIGN, cinnamonSignModel));
        generator.registerItemModel(ModBlocks.CINNAMON_SIGN.asItem());
        generator.excludeFromSimpleItemModelGeneration(ModBlocks.CINNAMON_WALL_SIGN);

        generator.registerFlowerPotPlant(ModBlocks.CINNAMON_SAPLING, ModBlocks.POTTED_CINNAMON_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        
        // Frostbark
        generator.registerSimpleCubeAll(ModBlocks.FROSTBARK_PLANKS);
        final TextureMap frostbarkPlanksTexture = TextureMap.all(id("block/frostbark_planks"))
            .put(TextureKey.PARTICLE,id("block/frostbark_planks"));
        generator.registerLog(ModBlocks.FROSTBARK_LOG)
            .log(ModBlocks.FROSTBARK_LOG)
            .wood(ModBlocks.FROSTBARK_WOOD);
        generator.registerLog(ModBlocks.STRIPPED_FROSTBARK_LOG)
            .log(ModBlocks.STRIPPED_FROSTBARK_LOG)
            .wood(ModBlocks.STRIPPED_FROSTBARK_WOOD);

        final Identifier frostbarkPressurePlate = Models.PRESSURE_PLATE_UP.upload(ModBlocks.FROSTBARK_PRESSURE_PLATE, frostbarkPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(ModBlocks.FROSTBARK_PRESSURE_PLATE,
            frostbarkPressurePlate,
            Models.PRESSURE_PLATE_DOWN.upload(ModBlocks.FROSTBARK_PRESSURE_PLATE, frostbarkPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_PRESSURE_PLATE, frostbarkPressurePlate);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createFenceBlockState(ModBlocks.FROSTBARK_FENCE,
            Models.FENCE_POST.upload(ModBlocks.FROSTBARK_FENCE, frostbarkPlanksTexture, generator.modelCollector),
            Models.FENCE_SIDE.upload(ModBlocks.FROSTBARK_FENCE, frostbarkPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_FENCE, Models.FENCE_INVENTORY.upload(ModBlocks.FROSTBARK_FENCE, frostbarkPlanksTexture, generator.modelCollector));

        final Identifier frostbarkFenceGateModel = Models.TEMPLATE_FENCE_GATE.upload(ModBlocks.FROSTBARK_FENCE_GATE, frostbarkPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createFenceGateBlockState(ModBlocks.FROSTBARK_FENCE_GATE,
            Models.TEMPLATE_FENCE_GATE_OPEN.upload(ModBlocks.FROSTBARK_FENCE_GATE, frostbarkPlanksTexture, generator.modelCollector),
            frostbarkFenceGateModel,
            Models.TEMPLATE_FENCE_GATE_WALL_OPEN.upload(ModBlocks.FROSTBARK_FENCE_GATE, frostbarkPlanksTexture, generator.modelCollector),
            Models.TEMPLATE_FENCE_GATE_WALL.upload(ModBlocks.FROSTBARK_FENCE_GATE, frostbarkPlanksTexture, generator.modelCollector),
            false
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_FENCE_GATE, frostbarkFenceGateModel);

        generator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(ModBlocks.FROSTBARK_BUTTON,
            Models.BUTTON.upload(ModBlocks.FROSTBARK_BUTTON, frostbarkPlanksTexture, generator.modelCollector),
            Models.BUTTON_PRESSED.upload(ModBlocks.FROSTBARK_BUTTON, frostbarkPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_BUTTON, Models.BUTTON_INVENTORY.upload(ModBlocks.FROSTBARK_BUTTON, frostbarkPlanksTexture, generator.modelCollector));

        final Identifier frostbarkStairsModel = Models.STAIRS.upload(ModBlocks.FROSTBARK_STAIRS, frostbarkPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(ModBlocks.FROSTBARK_STAIRS,
            Models.INNER_STAIRS.upload(ModBlocks.FROSTBARK_STAIRS, frostbarkPlanksTexture, generator.modelCollector),
            frostbarkStairsModel,
            Models.OUTER_STAIRS.upload(ModBlocks.FROSTBARK_STAIRS, frostbarkPlanksTexture, generator.modelCollector)
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_STAIRS, frostbarkStairsModel);

        final Identifier frostbarkSlabModel = Models.SLAB.upload(ModBlocks.FROSTBARK_SLAB, frostbarkPlanksTexture, generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(ModBlocks.FROSTBARK_SLAB,
            frostbarkSlabModel,
            Models.SLAB_TOP.upload(ModBlocks.FROSTBARK_SLAB, frostbarkPlanksTexture, generator.modelCollector),
            id("block/frostbark_planks")
        ));
        generator.registerParentedItemModel(ModBlocks.FROSTBARK_SLAB, frostbarkSlabModel);

        generator.registerDoor(ModBlocks.FROSTBARK_DOOR);
        generator.registerOrientableTrapdoor(ModBlocks.FROSTBARK_TRAPDOOR);
        generator.registerSingleton(ModBlocks.FROSTBARK_LEAVES, TexturedModel.LEAVES);

        generator.registerHangingSign(ModBlocks.STRIPPED_FROSTBARK_LOG, ModBlocks.FROSTBARK_HANGING_SIGN, ModBlocks.FROSTBARK_WALL_HANGING_SIGN);

        Identifier frostbarkSignModel = Models.PARTICLE.upload(ModBlocks.FROSTBARK_SIGN, TextureMap.particle(ModBlocks.FROSTBARK_PLANKS), generator.modelCollector);
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.FROSTBARK_SIGN, frostbarkSignModel));
        generator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(ModBlocks.FROSTBARK_WALL_SIGN, frostbarkSignModel));
        generator.registerItemModel(ModBlocks.FROSTBARK_SIGN.asItem());
        generator.excludeFromSimpleItemModelGeneration(ModBlocks.FROSTBARK_WALL_SIGN);

        generator.registerFlowerPotPlant(ModBlocks.FROSTBARK_SAPLING, ModBlocks.POTTED_FROSTBARK_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
    }
}