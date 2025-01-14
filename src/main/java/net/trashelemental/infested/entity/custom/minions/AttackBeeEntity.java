package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AttackBeeEntity extends Bee {


    public AttackBeeEntity(EntityType<? extends Bee> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Monster.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AttackSpiderEntity.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AttackSilverfishEntity.class, false, false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false));
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0)
                .add(Attributes.ATTACK_DAMAGE, 3)
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


    //Custom Behaviors

    //Does not take fall damage
    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source.is(DamageTypes.FALL))
            return false;
        return super.hurt(source, amount);
    }

    //Appearance effects, set timer for disappearance using age
    @Override
    public void onAddedToWorld() {
        super.onAddedToWorld();

        setAge(-300);

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

        if (!this.isBaby()) {
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

}
