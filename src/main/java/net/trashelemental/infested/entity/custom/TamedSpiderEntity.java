package net.trashelemental.infested.entity.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.OwnerHurtTargetGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class TamedSpiderEntity extends TamableAnimal {



    public TamedSpiderEntity(EntityType<? extends TamableAnimal> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
        this.isTame = false;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(1, new OwnerHurtByTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && follow(TamedSpiderEntity.this);
            }
        });
        this.targetSelector.addGoal(2, new OwnerHurtTargetGoal(this) {
            @Override
            public boolean canUse() {
                return super.canUse() && follow(TamedSpiderEntity.this);
            }
        });
        this.goalSelector.addGoal(3, new MeleeAttackGoal(this, 1.2, false) {
            @Override
            protected double getAttackReachSqr(LivingEntity entity) {
                return this.mob.getBbWidth() * this.mob.getBbWidth() + entity.getBbWidth();
            }
        });
        this.goalSelector.addGoal(4, new LeapAtTargetGoal(this, (float) 0.5));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1, (float) 10, (float) 2, false) {
            @Override
            public boolean canUse() {
                return super.canUse() && follow(TamedSpiderEntity.this);
            }
        });
        this.goalSelector.addGoal(6, new RandomStrollGoal(this, 1) {
            @Override
            public boolean canUse() {
                return super.canUse() && wander(TamedSpiderEntity.this);
            }
        });
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(8, new FloatGoal(this));
        this.goalSelector.addGoal(9, new LookAtPlayerGoal(this, Player.class, (float) 6));
        this.targetSelector.addGoal(10, new HurtByTargetGoal(this));
    }

    public static boolean follow(TamedSpiderEntity entity) {
        if (entity == null)
            return false;
        return entity.isFollowing();
    }

    public static boolean wander(TamedSpiderEntity entity) {
        if (entity == null)
            return false;
        return entity.isWandering();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createLivingAttributes()

                .add(Attributes.MAX_HEALTH, 15)
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
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(TamedSpiderEntity.class, EntityDataSerializers.BYTE);

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
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            this.setClimbing(this.horizontalCollision);
        }
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

    // Right click events
    @Override
    public InteractionResult mobInteract(Player pPlayer, InteractionHand pHand) {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);

        if (isPotionEffectItem(itemStack, pPlayer)) {
            return InteractionResult.SUCCESS;
        }

        if (isDyeItem (itemStack, pPlayer)) {
            return InteractionResult.SUCCESS;
        }

        if (this.isOwnedBy(pPlayer)) {
            cycleBehavior(pPlayer);
        }

        return super.mobInteract(pPlayer, pHand);
    }

    //Behavior
    private String BEHAVIOR = "FOLLOW";

    private void setBehaviorInPersistentData(String behavior) {
        CompoundTag tag = this.getPersistentData();
        tag.putString("Behavior", behavior);
    }

    public boolean isFollowing() {
        return this.BEHAVIOR.equals("FOLLOW");
    }

    public boolean isWandering() {
        return this.BEHAVIOR.equals("WANDER");
    }

    private void cycleBehavior(Player pPlayer) {
        switch (this.BEHAVIOR) {
            case "FOLLOW":
                this.BEHAVIOR = "WANDER";
                pPlayer.displayClientMessage(Component.literal("Spider will wander"), true);
                break;
            case "STAY":
                this.BEHAVIOR = "FOLLOW";
                pPlayer.displayClientMessage(Component.literal("Spider will follow"), true);
                break;
            case "WANDER":
                this.BEHAVIOR = "STAY";
                pPlayer.displayClientMessage(Component.literal("Spider will stay"), true);
                break;
        }
        this.setBehaviorInPersistentData(this.BEHAVIOR);
    }

    //Potion effect setting
    private String POTION_EFFECT = "POISON";

    private static final Map<String, MobEffect> POTION_EFFECTS = Map.of(
            "POISON", MobEffects.POISON,
            "WEAKNESS", MobEffects.WEAKNESS,
            "MOVEMENT_SLOW", MobEffects.MOVEMENT_SLOWDOWN,
            "CONFUSION", MobEffects.CONFUSION,
            "WITHER", MobEffects.WITHER,
            "LEVITATION", MobEffects.LEVITATION
    );

    private boolean isPotionEffectItem(ItemStack itemStack, Player pPlayer) {
        if (this.isOwnedBy(pPlayer)) {
        if (itemStack.is(Items.SPIDER_EYE)) {
            this.POTION_EFFECT ="POISON";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Poison"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        } else if (itemStack.is(Items.FERMENTED_SPIDER_EYE)) {
            this.POTION_EFFECT ="WEAKNESS";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Weakness"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        } else if (itemStack.is(Items.SUGAR)) {
            this.POTION_EFFECT ="MOVEMENT_SLOW";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Slowness"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        } else if (itemStack.is(Items.PUFFERFISH)) {
            this.POTION_EFFECT ="CONFUSION";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Nausea"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        } else if (itemStack.is(Items.WITHER_SKELETON_SKULL)) {
            this.POTION_EFFECT ="WITHER";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Wither"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        } else if (itemStack.is(Items.SHULKER_SHELL)) {
            this.POTION_EFFECT ="LEVITATION";
            pPlayer.displayClientMessage(Component.literal("Spider will inflict Levitation"), true);
            if (!pPlayer.isCreative()) {
                itemStack.shrink(1);
            }
            return true;
        }
        }
        return false;
    }

    //Potion Effect attack
    @Override
    public boolean doHurtTarget(Entity pEntity) {
        if (pEntity instanceof LivingEntity livingEntity) {
            MobEffect effect = POTION_EFFECTS.get(this.POTION_EFFECT);
            if (effect != null) {
                int duration = this.POTION_EFFECT.equals("LEVITATION") ? 100 : 200;
                livingEntity.addEffect(new MobEffectInstance(effect, duration, 0));
            }
        }
        return super.doHurtTarget(pEntity);
    }

    //Eye Colors
    private static final EntityDataAccessor<Integer> EYE_COLOR_DATA = SynchedEntityData.defineId(TamedSpiderEntity.class, EntityDataSerializers.INT);

    public DyeColor getEyeColor() {
        return DyeColor.byId(this.entityData.get(EYE_COLOR_DATA));
    }

    public void setEyeColor(DyeColor eyeColor) {
        this.entityData.set(EYE_COLOR_DATA, eyeColor.getId());
    }

    private boolean isDyeItem(ItemStack itemStack, Player pPlayer) {
        if (this.isOwnedBy(pPlayer)) {
            DyeColor dyeColor = DyeColor.getColor(itemStack);
            if (dyeColor != null) {
                this.setEyeColor(dyeColor);
                if (!pPlayer.getAbilities().instabuild) {
                    itemStack.shrink(1);
                }
                return true;
            }
        }
        return false;
    }


    //NBT Tags
    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putString("PotionEffect", this.POTION_EFFECT);
        compound.putString("Behavior", this.BEHAVIOR);
        compound.putByte("EyeColor", (byte) this.getEyeColor().getId());

    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if (compound.contains("PotionEffect")) {
            this.POTION_EFFECT = compound.getString("PotionEffect");
        }
        if (compound.contains("Behavior")) {
            this.BEHAVIOR = compound.getString("Behavior");
        }
        if (compound.contains("EyeColor", 99)) {
            this.setEyeColor(DyeColor.byId(compound.getByte("EyeColor")));
        }


    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
        this.entityData.define(EYE_COLOR_DATA, DyeColor.GREEN.getId());
    }

    //Takes no more than one heart worth of fall damage.
    @Override
    public boolean hurt(DamageSource pSource, float pAmount) {

        if (pSource.is(DamageTypes.FALL)) {
            pAmount = Math.min(pAmount, 2.0F);
        }

        return super.hurt(pSource, pAmount);
    }

}
