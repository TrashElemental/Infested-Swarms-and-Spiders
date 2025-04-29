package net.trashelemental.infested.util;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.trashelemental.infested.Config;
import net.trashelemental.infested.entity.ModEntities;

import java.util.HashMap;
import java.util.Map;

public class BlockEntityMapping {
    public static final Map<Block, EntitySpawnInfo> BLOCK_ENTITY_MAP = new HashMap<>();

    static double harvest = Config.HARVEST_BEETLE_CHANCE.get();
    static double jewel = Config.JEWEL_BEETLE_CHANCE.get();
    static double chorus = Config.CHORUS_BEETLE_CHANCE.get();
    static double debris = Config.DEBRIS_BEETLE_CHANCE.get();

    static {
        BLOCK_ENTITY_MAP.put(Blocks.GRASS, new EntitySpawnInfo(ModEntities.HARVEST_BEETLE.get(), harvest));
        BLOCK_ENTITY_MAP.put(Blocks.TALL_GRASS, new EntitySpawnInfo(ModEntities.HARVEST_BEETLE.get(),  harvest));
        BLOCK_ENTITY_MAP.put(Blocks.FERN, new EntitySpawnInfo(ModEntities.HARVEST_BEETLE.get(),  harvest));
        BLOCK_ENTITY_MAP.put(Blocks.LARGE_FERN, new EntitySpawnInfo(ModEntities.HARVEST_BEETLE.get(),  harvest));
        BLOCK_ENTITY_MAP.put(Blocks.DEAD_BUSH, new EntitySpawnInfo(ModEntities.HARVEST_BEETLE.get(),  harvest));

        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_COAL_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_COPPER_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_GOLD_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_LAPIS_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_IRON_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_DIAMOND_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_EMERALD_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));
        BLOCK_ENTITY_MAP.put(Blocks.DEEPSLATE_REDSTONE_ORE, new EntitySpawnInfo(ModEntities.JEWEL_BEETLE.get(), jewel));

        BLOCK_ENTITY_MAP.put(Blocks.END_STONE, new EntitySpawnInfo(ModEntities.CHORUS_BEETLE.get(), chorus));

        BLOCK_ENTITY_MAP.put(Blocks.NETHERRACK, new EntitySpawnInfo(ModEntities.ANCIENT_DEBREETLE.get(), debris));
    }
}

