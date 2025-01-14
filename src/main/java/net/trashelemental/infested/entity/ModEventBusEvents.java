package net.trashelemental.infested.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.SpawnPlacementRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.client.models.*;
import net.trashelemental.infested.entity.custom.*;
import net.trashelemental.infested.entity.custom.jewelbeetles.AncientDebreetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.ChorusBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.HarvestBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.JewelBeetleEntity;
import net.trashelemental.infested.entity.custom.minions.*;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventBusEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleEntity.createAttributes().build());
        event.put(ModEntities.GRUB.get(), GrubEntity.createAttributes().build());
        event.put(ModEntities.BRILLIANT_BEETLE.get(), BrilliantBeetleEntity.createAttributes().build());
        event.put(ModEntities.MANTIS.get(), MantisEntity.createAttributes().build());
        event.put(ModEntities.ORCHID_MANTIS.get(), OrchidMantisEntity.createAttributes().build());

        event.put(ModEntities.HARVEST_BEETLE.get(), HarvestBeetleEntity.createAttributes().build());
        event.put(ModEntities.JEWEL_BEETLE.get(), JewelBeetleEntity.createAttributes().build());
        event.put(ModEntities.CHORUS_BEETLE.get(), ChorusBeetleEntity.createAttributes().build());
        event.put(ModEntities.ANCIENT_DEBREETLE.get(), AncientDebreetleEntity.createAttributes().build());

        event.put(ModEntities.TAMED_SILVERFISH.get(), TamedSilverfishEntity.createAttributes().build());
        event.put(ModEntities.ATTACK_SILVERFISH.get(), AttackSilverfishEntity.createAttributes().build());
        event.put(ModEntities.TAMED_SPIDER.get(), TamedSpiderEntity.createAttributes().build());
        event.put(ModEntities.SPIDER_MINION.get(), SpiderMinionEntity.createAttributes().build());
        event.put(ModEntities.ATTACK_SPIDER.get(), AttackSpiderEntity.createAttributes().build());
        event.put(ModEntities.BEE_MINION.get(), BeeMinionEntity.createAttributes().build());
        event.put(ModEntities.ATTACK_BEE.get(), AttackBeeEntity.createAttributes().build());
    }

    @SubscribeEvent
    public static void registerSpawnPlacements(SpawnPlacementRegisterEvent event) {

        event.register(ModEntities.MANTIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                MantisEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.ORCHID_MANTIS.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                OrchidMantisEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.BRILLIANT_BEETLE.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                BrilliantBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.CRIMSON_BEETLE.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                CrimsonBeetleEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);
        event.register(ModEntities.GRUB.get(), SpawnPlacements.Type.NO_RESTRICTIONS, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                GrubEntity::canSpawn, SpawnPlacementRegisterEvent.Operation.OR);

    }

    public static final ModelLayerLocation CRIMSON_BEETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "crimson_beetle_layer"), "main");

    public static final ModelLayerLocation GRUB_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "grub_layer"), "main");

    public static final ModelLayerLocation HARVEST_BEETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "harvest_beetle_layer"), "main");

    public static final ModelLayerLocation JEWEL_BEETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "jewel_beetle_layer"), "main");

    public static final ModelLayerLocation CHORUS_BEETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "chorus_beetle_layer"), "main");

    public static final ModelLayerLocation ANCIENT_DEBREETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "ancient_debreetle_layer"), "main");

    public static final ModelLayerLocation BRILLIANT_BEETLE_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "brilliant_beetle_layer"), "main");

    public static final ModelLayerLocation MANTIS_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "mantis_layer"), "main");

    public static final ModelLayerLocation ORCHID_MANTIS_LAYER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "orchid_mantis_layer"), "main");


    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(CRIMSON_BEETLE_LAYER, CrimsonBeetleModel::createBodyLayer);
        event.registerLayerDefinition(GRUB_LAYER, GrubModel::createBodyLayer);
        event.registerLayerDefinition(BRILLIANT_BEETLE_LAYER, BrilliantBeetleModel::createBodyLayer);
        event.registerLayerDefinition(MANTIS_LAYER, MantisModel::createBodyLayer);
        event.registerLayerDefinition(ORCHID_MANTIS_LAYER, MantisModel::createBodyLayer);

        event.registerLayerDefinition(HARVEST_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(JEWEL_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(CHORUS_BEETLE_LAYER, JewelBeetleModel::createBodyLayer);
        event.registerLayerDefinition(ANCIENT_DEBREETLE_LAYER, JewelBeetleModel::createBodyLayer);

    }


}
