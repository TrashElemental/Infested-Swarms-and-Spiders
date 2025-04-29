package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.trashelemental.infested.Config;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.ai.MantisAttackGoal;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.TamableEntity;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.*;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Objects;

public class MantisEntity extends TamableEntity implements GeoEntity {

    private static final EntityDataAccessor<Boolean> ORCHID = SynchedEntityData.defineId(MantisEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> STUNTED = SynchedEntityData.defineId(MantisEntity.class, EntityDataSerializers.BOOLEAN);

    public MantisEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, Items.SPIDER_EYE, Items.FERMENTED_SPIDER_EYE);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor pLevel, DifficultyInstance pDifficulty, MobSpawnType pReason, @Nullable SpawnGroupData pSpawnData, @Nullable CompoundTag pDataTag) {
        BlockPos pos = this.getOnPos();
        if (this.level().getBiome(pos).is(Biomes.CHERRY_GROVE)) {
            setOrchidMantisSkin(true);
        }
        return super.finalizeSpawn(pLevel, pDifficulty, pReason, pSpawnData, pDataTag);
    }

    @Override
    public MantisEntity getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        MantisEntity child = ModEntities.MANTIS.get().create(level);
        if (child != null) {
            if (this.shouldUseOrchidMantisSkin()) {
                if (otherParent instanceof MantisEntity otherMantis && otherMantis.shouldUseOrchidMantisSkin()) {
                    child.setOrchidMantisSkin(true);
                } else if (level.getRandom().nextFloat() < 0.5f) {
                    child.setOrchidMantisSkin(true);
                }
            }
        }
        return child;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        {
            this.goalSelector.addGoal(0, new OwnerHurtByTargetGoal(this) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(MantisEntity.this);
                }
            });
            this.targetSelector.addGoal(1, new OwnerHurtTargetGoal(this) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(MantisEntity.this);
                }
            });
            this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
            this.targetSelector.addGoal(3, new MantisAttackGoal<>(
                    this, LivingEntity.class, true, (entity) -> isPrey(entity))
            {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(MantisEntity.this);
                }
            });
            this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.2, false) {
                @Override
                protected double getAttackReachSqr(LivingEntity entity) {
                    return 4;
                }
            });
            this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, 10, 2, false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(MantisEntity.this);
                }
            });
            this.goalSelector.addGoal(6, new FloatGoal(this));
            this.goalSelector.addGoal(7, new BreedGoal(this, 1));
            this.goalSelector.addGoal(8, new FollowParentGoal(this, 1));
            this.goalSelector.addGoal(9, new TemptGoal(this, 1, Ingredient.of(Items.SPIDER_EYE), false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && !stay(MantisEntity.this);
                }
            });
            this.goalSelector.addGoal(10, new TemptGoal(this, 1, Ingredient.of(Items.FERMENTED_SPIDER_EYE), false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && !stay(MantisEntity.this);
                }
            });
            this.goalSelector.addGoal(11, new RandomStrollGoal(this, 1) {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(MantisEntity.this);
                }
            });
            this.goalSelector.addGoal(12, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(13, new RandomLookAroundGoal(this));
        }
    }

    private static final TagKey<EntityType<?>> PREY = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation("infested:mantis_prey"));

    public boolean isPrey(LivingEntity entity) {

        if (entity instanceof MantisEntity) {
            return false;
        }

        if (entity instanceof TamableAnimal tamable && tamable.isTame() && !(this.isTame())) {
            return Config.MANTIS_PREY_ON_TAMED.get();
        }

        if (entity.getType().is(PREY)) {
            return true;
        }

        if (entity instanceof Bee) {
            return Config.MANTIS_PREY_ON_BEES.get();
        }

        else if (entity.getMobType().equals(MobType.ARTHROPOD)) {
            return Config.MANTIS_PREY_ON_MODDED.get();
        }

        return false;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3)
                .add(Attributes.ARMOR, 0)
                .add(Attributes.FOLLOW_RANGE, 16)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.3)
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
        return SoundEvents.PUFFER_FISH_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.PUFFER_FISH_DEATH;
    }


    @Override
    public boolean doHurtTarget(Entity pEntity) {

        infested.queueServerWork(8, () -> super.doHurtTarget(pEntity));

        return false;
    }

    /**
     * Mantises can glide slowly to the ground.
     */
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {

            Vec3 deltaMovement = this.getDeltaMovement();
            this.setDeltaMovement(deltaMovement.multiply(1.0, 0.6, 1.0));
        }
    }

    /**
     * Mantises do not take fall damage or make sounds when they fall.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    /**
     * A tamed mantis can be fed Bug Stew to give it Strength, and a baby
     * orchid mantis can be fed a poisonous potato to stunt its growth,
     * causing it to have the baby orchid mantis skin permanently.
     */
    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (this.isOwnedBy(player)) {
            if (itemstack.getItem() == ModItems.BUG_STEW.get()) {
                if (!this.level().isClientSide) {
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                        if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
                            player.drop(new ItemStack(Items.BOWL), false);
                        }
                    }
                    this.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
                    this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            } else if (itemstack.getItem() == Items.POISONOUS_POTATO && this.shouldUseOrchidMantisSkin() && this.isBaby() && !this.isStunted()) {
                if (!this.level().isClientSide) {
                    this.setStunted(true);

                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                    ParticleMethods.ParticlesAroundServerSide(level(), ParticleTypes.SMOKE,
                            this.getX(), this.getY(), this.getZ(), 5, 1);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }



    //Spawning
    public static boolean canSpawn(EntityType<MantisEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

    //GeckoLib
    private boolean isFalling() {
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
            if (this.isFalling()) {
                state.getController().setAnimation(RawAnimation.begin().then("ATTACK_FALL", Animation.LoopType.PLAY_ONCE));
            } else state.getController().setAnimation(RawAnimation.begin().then("ATTACK", Animation.LoopType.PLAY_ONCE));
            this.swinging = false;
        }

        return PlayState.CONTINUE;
    }


    private PlayState predicate(AnimationState<GeoAnimatable> state) {

        if (isFalling() && !this.isNoAi()) {
            state.getController().setAnimation(RawAnimation.begin().then("FALL", Animation.LoopType.LOOP));
            return PlayState.CONTINUE;
        }

        else if (state.isMoving()) {
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

    /**
     * If the mantis has the name 'Gimantis' or 'Kamacuras,' it will
     * be given a special skin. If it is a baby orchid mantis, or has been
     * stunted via a Poisonous Potato, it will have the baby orchid mantis skin.
     * Otherwise, it will have the regular orchid or default skin.
     */
    public ResourceLocation getTexture() {

        if (this.shouldUseGimantisSkin()) {
            return new ResourceLocation("infested", "textures/entity/gimantis.png");
        }

        else if (this.shouldUseKamacurasSkin()) {
            return new ResourceLocation("infested", "textures/entity/kamacuras.png");
        }

        else if (this.shouldUseOrchidMantisSkin()) {
            if (this.isBaby() || this.isStunted()) {
                return new ResourceLocation("infested", "textures/entity/orchid_mantis_baby.png");
            } else return new ResourceLocation("infested", "textures/entity/orchid_mantis.png");
        } else

        return new ResourceLocation("infested", "textures/entity/mantis.png");
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ORCHID, false);
        this.entityData.define(STUNTED, false);
    }


    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("isOrchidMantis", this.entityData.get(ORCHID));
        compound.putBoolean("isStunted", this.entityData.get(STUNTED));
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.entityData.set(ORCHID, compound.getBoolean("isOrchidMantis"));
        this.entityData.set(STUNTED, compound.getBoolean("isStunted"));
    }


    public boolean shouldUseOrchidMantisSkin() {
        return this.entityData.get(ORCHID);
    }

    public boolean isStunted() {
        return this.entityData.get(STUNTED);
    }

    public void setOrchidMantisSkin(boolean b) {
        this.entityData.set(ORCHID, b);
    }

    public void setStunted(boolean b) {
        this.entityData.set(STUNTED, b);
    }

    public boolean shouldUseGimantisSkin() {
        return this.getEntityName().equals("Gimantis");
    }

    public boolean shouldUseKamacurasSkin() {
        return this.getEntityName().equals("Kamacuras");
    }
}
