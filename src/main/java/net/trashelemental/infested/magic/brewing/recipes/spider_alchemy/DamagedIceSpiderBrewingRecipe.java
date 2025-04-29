package net.trashelemental.infested.magic.brewing.recipes.spider_alchemy;

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
public class DamagedIceSpiderBrewingRecipe implements IBrewingRecipe {
    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> BrewingRecipeRegistry.addRecipe(new DamagedIceSpiderBrewingRecipe()));
    }

    @Override
    public boolean isInput(ItemStack input) {
        return Ingredient.of(new ItemStack(ModItems.JUMPING_ICE_SPIDER_EGG.get())).test(input);
    }

    @Override
    public boolean isIngredient(ItemStack ingredient) {
        return Ingredient.of(new ItemStack(Items.GLOWSTONE_DUST)).test(ingredient);
    }

    @Override
    public ItemStack getOutput(ItemStack input, ItemStack ingredient) {
        if (isInput(input) && isIngredient(ingredient)) {
            return new ItemStack(ModItems.DAMAGED_ICE_SPIDER_EGG.get());
        }
        return ItemStack.EMPTY;
    }
}
