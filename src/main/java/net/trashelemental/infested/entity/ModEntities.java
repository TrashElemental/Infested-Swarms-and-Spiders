package net.trashelemental.infested.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.entity.custom.*;
import net.trashelemental.infested.entity.custom.loot_beetles.AncientDebreetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.ChorusBeetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.HarvestBeetleEntity;
import net.trashelemental.infested.entity.custom.loot_beetles.JewelBeetleEntity;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SilverfishMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;
import net.trashelemental.infested.entity.custom.projectile.AlchemySpiderEggEntity;
import net.trashelemental.infested.entity.custom.projectile.CobwebBombEntity;
import net.trashelemental.infested.entity.custom.projectile.SpiderEggSacEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.CloakedSpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.DamagedSpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.JumpingSpiderEntity;
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
                    .sized(1f, 1.5f).build("mantis"));

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


   public static final RegistryObject<EntityType<SilverfishMinionEntity>> SILVERFISH_MINION =
            ENTITY_TYPES.register("silverfish_minion", () -> EntityType.Builder.of(SilverfishMinionEntity::new, MobCategory.CREATURE)
                    .sized(0.4f, 0.3f).build("tamed_silverfish"));
   public static final RegistryObject<EntityType<TamedSpiderEntity>> TAMED_SPIDER =
            ENTITY_TYPES.register("tamed_spider", () -> EntityType.Builder.of(TamedSpiderEntity::new, MobCategory.CREATURE)
                    .sized(1f, 0.65f).build("tamed_spider"));
   public static final RegistryObject<EntityType<SpiderMinionEntity>> SPIDER_MINION =
          ENTITY_TYPES.register("spider_minion", () -> EntityType.Builder.of(SpiderMinionEntity::new, MobCategory.CREATURE)
                .sized(0.7f, 0.3f).build("spider_minion"));
   public static final RegistryObject<EntityType<BeeMinionEntity>> BEE_MINION =
            ENTITY_TYPES.register("bee_minion", () -> EntityType.Builder.of(BeeMinionEntity::new, MobCategory.CREATURE)
                    .sized(0.7f, 0.3f).build("bee_minion"));


   public static final RegistryObject<EntityType<JumpingSpiderEntity>> JUMPING_SPIDER =
            ENTITY_TYPES.register("jumping_spider", () -> EntityType.Builder.of(JumpingSpiderEntity::new, MobCategory.CREATURE)
                    .sized(1f, 0.65f).build("jumping_spider"));
   public static final RegistryObject<EntityType<DamagedSpiderEntity>> DAMAGED_SPIDER =
            ENTITY_TYPES.register("damaged_spider", () -> EntityType.Builder.of(DamagedSpiderEntity::new, MobCategory.CREATURE)
                    .sized(1f, 0.65f).build("damaged_spider"));
   public static final RegistryObject<EntityType<CloakedSpiderEntity>> CLOAKED_SPIDER =
            ENTITY_TYPES.register("cloaked_spider", () -> EntityType.Builder.of(CloakedSpiderEntity::new, MobCategory.CREATURE)
                    .sized(1f, 0.65f).build("cloaked_spider"));


   //Projectiles
   public static final RegistryObject<EntityType<AlchemySpiderEggEntity>> ALCHEMY_SPIDER_EGG =
            ENTITY_TYPES.register("alchemy_spider_egg", () ->
                    EntityType.Builder.<AlchemySpiderEggEntity>of(AlchemySpiderEggEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build("alchemy_spider_egg"));
    public static final RegistryObject<EntityType<SpiderEggSacEntity>> SPIDER_EGG_SAC =
            ENTITY_TYPES.register("spider_egg_sac", () ->
                    EntityType.Builder.<SpiderEggSacEntity>of(SpiderEggSacEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f).build("spider_egg_sac"));
    public static final RegistryObject<EntityType<CobwebBombEntity>> COBWEB_BOMB =
            ENTITY_TYPES.register("cobweb_bomb", () ->
                    EntityType.Builder.<CobwebBombEntity>of(CobwebBombEntity::new, MobCategory.MISC)
                            .sized(0.5f, 0.5f).build("cobweb_bomb"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
