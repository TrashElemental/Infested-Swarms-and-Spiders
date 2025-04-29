package net.trashelemental.infested.magic.effects.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class ParasiticInfectionDeathEvent {

    /**
     * When an entity with the Parasitic Infection effect dies, they will
     * spawn a Crimson Beetle at their location.
     */

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        Level level = entity.level();

        if (entity.hasEffect(ModMobEffects.PARASITIC_INFECTION.get())) {

            infested.queueServerWork(20, () -> {
                if (level instanceof ServerLevel world) {

                    ParticleMethods.ParticlesAroundServerSide(world, ParticleTypes.POOF,
                            entity.getX(), entity.getY(), entity.getZ(), 5, 1);

                    world.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
                            SoundEvents.SNIFFER_EGG_HATCH, SoundSource.NEUTRAL, 1, 2);

                    EntityType<?> crimsonBeetle = ModEntities.CRIMSON_BEETLE.get();
                    Entity beetleEntity = crimsonBeetle.create(world);
                    if (beetleEntity != null) {
                        beetleEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), world.getRandom().nextFloat() * 360F, 0);
                        world.addFreshEntity(beetleEntity);
                    }
                }
            });

        }
    }
}
