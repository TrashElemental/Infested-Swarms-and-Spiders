package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BeeMinionEntity extends MinionEntity implements GeoEntity {

    public BeeMinionEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, ParticleTypes.POOF, SoundEvents.BEEHIVE_ENTER);
        this.moveControl = new FlyingMoveControl(this, 20, true);
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
        this.targetSelector.addGoal(6, new NearestAttackableTargetGoal<>(this, SpiderMinionEntity.class, false, false) {
            @Override
            public boolean canUse() { return super.canUse() && !isTame(); }
        });

        this.goalSelector.addGoal(7, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(8, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, true));
        this.goalSelector.addGoal(9, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(10, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(11, new FloatGoal(this));
        this.goalSelector.addGoal(12, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.ATTACK_KNOCKBACK, 0)
                .add(Attributes.FLYING_SPEED, 0.6);
    }

    //Flying
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation nav = new FlyingPathNavigation(this, level) {
            public boolean isStableDestination(BlockPos pos) {
                return !this.level.getBlockState(pos.below()).isAir();
            }
        };
        nav.setCanOpenDoors(false);
        nav.setCanFloat(false);
        nav.setCanPassDoors(true);
        return nav;
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    //Fall Damage Immunity
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
       if (pSource.is(DamageTypes.FALL))
            return false;
        return super.hurt(pSource, pAmount);
    }

    //Sound Events
    @Override
    public SoundEvent getAmbientSound() {
        if (this.isAggressive()) {
            return SoundEvents.BEE_LOOP_AGGRESSIVE;
        }
        return SoundEvents.BEE_LOOP;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.BEE_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.BEE_DEATH;
    }

    protected float getSoundVolume() {
        return 0.2F;
    }


    //Poisons Target and dies on attacking
    private boolean hasStung = false;

    @Override
    public boolean doHurtTarget(Entity entity) {

        infested.queueServerWork(8, () -> {
            if (!hasStung) {
                super.doHurtTarget(entity);

                if (entity instanceof LivingEntity livingentity) {
                    livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1), this);
                }

                this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                        SoundEvents.BEE_STING, this.getSoundSource(), 1.0F, 1.0F);
                this.kill();
                hasStung = true;
            }
        });

        return false;
    }

    //GeckoLib
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "attackController", 4, this::attackPredicate));
    }

    private PlayState attackPredicate(AnimationState<GeoAnimatable> state) {

        if (this.swinging && state.getController().getAnimationState().equals(AnimationController.State.STOPPED)) {
            state.getController().forceAnimationReset();
            state.getController().setAnimation(RawAnimation.begin().then("BEE_ATTACK", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }


    private PlayState predicate(AnimationState<GeoAnimatable> state) {


        if (this.isFlying() && !this.isNoAi()) {
            state.getController().setAnimation(RawAnimation.begin().then("BEE_MOVE", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(RawAnimation.begin().then("BEE_IDLE", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
