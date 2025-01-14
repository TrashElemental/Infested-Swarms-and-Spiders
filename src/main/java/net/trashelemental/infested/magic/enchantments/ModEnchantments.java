package net.trashelemental.infested.magic.enchantments;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.magic.enchantments.custom.ConjuredSwarmEnchantment;
import net.trashelemental.infested.magic.enchantments.custom.EnsnaringStrikeEnchantment;
import net.trashelemental.infested.magic.enchantments.custom.InfestedEnchantment;
import net.trashelemental.infested.magic.enchantments.custom.ParasitoidEnchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> REGISTRY = DeferredRegister.create(Registries.ENCHANTMENT, infested.MOD_ID);

    public static final RegistryObject<Enchantment> INFESTED = REGISTRY.register("infested", InfestedEnchantment::new);
    public static final RegistryObject<Enchantment> PARASITOID = REGISTRY.register("parasitoid", ParasitoidEnchantment::new);
    public static final RegistryObject<Enchantment> ENSNARING_STRIKE = REGISTRY.register("ensnaring_strike", EnsnaringStrikeEnchantment::new);
    public static final RegistryObject<Enchantment> CONJURED_SWARM = REGISTRY.register("conjured_swarm", ConjuredSwarmEnchantment::new);

    public static void register(IEventBus eventBus) {
        REGISTRY.register(eventBus);
    }
}
