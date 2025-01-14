package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

public class BeeMinionEntity extends TamableAnimal implements GeoEntity {

    public BeeMinionEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.isTame = false;
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(4, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, true));
        this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1));
        this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(7, new FloatGoal(this));
        this.goalSelector.addGoal(7, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
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
    protected PathNavigation createNavigation(Level p_level) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, p_level) {
            public boolean isStableDestination(BlockPos p_27947_) {
                return !this.level.getBlockState(p_27947_.below()).isAir();
            }
        };
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(false);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    //Sound Events
    @Override
    protected SoundEvent getAmbientSound() {
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

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }


    //Taming
    private boolean isTame;

    @Override
    public boolean isTame() {
        return this.isTame;
    }

    @Override
    public void setTame(boolean tame) {
        super.setTame(tame);
        this.isTame = tame;
    }

    //Custom Behaviors

    //Owners can't Friendly Fire their Minions
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof LivingEntity attacker) {
            if (this.getOwnerUUID() != null && this.getOwnerUUID().equals(attacker.getUUID())) {
                return false;
            }
        }

        if (pSource.is(DamageTypes.FALL))
            return false;

        return super.hurt(pSource, pAmount);
    }


    //Poisons Target and dies on attacking
    @Override
    public boolean doHurtTarget(Entity entity) {

        if (entity instanceof LivingEntity) {
            LivingEntity livingentity = (LivingEntity)entity;

            livingentity.addEffect(new MobEffectInstance(MobEffects.POISON, 60, 1), this);

        }

        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.BEE_STING, this.getSoundSource(), 1.0F, 1.0F);
        this.kill();

        return super.doHurtTarget(entity);
    }


    //Appearance effects
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();
        for (int i = 0; i < 5; i++) {
            double offsetX = (this.random.nextDouble() - 0.5) * 0.5;
            double offsetY = (this.random.nextDouble() - 0.5) * 0.5;
            double offsetZ = (this.random.nextDouble() - 0.5) * 0.5;
            this.level().addParticle(
                    ParticleTypes.POOF, this.getX() + offsetX, this.getY() + offsetY, this.getZ() + offsetZ,
                    0.0D, 0.0D, 0.0D);
        }
    }

    //De-spawns when its time limit runs out and disappearance effects
    @Override
    public void tick() {
        super.tick();

        if (this.getAge() == 0) {

            CompoundTag minionTag = this.getPersistentData().getCompound("SwarmCellMinionTag");
            if (minionTag != null && minionTag.contains("Origin") && "SwarmCell".equals(minionTag.getString("Origin"))) {

                return;
            }

            if (this.level() instanceof ServerLevel serverLevel) {
                serverLevel.sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 5, 0, 0, 0, 0);
            }
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BEEHIVE_ENTER, this.getSoundSource(), 1.0F, 3.0F);
            this.discard();
        }
    }

    //No XP farming for you
    @Override
    public boolean shouldDropExperience() {
        return false;
    }

    //GeckoLib
    public boolean isFlying() {
        return !this.onGround();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
    }


    private PlayState predicate(AnimationState<GeoAnimatable> state) {

        if (this.isAggressive() && this.getTarget() != null && this.isWithinMeleeAttackRange(this.getTarget())) {
            state.getController().setAnimation(RawAnimation.begin().then("BEE_ATTACK", Animation.LoopType.PLAY_ONCE));
            return PlayState.CONTINUE;
        }

        else if (this.isFlying() && !this.isNoAi()) {
            state.getController().setAnimation(RawAnimation.begin().then("BEE_MOVE", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        else
            state.getController().setAnimation(RawAnimation.begin().then("BEE_IDLE", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
