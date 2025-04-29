package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
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
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.TamableEntity;
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

public class BrilliantBeetleEntity extends TamableEntity implements GeoEntity {


    public BrilliantBeetleEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, Items.COCOA_BEANS, Items.COOKIE);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals(); {
            this.goalSelector.addGoal(0, new FollowOwnerGoal(this, 1, 10, 2, false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && follow(BrilliantBeetleEntity.this);
                }
            });
            this.goalSelector.addGoal(1, new FloatGoal(this));
            this.goalSelector.addGoal(2, new BreedGoal(this, 1));
            this.goalSelector.addGoal(3, new FollowParentGoal(this, 1));
            this.goalSelector.addGoal(4, new PanicGoal(this, 1.2));
            this.goalSelector.addGoal(5, new TemptGoal(this, 1, Ingredient.of(Items.COCOA_BEANS), false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(BrilliantBeetleEntity.this);
                }
            });
            this.goalSelector.addGoal(6, new TemptGoal(this, 1, Ingredient.of(Items.COOKIE), false) {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(BrilliantBeetleEntity.this);
                }
            });
            this.goalSelector.addGoal(7, new RandomStrollGoal(this, 1) {
                @Override
                public boolean canUse() {
                    return super.canUse() && wander(BrilliantBeetleEntity.this);
                }
            });
            this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(9, new RandomLookAroundGoal(this));
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 1)
                .add(Attributes.ARMOR, 1)
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

    //Spawning
    public static boolean canSpawn(EntityType<BrilliantBeetleEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return Animal.checkAnimalSpawnRules(entityType, level, spawnType, position, random);
    }

    //Spider Climbing Behavior
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(BrilliantBeetleEntity.class, EntityDataSerializers.BYTE);

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

    //Gliding Behavior
    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.onGround() && this.getDeltaMovement().y < 0.0) {

            Vec3 deltaMovement = this.getDeltaMovement();
            this.setDeltaMovement(deltaMovement.multiply(1.0, 0.6, 1.0));
        }
    }

    //Does not take fall damage
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    protected void checkFallDamage(double pY, boolean pOnGround, BlockState pState, BlockPos pPos) {
    }

    //On right click behavior
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
                    this.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
                    this.playSound(SoundEvents.GENERIC_EAT, 1.0F, 1.0F);
                }
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }

            if (!player.isCrouching()) {
                player.startRiding(this);
                return InteractionResult.sidedSuccess(this.level().isClientSide());
            }
        }
        return super.mobInteract(player, hand);
    }

    //Riding Behavior
    @Override
    public void travel(Vec3 dir) {
        Entity entity = this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);

        if (this.isVehicle() && entity instanceof LivingEntity passenger) {
            this.setYRot(entity.getYRot());
            this.yRotO = this.getYRot();
            this.setXRot(entity.getXRot() * 0.5F);
            this.setRot(this.getYRot(), this.getXRot());
            this.yBodyRot = entity.getYRot();
            this.yHeadRot = entity.getYRot();

            this.setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            float forward = passenger.zza * 0.5F; //Slows it down, idk why but its speed is doubled when you're riding it
            float strafe = passenger.xxa* 0.5F; //Ditto
            super.travel(new Vec3(strafe, 0, forward));

            return;
        }
        super.travel(dir);
    }

    //Adjusts where the player visibly sits while riding
    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + -0.4;
    }


    //GeckoLib
    private boolean isFalling() {
        return !this.onGround() && !this.isNoAi();
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllerRegistrar) {
        controllerRegistrar.add(new AnimationController<GeoAnimatable>(this, "controller", 4, this::predicate));
    }


    private PlayState predicate(software.bernie.geckolib.core.animation.AnimationState<GeoAnimatable> state) {

        if (isFalling()) {
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


}
