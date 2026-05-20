package com.destinedtowander.common.index;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;

import static com.destinedtowander.ThothsEncyclopedia.id;

public class ModTags {
    public static class ModBlockTags {
        public static final TagKey<Block> CINNAMON_LOGS = createBlockTag("cinnamon_logs");
        public static final TagKey<Block> LEMON_LOGS = createBlockTag("lemon_logs");
        public static final TagKey<Block> AMETHINE_LOGS = createBlockTag("amethine_logs");
        public static final TagKey<Block> FROSTBARK_LOGS = createBlockTag("frostbark_logs");
    }

    public static class ModItemTags {
        public static final TagKey<Item> CINNAMON_LOGS = createItemTag("cinnamon_logs");
        public static final TagKey<Item> LEMON_LOGS = createItemTag("lemon_logs");
        public static final TagKey<Item> AMETHINE_LOGS = createItemTag("amethine_logs");
        public static final TagKey<Item> FROSTBARK_LOGS = createItemTag("frostbark_logs");
    }

    private static TagKey<Block> createBlockTag(String id) {
            return TagKey.of(RegistryKeys.BLOCK, id(id));
        }

    private static TagKey<Item> createItemTag(String id) {
        return TagKey.of(RegistryKeys.ITEM, id(id));
    }
}
