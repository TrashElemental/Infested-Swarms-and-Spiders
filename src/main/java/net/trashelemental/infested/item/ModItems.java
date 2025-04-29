package net.trashelemental.infested.item;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.armor.ModArmorMaterials;
import net.trashelemental.infested.armor.custom.BeeArmorItem;
import net.trashelemental.infested.armor.custom.ChitinArmorItem;
import net.trashelemental.infested.armor.custom.SpiderArmorItem;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.custom.*;
import net.trashelemental.infested.item.custom.throwing_items.AlchemySpiderEggItem;
import net.trashelemental.infested.item.custom.throwing_items.CobwebBombItem;
import net.trashelemental.infested.item.custom.throwing_items.EggSacItem;
import net.trashelemental.infested.item.custom.tools.MantisSickleItem;
import net.trashelemental.infested.item.custom.tools.SpiderSicaItem;
import net.trashelemental.infested.item.custom.tools.StingerPoniardItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, infested.MOD_ID);

    // Crafting Ingredients
    public static final RegistryObject<Item> CHITIN = ITEMS.register("chitin",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MANTIS_CLAW = ITEMS.register("mantis_claw",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> INSECT_TEMPLATE = ITEMS.register("insect_template",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_TEMPLATE = ITEMS.register("spider_template",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BEE_TEMPLATE = ITEMS.register("bee_template",
            () -> new Item(new Item.Properties()));


    // Foods
    public static final RegistryObject<Item> RAW_GRUB = ITEMS.register("raw_grub",
            () -> new Item(new Item.Properties().food(ModFoods.RAW_GRUB)));
    public static final RegistryObject<Item> FRIED_GRUB = ITEMS.register("fried_grub",
            () -> new Item(new Item.Properties().food(ModFoods.FRIED_GRUB)));
    public static final RegistryObject<Item> BUG_STEW = ITEMS.register("bug_stew",
            () -> new BugStewItem(new Item.Properties().food(ModFoods.BUG_STEW)));


    //Functional Items
    public static final RegistryObject<Item> SILVERFISH_EGGS = ITEMS.register("silverfish_eggs",
            () -> new SilverfishEggsItem(new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_EGG = ITEMS.register("spider_egg",
            () -> new SpiderEggItem(new Item.Properties()));
    public static final RegistryObject<Item> BEE_EGGS = ITEMS.register("bee_eggs",
            () -> new BeeEggsItem(new Item.Properties()));
    public static final RegistryObject<Item> TAMED_SPIDER_ARMOR = ITEMS.register("tamed_spider_armor",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_EGG_SAC = ITEMS.register("spider_egg_sac",
            () -> new EggSacItem(new Item.Properties(), ModEntities.SPIDER_MINION::get, 3));
    public static final RegistryObject<Item> COBWEB_BOMB = ITEMS.register("cobweb_bomb",
            () -> new CobwebBombItem(new Item.Properties()));

    public static final RegistryObject<Item> SWARM_CELL = ITEMS.register("swarm_cell_bee",
            () -> new SwarmCellItem(new Item.Properties(),
                    3, ModEntities.BEE_MINION::get, 30, ModItems.BEE_EGGS.get()));
    public static final RegistryObject<Item> SWARM_SAC = ITEMS.register("swarm_cell_spider",
            () -> new SwarmCellItem(new Item.Properties(),
                    2, ModEntities.SPIDER_MINION::get, 20, ModItems.SPIDER_EGG.get()));
    public static final RegistryObject<Item> SWARM_STONE = ITEMS.register("swarm_cell_silverfish",
            () -> new SwarmCellItem(new Item.Properties(),
                    5, ModEntities.SILVERFISH_MINION::get, 45, ModItems.SILVERFISH_EGGS.get()));

    //Tools
    public static final RegistryObject<Item> MANTIS_SICKLE = ITEMS.register("mantis_sickle",
            () -> new MantisSickleItem(ModToolTiers.MANTIS, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_SICA = ITEMS.register("spider_sica",
            () -> new SpiderSicaItem(ModToolTiers.MANTIS, new Item.Properties()));
    public static final RegistryObject<Item> STINGER_PONIARD = ITEMS.register("stinger_poniard",
            () -> new StingerPoniardItem(ModToolTiers.MANTIS, new Item.Properties()));

    //Armor Items
    public static final RegistryObject<Item> CHITIN_HELMET = ITEMS.register("chitin_helmet",
            () -> new ChitinArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_CHESTPLATE = ITEMS.register("chitin_chestplate",
            () -> new ChitinArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_LEGGINGS = ITEMS.register("chitin_leggings",
            () -> new ChitinArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> CHITIN_BOOTS = ITEMS.register("chitin_boots",
            () -> new ChitinArmorItem(ModArmorMaterials.CHITIN, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_HELMET = ITEMS.register("spider_helmet",
            () -> new SpiderArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_CHESTPLATE = ITEMS.register("spider_chestplate",
            () -> new SpiderArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_LEGGINGS = ITEMS.register("spider_leggings",
            () -> new SpiderArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> SPIDER_BOOTS = ITEMS.register("spider_boots",
            () -> new SpiderArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final RegistryObject<Item> BEE_HELMET = ITEMS.register("bee_helmet",
            () -> new BeeArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final RegistryObject<Item> BEE_CHESTPLATE = ITEMS.register("bee_chestplate",
            () -> new BeeArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final RegistryObject<Item> BEE_LEGGINGS = ITEMS.register("bee_leggings",
            () -> new BeeArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final RegistryObject<Item> BEE_BOOTS = ITEMS.register("bee_boots",
            () -> new BeeArmorItem(ModArmorMaterials.SPIDER, ArmorItem.Type.BOOTS, new Item.Properties()));

    //Spawn Eggs
    public static final RegistryObject<Item> CRIMSON_BEETLE_SPAWN_EGG = ITEMS.register("crimson_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRIMSON_BEETLE, -7271926, -14415607, new Item.Properties()));
    public static final RegistryObject<Item> GRUB_SPAWN_EGG = ITEMS.register("grub_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GRUB, -3361401, -7720655, new Item.Properties()));
    public static final RegistryObject<Item> BRILLIANT_BEETLE_SPAWN_EGG = ITEMS.register("brilliant_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BRILLIANT_BEETLE, -12109477, -16723242, new Item.Properties()));
    public static final RegistryObject<Item> MANTIS_SPAWN_EGG = ITEMS.register("mantis_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MANTIS, -16751104, -13382656, new Item.Properties()));

    public static final RegistryObject<Item> HARVEST_BEETLE_SPAWN_EGG = ITEMS.register("harvest_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HARVEST_BEETLE, -13408768, -6684826, new Item.Properties()));
    public static final RegistryObject<Item> JEWEL_BEETLE_SPAWN_EGG = ITEMS.register("jewel_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JEWEL_BEETLE, -6711040, -103, new Item.Properties()));
    public static final RegistryObject<Item> CHORUS_BEETLE_SPAWN_EGG = ITEMS.register("chorus_beetle_spawn_egg",
               () -> new ForgeSpawnEggItem(ModEntities.CHORUS_BEETLE, -6983240, -13057, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_DEBREETLE_SPAWN_EGG = ITEMS.register("ancient_debreetle_spawn_egg",
                () -> new ForgeSpawnEggItem(ModEntities.ANCIENT_DEBREETLE, -12308191, -8705266, new Item.Properties()));

    //Spider Alchemy
    public static final RegistryObject<Item> JUMPING_FIRE_SPIDER_EGG = ITEMS.register("jumping_fire_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.JUMPING_SPIDER::get, AlchemySpiderEntity.ElementType.FIRE));
    public static final RegistryObject<Item> DAMAGED_FIRE_SPIDER_EGG = ITEMS.register("damaged_fire_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.DAMAGED_SPIDER::get, AlchemySpiderEntity.ElementType.FIRE));
    public static final RegistryObject<Item> CLOAKED_FIRE_SPIDER_EGG = ITEMS.register("cloaked_fire_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.CLOAKED_SPIDER::get, AlchemySpiderEntity.ElementType.FIRE));
    public static final RegistryObject<Item> JUMPING_ICE_SPIDER_EGG = ITEMS.register("jumping_ice_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.JUMPING_SPIDER::get, AlchemySpiderEntity.ElementType.ICE));
    public static final RegistryObject<Item> DAMAGED_ICE_SPIDER_EGG = ITEMS.register("damaged_ice_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.DAMAGED_SPIDER::get, AlchemySpiderEntity.ElementType.ICE));
    public static final RegistryObject<Item> CLOAKED_ICE_SPIDER_EGG = ITEMS.register("cloaked_ice_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.CLOAKED_SPIDER::get, AlchemySpiderEntity.ElementType.ICE));
    public static final RegistryObject<Item> JUMPING_LIGHTNING_SPIDER_EGG = ITEMS.register("jumping_lightning_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.JUMPING_SPIDER::get, AlchemySpiderEntity.ElementType.LIGHTNING));
    public static final RegistryObject<Item> DAMAGED_LIGHTNING_SPIDER_EGG = ITEMS.register("damaged_lightning_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.DAMAGED_SPIDER::get, AlchemySpiderEntity.ElementType.LIGHTNING));
    public static final RegistryObject<Item> CLOAKED_LIGHTNING_SPIDER_EGG = ITEMS.register("cloaked_lightning_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.CLOAKED_SPIDER::get, AlchemySpiderEntity.ElementType.LIGHTNING));
    public static final RegistryObject<Item> JUMPING_PSYCHIC_SPIDER_EGG = ITEMS.register("jumping_psychic_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.JUMPING_SPIDER::get, AlchemySpiderEntity.ElementType.PSYCHIC));
    public static final RegistryObject<Item> DAMAGED_PSYCHIC_SPIDER_EGG = ITEMS.register("damaged_psychic_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.DAMAGED_SPIDER::get, AlchemySpiderEntity.ElementType.PSYCHIC));
    public static final RegistryObject<Item> JUMPING_BLAST_SPIDER_EGG = ITEMS.register("jumping_blast_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.JUMPING_SPIDER::get, AlchemySpiderEntity.ElementType.BLAST));
    public static final RegistryObject<Item> DAMAGED_BLAST_SPIDER_EGG = ITEMS.register("damaged_blast_spider_egg",
            () -> new AlchemySpiderEggItem(new Item.Properties(), ModEntities.DAMAGED_SPIDER::get, AlchemySpiderEntity.ElementType.BLAST));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
