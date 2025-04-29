package net.trashelemental.infested.util.event;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.CaveSpider;
import net.minecraft.world.entity.monster.Silverfish;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.util.BlockEntityMapping;
import net.trashelemental.infested.util.EntitySpawnInfo;

import java.util.Map;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class ModItemsDrop {

    /**
     * Logic for handling when mob items like Silverfish Eggs should
     * drop from their respective blocks and entities. Loot beetle spawning
     * is also here.
     */

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {

        Level level = (Level) event.getLevel();
        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        LootBeetleSpawning(level, pos, block);
        MinionEggsSpawning(level, pos, state);
    }

    public static void LootBeetleSpawning(Level level, BlockPos pos, Block block) {
        if (level instanceof ServerLevel serverLevel) {
            EntitySpawnInfo spawnInfo = BlockEntityMapping.BLOCK_ENTITY_MAP.get(block);

            if (spawnInfo != null) {
                double chance = spawnInfo.spawnChance;
                if (Math.random() < chance) {
                    Entity entity = spawnInfo.entityType.create(serverLevel);
                    if (entity != null) {
                        entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                        serverLevel.addFreshEntity(entity);
                    }
                }
            }
        }
    }

    public static void MinionEggsSpawning(Level level, BlockPos pos, BlockState state) {
        if (Math.random() >= 0.5) return;

        Item eggItem = null;

        if (state.is(BlockTags.create(new ResourceLocation("infested:infested_blocks")))) {
            eggItem = ModItems.SILVERFISH_EGGS.get();
        } else if (state.is(Blocks.BEEHIVE) || state.is(Blocks.BEE_NEST)) {
            eggItem = ModItems.BEE_EGGS.get();
        }

        if (eggItem != null) {
            ItemEntity entityToSpawn = new ItemEntity(level, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(eggItem));
            entityToSpawn.setPickUpDelay(10);
            level.addFreshEntity(entityToSpawn);
        }
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();

        if (!(level instanceof ServerLevel serverLevel)) return;

        Map<Class<? extends Entity>, DropData> dropMap = Map.of(
                Spider.class, new DropData(ModItems.SPIDER_EGG.get(), 0.05),
                CaveSpider.class, new DropData(ModItems.SPIDER_EGG.get(), 0.2),
                Silverfish.class, new DropData(ModItems.SILVERFISH_EGGS.get(), 0.1),
                Bee.class, new DropData(ModItems.BEE_EGGS.get(), 0.1)
        );

        for (Map.Entry<Class<? extends Entity>, DropData> entry : dropMap.entrySet()) {
            if (entry.getKey().isInstance(entity)) {
                DropData drop = entry.getValue();
                if (Math.random() < drop.chance) {
                    ItemEntity dropEntity = new ItemEntity(serverLevel, entity.getX(), entity.getY(), entity.getZ(), new ItemStack(drop.item));
                    dropEntity.setPickUpDelay(10);
                    serverLevel.addFreshEntity(dropEntity);
                }
                break;
            }
        }
    }

    private record DropData(Item item, double chance) {
    }
}
