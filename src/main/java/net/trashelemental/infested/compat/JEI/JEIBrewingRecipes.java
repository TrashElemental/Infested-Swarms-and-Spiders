package net.trashelemental.infested.compat.JEI;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.recipe.vanilla.IJeiBrewingRecipe;
import mezz.jei.api.recipe.vanilla.IVanillaRecipeFactory;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.brewing.ModPotions;

import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("deprecation")
@JeiPlugin
public class JEIBrewingRecipes implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation("infested:brewing_recipes");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        IVanillaRecipeFactory factory = registration.getVanillaRecipeFactory();

        List<IJeiBrewingRecipe> brewingRecipes = new ArrayList<>();
        ItemStack potion = new ItemStack(Items.POTION);
        ItemStack potion2 = new ItemStack(Items.POTION);
        List<ItemStack> ingredientStack = new ArrayList<>();
        List<ItemStack> inputStack = new ArrayList<>();



        //Non-Potion Items
        ingredientStack.add(new ItemStack(Items.FERMENTED_SPIDER_EYE)); //Item in the top
        inputStack.add(new ItemStack(Items.EGG));                       //Item in the bottom
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                List.copyOf(inputStack),
                new ItemStack(ModItems.SPIDER_EGG.get())                //Result
        ));
        inputStack.clear();
        ingredientStack.clear();


        ingredientStack.add(new ItemStack(ModItems.CHITIN.get()));
        inputStack.add(new ItemStack(Items.EGG));
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                List.copyOf(inputStack),
                new ItemStack(ModItems.SILVERFISH_EGGS.get())
        ));
        inputStack.clear();
        ingredientStack.clear();


        ingredientStack.add(new ItemStack(Items.HONEYCOMB));
        inputStack.add(new ItemStack(ModItems.SILVERFISH_EGGS.get()));
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                List.copyOf(inputStack),
                new ItemStack(ModItems.BEE_EGGS.get())
        ));
        inputStack.clear();
        ingredientStack.clear();

        //Potion Items
        ingredientStack.add(new ItemStack(ModItems.CHITIN.get()));
        PotionUtils.setPotion(potion, Potions.AWKWARD);
        PotionUtils.setPotion(potion2, ModPotions.RESISTANCE_POTION.get());
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                potion.copy(),
                potion2.copy()
        ));

        ingredientStack.clear();
        ingredientStack.add(new ItemStack(Items.REDSTONE));
        PotionUtils.setPotion(potion, ModPotions.RESISTANCE_POTION.get());
        PotionUtils.setPotion(potion2, ModPotions.RESISTANCE_POTION_LONG.get());
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                potion.copy(),
                potion2.copy()
        ));

        ingredientStack.clear();
        ingredientStack.add(new ItemStack(Items.GLOWSTONE_DUST));
        PotionUtils.setPotion(potion, ModPotions.RESISTANCE_POTION.get());
        PotionUtils.setPotion(potion2, ModPotions.RESISTANCE_POTION_STRONG.get());
        brewingRecipes.add(factory.createBrewingRecipe(
                List.copyOf(ingredientStack),
                potion.copy(),
                potion2.copy()
        ));



        registration.addRecipes(RecipeTypes.BREWING, brewingRecipes);
    }
}
