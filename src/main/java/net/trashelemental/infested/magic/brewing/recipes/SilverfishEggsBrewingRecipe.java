package net.trashelemental.infested.magic.brewing.recipes;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.trashelemental.infested.item.ModItems;

@Mod.EventBusSubscriber(modid = "infested", bus = Mod.EventBusSubscriber.Bus.MOD)
public class SilverfishEggsBrewingRecipe implements IBrewingRecipe {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new SilverfishEggsBrewingRecipe()));
    }

    @Override
    public boolean isInput(ItemStack input) {
        return Ingredient.of(new ItemStack(Items.EGG)).test(input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(new ItemStack(ModItems.CHITIN.get())).test(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return new ItemStack(ModItems.SILVERFISH_EGGS.get());
        }
        return ItemStack.EMPTY;
    }
}
