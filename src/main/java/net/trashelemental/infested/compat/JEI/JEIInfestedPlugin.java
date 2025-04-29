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
                new ItemStack(ModItems.BUG_STEW.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.bug_stew_info"));

        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.COBWEB_BOMB.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.cobweb_bomb_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SPIDER_EGG_SAC.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_egg_sac_info"));


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


        //Equipment
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.MANTIS_SICKLE.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.mantis_sickle_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.SPIDER_SICA.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_sica_info"));
        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.STINGER_PONIARD.get())), VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.stinger_poniard_info"));


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

        registration.addIngredientInfo(List.of(
                        new ItemStack(ModItems.BEE_HELMET.get()),
                        new ItemStack(ModItems.BEE_CHESTPLATE.get()),
                        new ItemStack(ModItems.BEE_LEGGINGS.get()),
                        new ItemStack(ModItems.BEE_BOOTS.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.bee_armor_info"));

        registration.addIngredientInfo(List.of(
                new ItemStack(ModItems.JUMPING_FIRE_SPIDER_EGG.get()),
                new ItemStack(ModItems.DAMAGED_FIRE_SPIDER_EGG.get()),
                new ItemStack(ModItems.CLOAKED_FIRE_SPIDER_EGG.get()),
                new ItemStack(ModItems.JUMPING_LIGHTNING_SPIDER_EGG.get()),
                new ItemStack(ModItems.DAMAGED_LIGHTNING_SPIDER_EGG.get()),
                new ItemStack(ModItems.CLOAKED_LIGHTNING_SPIDER_EGG.get()),
                new ItemStack(ModItems.JUMPING_ICE_SPIDER_EGG.get()),
                new ItemStack(ModItems.DAMAGED_ICE_SPIDER_EGG.get()),
                new ItemStack(ModItems.CLOAKED_ICE_SPIDER_EGG.get()),
                new ItemStack(ModItems.JUMPING_BLAST_SPIDER_EGG.get()),
                new ItemStack(ModItems.DAMAGED_BLAST_SPIDER_EGG.get()),
                new ItemStack(ModItems.JUMPING_PSYCHIC_SPIDER_EGG.get()),
                new ItemStack(ModItems.DAMAGED_PSYCHIC_SPIDER_EGG.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.spider_alchemy_info"));

        registration.addIngredientInfo(List.of(
                        new ItemStack(ModItems.JUMPING_FIRE_SPIDER_EGG.get()),
                        new ItemStack(ModItems.JUMPING_LIGHTNING_SPIDER_EGG.get()),
                        new ItemStack(ModItems.JUMPING_ICE_SPIDER_EGG.get()),
                        new ItemStack(ModItems.JUMPING_BLAST_SPIDER_EGG.get()),
                        new ItemStack(ModItems.JUMPING_PSYCHIC_SPIDER_EGG.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.jumping_spider_info"));

        registration.addIngredientInfo(List.of(
                        new ItemStack(ModItems.DAMAGED_FIRE_SPIDER_EGG.get()),
                        new ItemStack(ModItems.DAMAGED_LIGHTNING_SPIDER_EGG.get()),
                        new ItemStack(ModItems.DAMAGED_ICE_SPIDER_EGG.get()),
                        new ItemStack(ModItems.DAMAGED_BLAST_SPIDER_EGG.get()),
                        new ItemStack(ModItems.DAMAGED_PSYCHIC_SPIDER_EGG.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.damaged_spider_info"));

        registration.addIngredientInfo(List.of(
                        new ItemStack(ModItems.CLOAKED_FIRE_SPIDER_EGG.get()),
                        new ItemStack(ModItems.CLOAKED_LIGHTNING_SPIDER_EGG.get()),
                        new ItemStack(ModItems.CLOAKED_ICE_SPIDER_EGG.get())),
                VanillaTypes.ITEM_STACK, Component.translatable("jei.infested.cloaked_spider_info"));

    }

}
