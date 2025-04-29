package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.entity.ModEntities;
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

public class CrimsonBeetleEntity extends Animal implements GeoEntity {

    public CrimsonBeetleEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals(); {

            this.goalSelector.addGoal(0, new MeleeAttackGoal(this, 1.2, false) {
                @Override
                protected double getAttackReachSqr(LivingEntity entity) {
                    return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
                }
            });
            this.targetSelector.addGoal(1, new HurtByTargetGoal(this));

            this.goalSelector.addGoal(2, new FloatGoal(this));
            this.goalSelector.addGoal(3, new BreedGoal(this, 1));
            this.goalSelector.addGoal(4, new TemptGoal(this, 1, Ingredient.of(Items.ROTTEN_FLESH), false));
            this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
       return Animal.createLivingAttributes()
               .add(Attributes.MAX_HEALTH, 10)
               .add(Attributes.MOVEMENT_SPEED, 0.25D)
               .add(Attributes.ATTACK_DAMAGE, 2)
               .add(Attributes.ARMOR, 1)
               .add(Attributes.FOLLOW_RANGE, 16)
               .add(Attributes.ATTACK_KNOCKBACK, 0);
    }

    @Override
    public boolean isFood(ItemStack pStack) {
        return pStack.is(Items.ROTTEN_FLESH);
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

    //Spawning
    public static boolean canSpawn(EntityType<CrimsonBeetleEntity> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
        BlockState below = level.getBlockState(pos.below());
        BlockState current = level.getBlockState(pos);

        return current.isAir() &&
                below.isFaceSturdy(level, pos.below(), Direction.UP) &&
                !below.is(Blocks.NETHER_WART_BLOCK) &&
                !below.is(Blocks.SHROOMLIGHT) &&
                !below.is(Blocks.WEEPING_VINES) &&
                !below.is(Blocks.WEEPING_VINES_PLANT) &&
                !below.is(Blocks.GLOWSTONE) &&
                !below.is(Blocks.AIR) &&
                !below.is(Blocks.CRIMSON_STEM);
    }

    //Custom Behavior

    //Breed Offspring are Grubs, not Crimson Beetles
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.GRUB.get().create(serverLevel);
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
