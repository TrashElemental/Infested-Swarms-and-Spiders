package net.trashelemental.infested.event.custom;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingChangeTargetEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.effects.ModMobEffects;
import net.trashelemental.infested.entity.custom.MantisEntity;
import net.trashelemental.infested.infested;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class MantisEvents {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event != null && event.getEntity() != null && event.getSource().getEntity() != null) {
            execute(event, event.getEntity().level(), event.getEntity(), event.getSource().getEntity());
        }
    }

    private static void execute(@Nullable Event event, Level world, Entity entity, Entity sourceEntity) {

        //When the mantis kills an arthropod, it is healed to max health.
        if (entity instanceof LivingEntity killedEntity && sourceEntity instanceof MantisEntity mantis) {
            if (killedEntity.getMobType() == MobType.ARTHROPOD) {

                mantis.setHealth(mantis.getMaxHealth());

                ((ServerLevel) world).sendParticles(
                        ParticleTypes.HAPPY_VILLAGER,
                        mantis.getX(), mantis.getY() + 1, mantis.getZ(),
                        10, 0.5, 0.5, 0.5, 0.1);

                world.playSound(null, mantis.blockPosition(),
                        SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
                infested.queueServerWork(5, () -> world.playSound(null, mantis.blockPosition(),
                        SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));
                infested.queueServerWork(10, () -> world.playSound(null, mantis.blockPosition(),
                        SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));
            }
        }
    }

    @SubscribeEvent
    public static void onEntitySetsAttackTarget(LivingChangeTargetEvent event) {
        execute(event, event.getEntity());
    }

    public static void execute(Entity sourceentity) {
        execute(null, sourceentity);
    }

    private static void execute(@Nullable Event event, Entity sourceentity) {
        if (sourceentity == null || !(sourceentity instanceof MantisEntity mantisEntity)) {
            return;
        }

        if (!mantisEntity.hasEffect(ModMobEffects.AMBUSH_COOLDOWN.get())) {
            mantisEntity.addEffect(new MobEffectInstance(ModMobEffects.AMBUSH.get(), 300, 0, false, true));
        }
    }

}
