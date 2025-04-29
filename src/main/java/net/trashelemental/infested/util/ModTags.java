package net.trashelemental.infested.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.trashelemental.infested.infested;

public class ModTags {

    public static class Blocks {

        public static final TagKey<Block> INFESTED_BLOCKS = tag("infested_blocks");


        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(infested.MOD_ID, name));
        }
    }

    public static class Entities {


        private static TagKey<EntityType<?>> tag(String name) {
            return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(infested.MOD_ID, name));
        }
    }

    public static class Items {

        public static final TagKey<Item> SUMMONS_SPIDER = tag("summons_spider");
        public static final TagKey<Item> SUMMONS_BEE = tag("summons_bee");

        public static final TagKey<Item> ARTHROPOD_FOOD = tag("arthropod_food");

        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(infested.MOD_ID, name));
        }
    }

}
