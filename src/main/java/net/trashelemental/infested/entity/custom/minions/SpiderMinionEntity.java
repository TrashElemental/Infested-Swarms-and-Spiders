package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.trashelemental.infested.entity.ai.NonTeleportingFollowOwnerGoal;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;

import java.util.Objects;

public class SpiderMinionEntity extends MinionEntity {
    public SpiderMinionEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, ParticleTypes.POOF, SoundEvents.SPIDER_AMBIENT);
    }

    public boolean shouldUseRedEyes() {
        return !this.isTame();
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
        this.targetSelector.addGoal(5, new NearestAttackableTargetGoal<>(this, SilverfishMinionEntity.class, false, false) {
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
        this.goalSelector.addGoal(8, new LeapAtTargetGoal(this, (float) 0.5));
        this.goalSelector.addGoal(9, new NonTeleportingFollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(10, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(11, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(12, new FloatGoal(this));
        this.goalSelector.addGoal(13, new LookAtPlayerGoal(this, Player.class, (float) 6));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 4)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 2)
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
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(SoundEvents.SPIDER_STEP), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    //Spider Climbing Behavior
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(SpiderMinionEntity.class, EntityDataSerializers.BYTE);

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    public boolean onClimbable() {
        return this.isClimbing();
    }

    public boolean isClimbing() {
        return (this.entityData.get(DATA_FLAGS_ID) & 1) != 0;
    }

    public void setClimbing(boolean p_33820_) {
        byte b0 = this.entityData.get(DATA_FLAGS_ID);
        if (p_33820_) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }
        this.entityData.set(DATA_FLAGS_ID, b0);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
    }

    //Poison immunity
    @Override
    public boolean canBeAffected(MobEffectInstance pPotioneffect) {
        if (pPotioneffect.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, pPotioneffect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(pPotioneffect);
        }
    }

    //Cobweb immunity
    @Override
    public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
        if (!pState.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(pState, pMotionMultiplier);
        }
    }

    //Poison Attack
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (super.doHurtTarget(pEntity)) {
            if (pEntity instanceof LivingEntity) {
                ((LivingEntity) pEntity).addEffect(new MobEffectInstance(MobEffects.POISON, 100, 0), this);
            }
            return true;
        } else {
            return false;
        }
    }

    //Takes minimal fall damage.
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.is(DamageTypes.FALL)) {
            pAmount = Math.min(pAmount, 2.0F);
        }
        return super.hurt(pSource, pAmount);
    }
}
