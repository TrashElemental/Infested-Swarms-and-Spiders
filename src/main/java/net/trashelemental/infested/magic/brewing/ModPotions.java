package net.trashelemental.infested.magic.brewing;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.magic.effects.ModMobEffects;

public class ModPotions {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(ForgeRegistries.POTIONS, infested.MOD_ID);


    public static final RegistryObject<Potion> RESISTANCE_POTION = REGISTRY.register("resistance_potion", () ->
            new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 900, 0, false, true)));
    public static final RegistryObject<Potion> RESISTANCE_POTION_LONG = REGISTRY.register("resistance_potion_long", () -> 
            new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1800, 0, false, true)));
    public static final RegistryObject<Potion> RESISTANCE_POTION_STRONG = REGISTRY.register("resistance_potion_strong", () -> 
            new Potion(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 440, 1, false, true)));

    public static final RegistryObject<Potion> AMBUSH_POTION = REGISTRY.register("ambush_potion", () ->
            new Potion(new MobEffectInstance(ModMobEffects.AMBUSH.get(), 300, 0, false, true)));


    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
