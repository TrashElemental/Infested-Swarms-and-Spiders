package net.trashelemental.infested.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.entity.custom.*;
import net.trashelemental.infested.entity.custom.jewelbeetles.AncientDebreetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.ChorusBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.HarvestBeetleEntity;
import net.trashelemental.infested.entity.custom.jewelbeetles.JewelBeetleEntity;
import net.trashelemental.infested.entity.custom.minions.*;
import net.trashelemental.infested.infested;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, infested.MOD_ID);


    public static final RegistryObject<EntityType<CrimsonBeetleEntity>> CRIMSON_BEETLE =
            ENTITY_TYPES.register("crimson_beetle", () -> EntityType.Builder.of(CrimsonBeetleEntity::new, MobCategory.AMBIENT)
                    .sized(0.6f, 0.5f).build("crimson_beetle"));
    public static final RegistryObject<EntityType<GrubEntity>> GRUB =
            ENTITY_TYPES.register("grub", () -> EntityType.Builder.of(GrubEntity::new, MobCategory.AMBIENT)
                    .sized(0.4f, 0.3f).build("grub"));
    public static final RegistryObject<EntityType<BrilliantBeetleEntity>> BRILLIANT_BEETLE =
            ENTITY_TYPES.register("brilliant_beetle", () -> EntityType.Builder.of(BrilliantBeetleEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1f).build("brilliant_beetle"));
    public static final RegistryObject<EntityType<MantisEntity>> MANTIS =
            ENTITY_TYPES.register("mantis", () -> EntityType.Builder.of(MantisEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1f).build("mantis"));
    public static final RegistryObject<EntityType<OrchidMantisEntity>> ORCHID_MANTIS =
            ENTITY_TYPES.register("orchid_mantis", () -> EntityType.Builder.of(OrchidMantisEntity::new, MobCategory.CREATURE)
                    .sized(1.5f, 1f).build("orchid_mantis"));


    public static final RegistryObject<EntityType<HarvestBeetleEntity>> HARVEST_BEETLE =
            ENTITY_TYPES.register("harvest_beetle", () -> EntityType.Builder.of(HarvestBeetleEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build("harvest_beetle"));
    public static final RegistryObject<EntityType<JewelBeetleEntity>> JEWEL_BEETLE =
          ENTITY_TYPES.register("jewel_beetle", () -> EntityType.Builder.of(JewelBeetleEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build("jewel_beetle"));
    public static final RegistryObject<EntityType<ChorusBeetleEntity>> CHORUS_BEETLE =
            ENTITY_TYPES.register("chorus_beetle", () -> EntityType.Builder.of(ChorusBeetleEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build("chorus_beetle"));
   public static final RegistryObject<EntityType<AncientDebreetleEntity>> ANCIENT_DEBREETLE =
            ENTITY_TYPES.register("ancient_debreetle", () -> EntityType.Builder.of(AncientDebreetleEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 0.8f).build("ancient_debreetle"));


    public static final RegistryObject<EntityType<TamedSilverfishEntity>> TAMED_SILVERFISH =
            ENTITY_TYPES.register("tamed_silverfish", () -> EntityType.Builder.of(TamedSilverfishEntity::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.3f).build("tamed_silverfish"));
    public static final RegistryObject<EntityType<AttackSilverfishEntity>> ATTACK_SILVERFISH =
            ENTITY_TYPES.register("attack_silverfish", () -> EntityType.Builder.of(AttackSilverfishEntity::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.3f).build("attack_silverfish"));
    public static final RegistryObject<EntityType<TamedSpiderEntity>> TAMED_SPIDER =
            ENTITY_TYPES.register("tamed_spider", () -> EntityType.Builder.of(TamedSpiderEntity::new, MobCategory.CREATURE)
                    .sized(1.4f, 0.6f).build("tamed_spider"));
    public static final RegistryObject<EntityType<SpiderMinionEntity>> SPIDER_MINION =
          ENTITY_TYPES.register("spider_minion", () -> EntityType.Builder.of(SpiderMinionEntity::new, MobCategory.CREATURE)
                .sized(0.7f, 0.3f).build("spider_minion"));
    public static final RegistryObject<EntityType<AttackSpiderEntity>> ATTACK_SPIDER =
          ENTITY_TYPES.register("attack_spider", () -> EntityType.Builder.of(AttackSpiderEntity::new, MobCategory.CREATURE)
                .sized(0.7f, 0.3f).build("attack_spider"));
    public static final RegistryObject<EntityType<BeeMinionEntity>> BEE_MINION =
            ENTITY_TYPES.register("bee_minion", () -> EntityType.Builder.of(BeeMinionEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.3f).build("bee_minion"));
    public static final RegistryObject<EntityType<AttackBeeEntity>> ATTACK_BEE =
            ENTITY_TYPES.register("attack_bee", () -> EntityType.Builder.of(AttackBeeEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.3f).build("attack_bee"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
