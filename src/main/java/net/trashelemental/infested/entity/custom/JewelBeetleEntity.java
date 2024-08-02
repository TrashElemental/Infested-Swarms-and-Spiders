package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class JewelBeetleEntity extends Animal {


   public JewelBeetleEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;


    private void setupAnimationStates() {
        if (!idleAnimationState.isStarted()) {
            this.idleAnimationState.start(this.tickCount);
        }
    }

    @Override
    protected void updateWalkAnimation(float pPartialTick) {
        float f;
        if(this.getPose() == Pose.STANDING) {
            f = Math.min(pPartialTick * 6f, 1f);
        } else {
            f = 0f;
        }
        this.walkAnimation.update(f, 0.2f);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals(); {

            this.goalSelector.addGoal(0, new AvoidEntityGoal<>(this, Player.class, (float) 10, 1, 1.2));
            this.goalSelector.addGoal(1, new PanicGoal(this, 1));
            this.goalSelector.addGoal(2, new FloatGoal(this));
            this.goalSelector.addGoal(3, new ClimbOnTopOfPowderSnowGoal(this, this.level()));
            this.goalSelector.addGoal(4, new RandomStrollGoal(this, 1));
            this.goalSelector.addGoal(5, new LookAtPlayerGoal(this, Player.class, (float) 6));
            this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));

        }
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
    public SoundEvent getAmbientSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.tropical_fish.ambient"));
    }
    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.spider.step"))), 0.15f, 1);
    }
    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.tropical_fish.hurt"));
    }
    @Override
    public SoundEvent getDeathSound() {
        return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("entity.tropical_fish.death"));
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

        setAge(200);

        for (int i = 0; i < 10; i++) {
            double offsetX = (this.random.nextDouble() - 0.5);
            double offsetY = (this.random.nextDouble() - 0.5);
            double offsetZ = (this.random.nextDouble() - 0.5);
            this.level().addParticle(
                    ParticleTypes.FIREWORK,
                    this.getX() + offsetX,
                    this.getY() + offsetY,
                    this.getZ() + offsetZ,
                    0.0D, 0.0D, 0.0D);
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.AMETHYST_BLOCK_CHIME, this.getSoundSource(), 1.0F, 3.0F);

        }
    }

    //De-spawns when its time limit runs out and disappearance effects
    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide) {
          setupAnimationStates();
       }

        if (this.age == 0) {
            if (this.level() instanceof ServerLevel serverLevel) {
               serverLevel.sendParticles(ParticleTypes.POOF, this.getX(), this.getY(), this.getZ(), 5, 0, 0, 0, 0);
            }
            this.level().playSound(null, this.getX(), this.getY(), this.getZ(),
                    SoundEvents.BEEHIVE_ENTER, this.getSoundSource(), 1.0F, 3.0F);
            this.discard();
       }
    }

    //Drops more experience than the average mob
    @Override
    public int getExperienceReward() {
        return 10;
    }

}
