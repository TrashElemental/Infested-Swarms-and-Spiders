package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class GrubEntity extends Animal implements GeoEntity {

    public GrubEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals(); {

            this.goalSelector.addGoal(0, new PanicGoal(this, 1));
            this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1, false) {
                @Override
                protected double getAttackReachSqr(LivingEntity entity) {
                    return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
                }
            });
            this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, ZombifiedPiglin.class, 10, true, false,
                    (target) -> !target.hasEffect(ModMobEffects.PARASITIC_INFECTION.get())));
            this.goalSelector.addGoal(3, new LeapAtTargetGoal(this, (float) 0.5f));

            this.goalSelector.addGoal(4, new FloatGoal(this));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1, Ingredient.of(Items.ROTTEN_FLESH), false));
            this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));

        }
    }

    public static AttributeSupplier.Builder createAttributes() {
       return Animal.createLivingAttributes()
               .add(Attributes.MAX_HEALTH, 4)
               .add(Attributes.MOVEMENT_SPEED, 0.2)
               .add(Attributes.ATTACK_DAMAGE, 1)
               .add(Attributes.ARMOR, 0)
               .add(Attributes.FOLLOW_RANGE, 26)
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
        this.playSound(Objects.requireNonNull(SoundEvents.SILVERFISH_STEP), 0.15f, 1);
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.COD_HURT;
    }
    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.COD_DEATH;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    //Spawning
    public static boolean canSpawn(EntityType<GrubEntity> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
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

    //Custom Behaviors

    //If the Grub is fed Rotten Flesh, it has a 10% chance to grow into a Crimson Beetle.
    //If it's fed Cocoa Beans, a Brilliant Beetle.
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (itemStack.is(Items.ROTTEN_FLESH)) {

            if (!pPlayer.isCreative()) { itemStack.shrink(1); }

            this.playSound(SoundEvents.GENERIC_EAT);
            ParticleMethods.ParticlesAroundServerSide(level(), ParticleTypes.HAPPY_VILLAGER,
                    this.getX(), this.getY(), this.getZ(), 5, 1);

            if (Math.random() >= 0.9) {

                if (!this.level().isClientSide()) {
                    this.discard();
                }

                if (this.level() instanceof ServerLevel _level) {
                    Entity entityToSpawn = ModEntities.CRIMSON_BEETLE.get().spawn(_level, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        entityToSpawn.setYRot(this.level().getRandom().nextFloat() * 360F);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }

        else if (itemStack.is(Items.COCOA_BEANS)) {

            if (!pPlayer.isCreative()) { itemStack.shrink(1); }

            this.playSound(SoundEvents.GENERIC_EAT);
            ParticleMethods.ParticlesAroundServerSide(level(), ParticleTypes.HAPPY_VILLAGER,
                    this.getX(), this.getY(), this.getZ(), 5, 1);

            if (Math.random() >= 0.9) {
                if (!this.level().isClientSide()) {
                    this.discard();
                }
                if (this.level() instanceof ServerLevel _level) {
                    Entity entityToSpawn = ModEntities.BRILLIANT_BEETLE.get().spawn(_level, BlockPos.containing(this.getX(), this.getY(), this.getZ()), MobSpawnType.MOB_SUMMONED);
                    if (entityToSpawn != null) {
                        entityToSpawn.setYRot(this.level().getRandom().nextFloat() * 360F);
                    }
                }
            }
            return InteractionResult.SUCCESS;
        }
        return super.mobInteract(pPlayer, pHand);
    }

    //Spawns 2-3 Grubs when breeding Crimson Beetles, rather than 1.
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        if (this.isBaby()) {
            this.setAge(0);

            int numberOfEntities = this.random.nextInt(2) + 1;
            for (int i = 0; i < numberOfEntities; i++) {
                GrubEntity newGrub = ModEntities.GRUB.get().create(this.level());
                if (newGrub != null) {
                    newGrub.setPos(this.getX() + this.random.nextDouble() - 0.5,
                            this.getY(),
                            this.getZ() + this.random.nextDouble() - 0.5);
                    this.level().addFreshEntity(newGrub);
                }
            }
        }
    }

    @Override
    public boolean doHurtTarget(Entity entity) {

        Level level = this.level();
        LivingEntity target = (LivingEntity) entity;

        level.levelEvent(2001, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
                Block.getId(Blocks.CRIMSON_STEM.defaultBlockState()));
        level.playSound(null, new BlockPos((int) entity.getX(), (int) entity.getY(), (int) entity.getZ()),
                SoundEvents.BEEHIVE_ENTER, SoundSource.NEUTRAL, 1, 1);
        if (!target.hasEffect(ModMobEffects.PARASITIC_INFECTION.get())) {
            target.addEffect(new MobEffectInstance(ModMobEffects.PARASITIC_INFECTION.get(), 2400, 1));
        }
        this.discard();

        return super.doHurtTarget(entity);
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
