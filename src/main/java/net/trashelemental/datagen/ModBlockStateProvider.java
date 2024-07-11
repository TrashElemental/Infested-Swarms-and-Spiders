package net.trashelemental.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, infested.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        BlockWithItem(ModBlocks.CHITIN_BLOCK);
        slabBlock(((SlabBlock) ModBlocks.CHITIN_SLAB.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        stairsBlock(((StairBlock) ModBlocks.CHITIN_STAIRS.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        wallBlock(((WallBlock) ModBlocks.CHITIN_WALL.get()), blockTexture(ModBlocks.CHITIN_BLOCK.get()));
        BlockWithItem(ModBlocks.CHITIN_BRICKS);
        slabBlock(((SlabBlock) ModBlocks.CHITIN_BRICK_SLAB.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        stairsBlock(((StairBlock) ModBlocks.CHITIN_BRICK_STAIRS.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        wallBlock(((WallBlock) ModBlocks.CHITIN_BRICK_WALL.get()), blockTexture(ModBlocks.CHITIN_BRICKS.get()));
        BlockWithItem(ModBlocks.CHISELED_CHITIN_BRICKS);

        BlockWithItem(ModBlocks.SILVERFISH_TRAP);
    }

    private void BlockWithItem (RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
