package com.destinedtowander.common.index;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModTags {
    TagKey<Block> CINNAMON_LOGS = createBlockTag("cinamon_logs");
    TagKey<Block> LEMON_LOGS = createBlockTag("lemon_logs");

    private static TagKey<Block> createBlockTag(String id) {
            return TagKey.of(RegistryKeys.BLOCK, id(id));
        }

    private static TagKey<Item> createItemTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, id(id));
    }
}
