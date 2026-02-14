package com.destinedtowander.common.datagen;

import com.destinedtowander.common.index.ModItems;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

import static com.destinedtowander.ThothsEncyclopedia.MOD_ID;
import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModModelGen extends FabricModelProvider {
    public ModModelGen(FabricDataOutput output) {
        super(output);
    }

    Model SCARF_BODY = new Model(Optional.of(id("item/scarf_body")), Optional.empty(),  TextureKey.LAYER0);

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        Models.GENERATED.upload(
            id("item/quiver"),
            TextureMap.layer0(id("item/quiver")),
            itemModelGenerator.writer
        );
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }
}