package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
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
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpiderMinionEntity extends TamableAnimal {



    public SpiderMinionEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.isTame = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this));
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, (float) 0.5));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false));
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, (float) 6));
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
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.ambient"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.step"))), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.death"));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    //Taming
    private boolean isTame;

    @Override
    public boolean isTame() {
        return this.isTame;
    }

    @Override
    public void setTame(boolean pTamed) {
        this.isTame = pTamed;
    }




    //Custom Behaviors

    //Spider Climbing Behavior (Also some in defineSyncedData and tick.)
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

    //No EXP Farming for you lol
    @Override
    public int getExperienceReward() {
        return 0;
    }

    //Owners can't Friendly Fire their Minions
    //Can take no more than one heart's worth of fall damage.
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {
        if (pSource.getEntity() instanceof LivingEntity attacker) {
            if (this.getOwnerUUID() != null && this.getOwnerUUID().equals(attacker.getUUID())) {
                return false;
            }
        }

        if (pSource.is(DamageTypes.FALL)) {
            pAmount = Math.min(pAmount, 2.0F);
        }

        return super.hurt(pSource, pAmount);
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
                    SoundEvents.SPIDER_AMBIENT, this.getSoundSource(), 1.0F, 1.0F);
            this.discard();
        }

        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
    }

}
