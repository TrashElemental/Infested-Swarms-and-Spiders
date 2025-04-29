package net.trashelemental.infested.entity;

import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.custom.*;
import net.trashelemental.infested.entity.custom.loot_beetles.AncientDebreetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.ChorusBeetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.HarvestBeetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.JewelBeetleEntity;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SilverfishMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.CloakedSpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.DamagedSpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.JumpingSpiderEntity;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleEntity.createAttributes().build());
        event.put(ModEntities.GRUB.get(), GrubEntity.createAttributes().build());
        event.put(ModEntities.BRILLIANT_BEETLE.get(), BrilliantBeetleEntity.createAttributes().build());
        event.put(ModEntities.MANTIS.get(), MantisEntity.createAttributes().build());

        event.put(ModEntities.HARVEST_BEETLE.get(), HarvestBeetleEntity.createAttributes().build());
        event.put(ModEntities.JEWEL_BEETLE.get(), JewelBeetleEntity.createAttributes().build());
        event.put(ModEntities.CHORUS_BEETLE.get(), ChorusBeetleEntity.createAttributes().build());
        event.put(ModEntities.ANCIENT_DEBREETLE.get(), AncientDebreetleEntity.createAttributes().build());

        event.put(ModEntities.SILVERFISH_MINION.get(), SilverfishMinionEntity.createAttributes().build());
        event.put(ModEntities.TAMED_SPIDER.get(), TamedSpiderEntity.createAttributes().build());
        event.put(ModEntities.SPIDER_MINION.get(), SpiderMinionEntity.createAttributes().build());
        event.put(ModEntities.BEE_MINION.get(), BeeMinionEntity.createAttributes().build());

        event.put(ModEntities.JUMPING_SPIDER.get(), JumpingSpiderEntity.createAttributes().build());
        event.put(ModEntities.DAMAGED_SPIDER.get(), DamagedSpiderEntity.createAttributes().build());
        event.put(ModEntities.CLOAKED_SPIDER.get(), CloakedSpiderEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

        event.register(ModEntities.MANTIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MantisEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.BRILLIANT_BEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BrilliantBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.CRIMSON_BEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrimsonBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.GRUB.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GrubEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

    }




    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {


    }


}
