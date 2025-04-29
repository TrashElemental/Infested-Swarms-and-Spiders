package net.trashelemental.infested.entity.custom.spider_alchemy;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;

import java.util.List;

public class DamagedSpiderEntity extends AlchemySpiderEntity {
    public DamagedSpiderEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 0)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    /**
     * The Damaged Spider type is essentially a landmine / grenade that will stay in place, and explode if attacked
     * or if a non-allied entity to its player comes too close.
     */
    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && !hasBlasted) {
            List<LivingEntity> nearbyEntities = this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(1.0D));

            for (LivingEntity entity : nearbyEntities) {
                if (entity != this && this.shouldBlastEffectEntity(entity) && !hasBlasted) {
                    this.doBlast();
                    this.discard();
                    hasBlasted = true;
                    break;
                }
            }
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (!hasBlasted) {
            this.doBlast();
            this.discard();
            hasBlasted = true;
        }

        return super.hurt(source, amount);
    }
}
