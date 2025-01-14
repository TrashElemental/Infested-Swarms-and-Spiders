package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
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

import java.util.Objects;

public class CrimsonBeetleEntity extends Animal {


   public CrimsonBeetleEntity(EntityType<? extends Animal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public final AnimationState idleAnimationState = new AnimationState();
   private int idleAnimationTimeout = 0;

    @Override
    public void tick() {
        super.tick();

        if(this.level().isClientSide) {
            setupAnimationStates();
        }
    }

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

    //Spawning
    public static boolean canSpawn(EntityType<CrimsonBeetleEntity> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos position, RandomSource random) {
        return !level.getBlockState(position.below()).is(Blocks.NETHER_WART_BLOCK)
                && !level.getBlockState(position.below()).is(Blocks.SHROOMLIGHT)
                && !level.getBlockState(position.below()).is(Blocks.WEEPING_VINES)
                && !level.getBlockState(position.below()).is(Blocks.WEEPING_VINES_PLANT)
                && !level.getBlockState(position.below()).is(Blocks.GLOWSTONE)
                && !level.getBlockState(position.below()).is(Blocks.AIR)
                && !level.getBlockState(position.below()).is(Blocks.CRIMSON_STEM);
    }




    //Custom Behavior

    //Breed Offspring are Grubs, not Crimson Beetles
    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return ModEntities.GRUB.get().create(serverLevel);
    }


}
