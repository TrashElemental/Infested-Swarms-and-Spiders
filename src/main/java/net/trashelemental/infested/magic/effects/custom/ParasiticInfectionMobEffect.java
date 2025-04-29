package net.trashelemental.infested.magic.effects.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

public class ParasiticInfectionMobEffect extends MobEffect {
    public ParasiticInfectionMobEffect() { super(MobEffectCategory.HARMFUL, -13434109); }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }

    @Override
    public void addAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
    }

    @Override
    public void removeAttributeModifiers(LivingEntity entity, AttributeMap attributeMap, int amplifier) {
        Level level = entity.level();

        entity.hurt(new DamageSource(level.registryAccess().registryOrThrow(
                Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), 30);

        infested.queueServerWork(20, () -> {
            if (level instanceof ServerLevel _level) {

                ParticleMethods.ParticlesAroundServerSide(_level, ParticleTypes.POOF,
                        entity.getX(), entity.getY(), entity.getZ(), 5, 1);

                _level.playSound(null, BlockPos.containing(entity.getX(), entity.getY(), entity.getZ()),
                        SoundEvents.SNIFFER_EGG_HATCH,
                        SoundSource.NEUTRAL, 1, 2);

                EntityType<?> crimsonBeetle = ModEntities.CRIMSON_BEETLE.get();
                Entity beetleEntity = crimsonBeetle.create(_level);
                if (beetleEntity != null) {
                    beetleEntity.moveTo(entity.getX(), entity.getY(), entity.getZ(), level.getRandom().nextFloat() * 360F, 0);
                    _level.addFreshEntity(beetleEntity);
                }
            }
        });
        super.removeAttributeModifiers(entity, attributeMap, amplifier);
    }

    public ResourceLocation getIcon() {
        return new ResourceLocation("infested", "textures/mob_effect/parasitic_infection.png");
    }

}
