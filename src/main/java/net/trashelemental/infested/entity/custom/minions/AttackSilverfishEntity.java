package net.trashelemental.infested.entity.custom.minions;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class AttackSilverfishEntity extends Animal {


    public AttackSilverfishEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }


    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.targetSelector.addGoal(0, new NearestAttackableTargetGoal<>(this, Monster.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, false, false));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, AttackSpiderEntity.class, false, false));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 1));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FloatGoal(this));
        this.goalSelector.addGoal(6, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.3)
                .add(Attributes.ATTACK_DAMAGE, 1)
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
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.ambient"));
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.step"))), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.hurt"));
    }

    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.silverfish.death"));
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }


    //Custom Behaviors

    //No EXP Farming for you lol
    @Override
    public int getExperienceReward() {
        return 0;
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





}
