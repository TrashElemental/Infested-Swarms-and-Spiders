package net.trashelemental.infested.entity.custom.loot_beetles;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;

public class LootBeetleEntity extends Animal implements GeoEntity {

    private final ParticleOptions particles;
    private final SoundEvent despawnSound;
    private final int experienceDrop;

    public LootBeetleEntity(EntityType<? extends Animal> pEntityType, Level pLevel, ParticleOptions particles, SoundEvent despawnSound, int experienceDrop) {
        super(pEntityType, pLevel);
        this.particles = particles;
        this.despawnSound = despawnSound;
        this.experienceDrop = experienceDrop;
    }

    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
            this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, (float) 10, 1, 1.2));
            this.goalSelector.addGoal(1, new PanicGoal(this, 1));
            this.goalSelector.addGoal(2, new FloatGoal(this));
            this.goalSelector.addGoal(3, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
            this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 10)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 26)
                .add(Attributes.ATTACK_KNOCKBACK, 0);

    }

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }


    //Sound Events
    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(SoundEvents.SPIDER_STEP), 0.15f, 1);
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.TROPICAL_FISH_HURT;
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.TROPICAL_FISH_DEATH;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }



    //Custom Behaviors

    //Appearance effects and setting the timer for it to despawn (using age)
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        ParticleMethods.ParticlesBurst(level(), particles, this.getX(), this.getY(), this.getZ(), 10, 0.1);
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                SoundEvents.AMETHYST_BLOCK_CHIME, this.getSoundSource(), 10.0F, 3.0F);
    }

    //De-spawns when its time limit runs out and disappearance effects
    private int lifespan = 200;

    @Override
    public void tick() {
        super.tick();

        if (!this.level().isClientSide && !(this.isNoAi())) {
            lifespan--;

            if (lifespan <= 0) {
                ParticleMethods.ParticlesBurst(level(), particles, this.getX(), this.getY(), this.getZ(), 10, 0.1);
                this.level().playSound(null, this.getX(), this.getY(), this.getZ(), despawnSound, this.getSoundSource(), 1.0F, 3.0F);
                this.discard();
            }
        }
    }

    //Drops more experience than the average mob
    @Override
    public int getExperienceReward() {
        return experienceDrop;
    }


    //GeckoLib
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
    }


    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<GeoAnimatable> state) {

        if(state.isMoving()) {
            state.getController().setAnimation(RawAnimation.begin().then("WALK", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        state.getController().setAnimation(RawAnimation.begin().then("IDLE", Animation.LoopType.LOOP));
        return PlayState.CONTINUE;

    }

    private AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
