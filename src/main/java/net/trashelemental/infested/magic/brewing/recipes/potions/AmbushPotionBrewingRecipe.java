package net.trashelemental.infested.magic.brewing.recipes.potions;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.common.brewing.IBrewingRecipe;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.brewing.ModPotions;

@Mod.EventBusSubscriber(modid = "infested", bus = Mod.EventBusSubscriber.Bus.MOD)
public class AmbushPotionBrewingRecipe implements IBrewingRecipe {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new AmbushPotionBrewingRecipe()));
    }

    @Override
    public boolean isInput(ItemStack input) {
        Ingredient awkwardPotions = Ingredient.of(
                PotionUtils.setPotion(new ItemStack(Items.POTION), Potions.AWKWARD),
                PotionUtils.setPotion(new ItemStack(Items.SPLASH_POTION), Potions.AWKWARD),
                PotionUtils.setPotion(new ItemStack(Items.LINGERING_POTION), Potions.AWKWARD)
        );
        return awkwardPotions.test(input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(new ItemStack(ModItems.MANTIS_CLAW.get())).test(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return PotionUtils.setPotion(new ItemStack(input.getItem()), ModPotions.AMBUSH_POTION.get());
        }
        return ItemStack.EMPTY;
    }
}
