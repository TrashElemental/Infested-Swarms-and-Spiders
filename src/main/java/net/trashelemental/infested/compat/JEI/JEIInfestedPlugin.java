package net.trashelemental.infested.compat.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.util.List;

@JeiPlugin
public class JEIInfestedPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(infested.MOD_ID, "jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {

        //Functional Items
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SPIDER_EGG.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_egg_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SILVERFISH_EGGS.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.silverfish_eggs_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.RAW_GRUB.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.raw_grub_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.BEE_EGGS.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.bee_eggs_info"));

        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SWARM_CELL.get()),
                new ItemStack(ModItems.SWARM_SAC.get()),
                new ItemStack(ModItems.SWARM_STONE.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.swarm_cell_info"));


        //Functional Blocks
        registration.addIngredientInfo(List.of(
                new ItemStack(ModBlocks.COBWEB_TRAP.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.cobweb_trap_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModBlocks.SPINNERET.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spinneret_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModBlocks.SILVERFISH_TRAP.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.silverfish_trap_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModBlocks.SPIDER_TRAP.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_trap_info"));


        //Armors
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SPIDER_HELMET.get()),
                new ItemStack(ModItems.SPIDER_CHESTPLATE.get()),
                new ItemStack(ModItems.SPIDER_LEGGINGS.get()),
                new ItemStack(ModItems.SPIDER_BOOTS.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_armor_info"));

        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.CHITIN_HELMET.get()),
                new ItemStack(ModItems.CHITIN_CHESTPLATE.get()),
                new ItemStack(ModItems.CHITIN_LEGGINGS.get()),
                new ItemStack(ModItems.CHITIN_BOOTS.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.chitin_armor_info"));



    }

}
