package net.trashelemental.infested.item;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.TierSortingRegistry;
import net.trashelemental.infested.infested;

import java.util.List;

public class ModToolTiers {

    public static final Tier MANTIS = TierSortingRegistry.registerTier(

            new ForgeTier(
                    2,
                    250,
                    8.0f,
                    2.0f,
                    15,
                    Tags.Blocks.NEEDS_WOOD_TOOL,
                    () -> Ingredient.of(ModItems.MANTIS_CLAW.get())),
            new ResourceLocation(infested.MOD_ID, "mantis"),
            List.of(Tiers.IRON), List.of()
    );

}
