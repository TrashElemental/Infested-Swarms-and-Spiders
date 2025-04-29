package net.trashelemental.infested.magic.brewing.recipes.potions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.trashelemental.infested.magic.brewing.ModPotions;

@Mod.EventBusSubscriber(modid = "infested", bus = Mod.EventBusSubscriber.Bus.MOD)
public class ResistancePotionStrongBrewingRecipe implements IBrewingRecipe {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new ResistancePotionStrongBrewingRecipe()));
    }

    @Override
    public boolean isInput(ItemStack input) {
        Ingredient awkwardPotions = Ingredient.of(
                PotionUtils.setPotion(new ItemStack(Items.POTION), ModPotions.RESISTANCE_POTION.get()),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), ModPotions.RESISTANCE_POTION.get()),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), ModPotions.RESISTANCE_POTION.get())
        );
        return awkwardPotions.test(input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)).test(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return PotionUtils.setPotion(new ItemStack(input.getItem()), ModPotions.RESISTANCE_POTION_STRONG.get());
        }
        return ItemStack.EMPTY;
    }
}
