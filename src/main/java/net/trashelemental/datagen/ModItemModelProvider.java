package net.trashelemental.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.common.Mod;
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
        simpleItem(ModItems.INSECT_TEMPLATE);
        simpleItem(ModItems.SPIDER_TEMPLATE);

        simpleItem(ModItems.RAW_GRUB);
        simpleItem(ModItems.FRIED_GRUB);
        simpleItem(ModItems.BUG_STEW);

        simpleItem(ModItems.SILVERFISH_EGGS);
        simpleItem(ModItems.SPIDER_EGG);

        wallItem(ModBlocks.CHITIN_WALL, ModBlocks.CHITIN_BLOCK);
        wallItem(ModBlocks.CHITIN_BRICK_WALL, ModBlocks.CHITIN_BRICKS);
        evenSimplerBlockItem(ModBlocks.CHITIN_SLAB);
        evenSimplerBlockItem(ModBlocks.CHITIN_BRICK_SLAB);
        evenSimplerBlockItem(ModBlocks.CHITIN_STAIRS);
        evenSimplerBlockItem(ModBlocks.CHITIN_BRICK_STAIRS);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(infested.MOD_ID, "item/" + item.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(infested.MOD_ID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseblock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall", new ResourceLocation(infested.MOD_ID, "block/" + ForgeRegistries.BLOCKS.getKey(baseblock.get()).getPath()));
    }
}
