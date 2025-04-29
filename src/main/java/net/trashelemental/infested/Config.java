package net.trashelemental.infested;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static final ForgeConfigSpec.BooleanValue FEED_MOD_PETS_MEAT = BUILDER
            .comment("Controls whether tamed mobs from this mod can be fed meat to heal them, like wolves. Defaults to false.")
            .define("Tamed mobs from this mod can be healed with meat:", false);

    public static final ForgeConfigSpec.IntValue DEFAULT_TAMED_SPIDER_HEALTH = BUILDER
            .comment("Default maximum health value of Tamed Spiders. Defaults to 15.")
            .defineInRange("Tamed Spider Max Health:", 15, 1, 100);

    public static final ForgeConfigSpec.BooleanValue MANTIS_PREY_ON_MODDED = BUILDER
            .comment("Controls whether wild or wandering mantises will attempt to predate arthropods from other mods. Defaults to false.")
            .define("Mantises will predate arthropods from other mods:", false);
    public static final ForgeConfigSpec.BooleanValue MANTIS_PREY_ON_BEES = BUILDER
            .comment("Controls whether wild or wandering mantises will attempt to predate bees. Defaults to false.")
            .define("Mantises will predate bees:", false);
    public static final ForgeConfigSpec.BooleanValue MANTIS_PREY_ON_TAMED = BUILDER
            .comment("Controls whether wild mantises will attempt to predate tamed entities. Defaults to true.")
            .define("Mantises will predate tamed entities:", true);

    public static final ForgeConfigSpec.DoubleValue HARVEST_BEETLE_CHANCE = BUILDER
            .comment("Chance for Harvest Beetles to spawn when grasses, ferns, and dead bushes are broken. Defaults to 5%.")
            .defineInRange("Harvest Beetle Spawn Chance:", 0.05, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue JEWEL_BEETLE_CHANCE = BUILDER
            .comment("Chance for Jewel Beetles to spawn when deepslate ores are broken. Defaults to 5%.")
            .defineInRange("Jewel Beetle Spawn Chance:", 0.05, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue CHORUS_BEETLE_CHANCE = BUILDER
            .comment("Chance for Chorus Beetles to spawn when End Stone is broken. Defaults to 1%.")
            .defineInRange("Chorus Beetle Spawn Chance:", 0.01, 0.0, 1.0);
    public static final ForgeConfigSpec.DoubleValue DEBRIS_BEETLE_CHANCE = BUILDER
            .comment("Chance for Ancient Debreetles to spawn when Netherrack is broken. Defaults to 0.5%.")
            .defineInRange("Ancient Debreetle Spawn Chance:", 0.005, 0.0, 1.0);



    static final ForgeConfigSpec SPEC = BUILDER.build();

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        if (event.getConfig().getSpec() == Config.SPEC) {
        }

    }
}
