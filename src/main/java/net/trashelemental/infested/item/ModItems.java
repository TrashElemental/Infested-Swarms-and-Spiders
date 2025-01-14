package net.trashelemental.infested.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.armor.custom.ChitinArmorItem;
import net.trashelemental.infested.armor.custom.SpiderArmorItem;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.custom.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, infested.MOD_ID);

    // Crafting Ingredients
    public static final RegistryObject<Item> CHITIN = ITEMS.register("chitin",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> INSECT_TEMPLATE = ITEMS.register("insect_template",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SPIDER_TEMPLATE = ITEMS.register("spider_template",
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

    public static final RegistryObject<Item> SWARM_CELL = ITEMS.register("swarm_cell_bee",
            () -> new SwarmCellItem(new Item.Properties(),
                    3, ModEntities.BEE_MINION::get, 30, ModItems.BEE_EGGS.get()));

    public static final RegistryObject<Item> SWARM_SAC = ITEMS.register("swarm_cell_spider",
            () -> new SwarmCellItem(new Item.Properties(),
                    2, ModEntities.SPIDER_MINION::get, 20, ModItems.SPIDER_EGG.get()));

    public static final RegistryObject<Item> SWARM_STONE = ITEMS.register("swarm_cell_silverfish",
            () -> new SwarmCellItem(new Item.Properties(),
                    5, ModEntities.TAMED_SILVERFISH::get, 45, ModItems.SILVERFISH_EGGS.get()));

    //Armor Items
    public static final RegistryObject<Item> CHITIN_HELMET = ITEMS.register("chitin_helmet",
            ChitinArmorItem.Helmet::new);
    public static final RegistryObject<Item> CHITIN_CHESTPLATE = ITEMS.register("chitin_chestplate",
            ChitinArmorItem.Chestplate::new);
    public static final RegistryObject<Item> CHITIN_LEGGINGS = ITEMS.register("chitin_leggings",
            ChitinArmorItem.Leggings::new);
    public static final RegistryObject<Item> CHITIN_BOOTS = ITEMS.register("chitin_boots",
            ChitinArmorItem.Boots::new);

    public static final RegistryObject<Item> SPIDER_HELMET = ITEMS.register("spider_helmet",
            SpiderArmorItem.Helmet::new);
    public static final RegistryObject<Item> SPIDER_CHESTPLATE = ITEMS.register("spider_chestplate",
            SpiderArmorItem.Chestplate::new);
    public static final RegistryObject<Item> SPIDER_LEGGINGS = ITEMS.register("spider_leggings",
            SpiderArmorItem.Leggings::new);
    public static final RegistryObject<Item> SPIDER_BOOTS = ITEMS.register("spider_boots",
            SpiderArmorItem.Boots::new);

    //Spawn Eggs
    public static final RegistryObject<Item> CRIMSON_BEETLE_SPAWN_EGG = ITEMS.register("crimson_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.CRIMSON_BEETLE, -7271926, -14415607, new Item.Properties()));
    public static final RegistryObject<Item> GRUB_SPAWN_EGG = ITEMS.register("grub_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.GRUB, -3361401, -7720655, new Item.Properties()));
    public static final RegistryObject<Item> BRILLIANT_BEETLE_SPAWN_EGG = ITEMS.register("brilliant_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.BRILLIANT_BEETLE, -12109477, -16723242, new Item.Properties()));
    public static final RegistryObject<Item> MANTIS_SPAWN_EGG = ITEMS.register("mantis_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.MANTIS, -16751104, -13382656, new Item.Properties()));
    public static final RegistryObject<Item> ORCHID_MANTIS_SPAWN_EGG = ITEMS.register("orchid_mantis_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ORCHID_MANTIS, -1270065, -6666, new Item.Properties()));

    public static final RegistryObject<Item> HARVEST_BEETLE_SPAWN_EGG = ITEMS.register("harvest_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.HARVEST_BEETLE, -13408768, -6684826, new Item.Properties()));
    public static final RegistryObject<Item> JEWEL_BEETLE_SPAWN_EGG = ITEMS.register("jewel_beetle_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.JEWEL_BEETLE, -6711040, -103, new Item.Properties()));
    public static final RegistryObject<Item> CHORUS_BEETLE_SPAWN_EGG = ITEMS.register("chorus_beetle_spawn_egg",
               () -> new ForgeSpawnEggItem(ModEntities.CHORUS_BEETLE, -6983240, -13057, new Item.Properties()));
    public static final RegistryObject<Item> ANCIENT_DEBREETLE_SPAWN_EGG = ITEMS.register("ancient_debreetle_spawn_egg",
                () -> new ForgeSpawnEggItem(ModEntities.ANCIENT_DEBREETLE, -12308191, -8705266, new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
