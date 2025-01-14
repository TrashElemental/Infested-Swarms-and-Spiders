package net.trashelemental.infested.datagen.loot;

import net.minecraft.data.loot.EntityLootSubProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.item.ModItems;

import java.util.stream.Stream;

public class ModEntityLootTables extends EntityLootSubProvider {
    public ModEntityLootTables() {
        super(FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        add(ModEntities.CRIMSON_BEETLE.get(), createCrimsonBeetleLootTable());
        add(ModEntities.GRUB.get(), createGrubLootTable());
        add(ModEntities.HARVEST_BEETLE.get(), createHarvestBeetleLootTable());
        add(ModEntities.JEWEL_BEETLE.get(), createJewelBeetleLootTable());
        add(ModEntities.CHORUS_BEETLE.get(), createChorusBeetleLootTable());
        add(ModEntities.ANCIENT_DEBREETLE.get(), createAncientDebreetleLootTable());
        add(ModEntities.BRILLIANT_BEETLE.get(), createBrilliantBeetleLootTable());
        add(ModEntities.MANTIS.get(), createMantisLootTable());
        add(ModEntities.ORCHID_MANTIS.get(), createOrchidMantisLootTable());
    }

    private LootTable.Builder createCrimsonBeetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(ModItems.RAW_GRUB.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(0, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))
                                .when(LootItemRandomChanceCondition.randomChance(0.5f)))
                        .add(LootItem.lootTableItem(ModItems.CHITIN.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createGrubLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(ModItems.RAW_GRUB.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createHarvestBeetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.CARROT)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.POTATO)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.WHEAT_SEEDS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.BEETROOT_SEEDS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.MELON_SEEDS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.PUMPKIN_SEEDS)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                );
    }

    private LootTable.Builder createJewelBeetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.RAW_IRON)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.RAW_COPPER)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.RAW_GOLD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.DIAMOND)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.EMERALD)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                        .add(LootItem.lootTableItem(Items.LAPIS_LAZULI)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1))))
                );
    }

    private LootTable.Builder createChorusBeetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.SHULKER_SHELL)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createAncientDebreetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(Items.NETHERITE_SCRAP)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 1)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createBrilliantBeetleLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(ModItems.CHITIN.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createMantisLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(ModItems.CHITIN.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }

    private LootTable.Builder createOrchidMantisLootTable() {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(UniformGenerator.between(1.0F, 1.0F))
                        .add(LootItem.lootTableItem(ModItems.CHITIN.get())
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1, 2)))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 1)))));
    }



    @Override
    protected Stream<EntityType<?>> getKnownEntityTypes() {
        return Stream.of(
                ModEntities.CRIMSON_BEETLE.get(),
                ModEntities.GRUB.get(),
                ModEntities.HARVEST_BEETLE.get(),
                ModEntities.JEWEL_BEETLE.get(),
                ModEntities.CHORUS_BEETLE.get(),
                ModEntities.ANCIENT_DEBREETLE.get(),
                ModEntities.BRILLIANT_BEETLE.get(),
                ModEntities.MANTIS.get(),
                ModEntities.ORCHID_MANTIS.get()
        );
    }
}
