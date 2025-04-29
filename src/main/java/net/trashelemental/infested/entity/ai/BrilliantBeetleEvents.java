package net.trashelemental.infested.entity.ai;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;


@Mod.EventBusSubscriber
public class BrilliantBeetleEvents {

    //Protects the rider of a Brilliant Beetle from fall damage.
    @SubscribeEvent
    public static void cancelFallDamageForRider(LivingHurtEvent event) {
        DamageSource damage = event.getSource();
        Entity entity = event.getEntity();

        if (damage.is(DamageTypes.FALL) && entity.isPassenger()) {
            if (entity.getVehicle() instanceof BrilliantBeetleEntity) {
                event.setCanceled(true);
            }
        }
    }
}

