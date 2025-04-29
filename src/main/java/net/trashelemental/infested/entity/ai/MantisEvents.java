package net.trashelemental.infested.entity.ai;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.custom.MantisEntity;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import net.trashelemental.infested.util.event.UtilEvents;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

@Mod.EventBusSubscriber
public class MantisEvents {

    //When the mantis kills an arthropod, it is healed to max health.
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof MantisEntity mantis)) {
            return;
        }

        if (!(event.getEntity().getMobType() == MobType.ARTHROPOD)) {
            return;
        }

        Level level = event.getEntity().level();

        mantis.setHealth(mantis.getMaxHealth());

        ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.HAPPY_VILLAGER,
                mantis.getX(), mantis.getY(), mantis.getZ(), 10, 1);

        UtilEvents.playEatSound(level, mantis);
    }

    //When a mantis begins targeting an entity, it applies ambush to itself if it can.
    @SubscribeEvent
    public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof MantisEntity mantis)) {
            return;
        }

        if (!mantis.hasEffect(ModMobEffects.AMBUSH_COOLDOWN.get())) {
            mantis.addEffect(new MobEffectInstance(ModMobEffects.AMBUSH.get(), 300, 0, false, true));
        }
    }
}
