package net.trashelemental.infested.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, infested.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.CHITIN);
        simpleItem(ModItems.MANTIS_CLAW);
        simpleItem(ModItems.INSECT_TEMPLATE);
        simpleItem(ModItems.SPIDER_TEMPLATE);
        simpleItem(ModItems.BEE_TEMPLATE);

        handheldItem(ModItems.MANTIS_SICKLE);
        handheldItem(ModItems.SPIDER_SICA);
        handheldItem(ModItems.STINGER_PONIARD);

        simpleItem(ModItems.RAW_GRUB);
        simpleItem(ModItems.FRIED_GRUB);
        simpleItem(ModItems.BUG_STEW);

        simpleItem(ModItems.SILVERFISH_EGGS);
        simpleItem(ModItems.SPIDER_EGG);
        simpleItem(ModItems.BEE_EGGS);
        simpleItem(ModItems.TAMED_SPIDER_ARMOR);

        simpleItem(ModItems.SPIDER_EGG_SAC);
        simpleItem(ModItems.COBWEB_BOMB);

        simpleItem(ModItems.SWARM_CELL);
        simpleItem(ModItems.SWARM_SAC);
        simpleItem(ModItems.SWARM_STONE);

        simpleItem(ModItems.CHITIN_HELMET);
        simpleItem(ModItems.CHITIN_CHESTPLATE);
        simpleItem(ModItems.CHITIN_LEGGINGS);
        simpleItem(ModItems.CHITIN_BOOTS);
        simpleItem(ModItems.SPIDER_HELMET);
        simpleItem(ModItems.SPIDER_CHESTPLATE);
        simpleItem(ModItems.SPIDER_LEGGINGS);
        simpleItem(ModItems.SPIDER_BOOTS);
        simpleItem(ModItems.BEE_HELMET);
        simpleItem(ModItems.BEE_CHESTPLATE);
        simpleItem(ModItems.BEE_LEGGINGS);
        simpleItem(ModItems.BEE_BOOTS);

        nonNameMatchingTextureItem(ModItems.JUMPING_FIRE_SPIDER_EGG, "fire_spider_egg");
        nonNameMatchingTextureItem(ModItems.DAMAGED_FIRE_SPIDER_EGG, "fire_spider_egg");
        nonNameMatchingTextureItem(ModItems.CLOAKED_FIRE_SPIDER_EGG, "fire_spider_egg");
        nonNameMatchingTextureItem(ModItems.JUMPING_ICE_SPIDER_EGG, "ice_spider_egg");
        nonNameMatchingTextureItem(ModItems.DAMAGED_ICE_SPIDER_EGG, "ice_spider_egg");
        nonNameMatchingTextureItem(ModItems.CLOAKED_ICE_SPIDER_EGG, "ice_spider_egg");
        nonNameMatchingTextureItem(ModItems.JUMPING_LIGHTNING_SPIDER_EGG, "lightning_spider_egg");
        nonNameMatchingTextureItem(ModItems.DAMAGED_LIGHTNING_SPIDER_EGG, "lightning_spider_egg");
        nonNameMatchingTextureItem(ModItems.CLOAKED_LIGHTNING_SPIDER_EGG, "lightning_spider_egg");
        nonNameMatchingTextureItem(ModItems.JUMPING_PSYCHIC_SPIDER_EGG, "psychic_spider_egg");
        nonNameMatchingTextureItem(ModItems.DAMAGED_PSYCHIC_SPIDER_EGG, "psychic_spider_egg");
        nonNameMatchingTextureItem(ModItems.JUMPING_BLAST_SPIDER_EGG, "blast_spider_egg");
        nonNameMatchingTextureItem(ModItems.DAMAGED_BLAST_SPIDER_EGG, "blast_spider_egg");

        wallItem(ModBlocks.CHITIN_WALL, ModBlocks.CHITIN_BLOCK);
        wallItem(ModBlocks.CHITIN_BRICK_WALL, ModBlocks.CHITIN_BRICKS);
        evenSimplerBlockItem(ModBlocks.CHITIN_SLAB);
        evenSimplerBlockItem(ModBlocks.CHITIN_BRICK_SLAB);
        evenSimplerBlockItem(ModBlocks.CHITIN_STAIRS);
        evenSimplerBlockItem(ModBlocks.CHITIN_BRICK_STAIRS);

        withExistingParent(ModBlocks.COBWEB_TRAP.getId().getPath(),
                modLoc("item/cobweb_trap_item"));

        withExistingParent(ModItems.CRIMSON_BEETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.GRUB_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.BRILLIANT_BEETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.MANTIS_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

        withExistingParent(ModItems.HARVEST_BEETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.JEWEL_BEETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.CHORUS_BEETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
        withExistingParent(ModItems.ANCIENT_DEBREETLE_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));

    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(infested.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder nonNameMatchingTextureItem(RegistryObject<Item> item, String textureName) {
        return withExistingParent(item.getId().getPath(), new ResourceLocation("item/generated"))
                .texture("layer0", modLoc("item/" + textureName));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(infested.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseblock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(infested.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseblock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(infested.MOD_ID, "item/" + item.getId().getPath()));
    }
}
