package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
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
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;

import java.util.Objects;

public class SilverfishMinionEntity extends MinionEntity {
    public SilverfishMinionEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, ParticleTypes.POOF, SoundEvents.BEEHIVE_ENTER);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Player.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });
        this.targetSelector.addGoal(4, new NearestAttackableTargetGoal<>(this, Monster.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, SpiderMinionEntity.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, BeeMinionEntity.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });

        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(9, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0);
    }



    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    //Sound Events
    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SILVERFISH_AMBIENT;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(SoundEvents.SILVERFISH_STEP), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.SILVERFISH_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SILVERFISH_DEATH;
    }
}
