package net.trashelemental.infested.entity.custom.spider_alchemy;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class AlchemySpiderEntity extends MinionEntity {

    private static final EntityDataAccessor<String> ELEMENT = SynchedEntityData.defineId(AlchemySpiderEntity.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Boolean> BONUS = SynchedEntityData.defineId(AlchemySpiderEntity.class, EntityDataSerializers.BOOLEAN);

    public ResourceLocation getTexture() {
        return new ResourceLocation(infested.MOD_ID, "textures/entity/spider_" + getElement().name().toLowerCase() + ".png");
    }

    public ResourceLocation getEmissiveTexture() {
        return new ResourceLocation(infested.MOD_ID, "textures/entity/spider_" + getElement().name().toLowerCase() + "_glowmask.png");
    }

    public boolean hasBlasted = false;

    public AlchemySpiderEntity(EntityType<? extends TamableAnimal> entityType, Level level) {
        super(entityType, level, ParticleTypes.POOF, SoundEvents.SPIDER_AMBIENT);
    }

    /**
     * Element Types, getting and setting.
     */
    public enum ElementType {
        FIRE, ICE, LIGHTNING, BLAST, PSYCHIC;
    }

    public void setElement(ElementType type) {
        this.entityData.set(ELEMENT, type.name());
    }

    public ElementType getElement() {
        return ElementType.valueOf(this.entityData.get(ELEMENT));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_FLAGS_ID, (byte) 0);
        this.entityData.define(ELEMENT, ElementType.FIRE.name());
        this.entityData.define(BONUS, false);
    }

    /**
     * Element blasts.
     */
    public ParticleOptions blastParticles() {
        if (this.getElement().equals(ElementType.ICE)) {
            return ParticleTypes.SNOWFLAKE;
        }
        if (this.getElement().equals(ElementType.LIGHTNING)) {
            return ParticleTypes.FIREWORK;
        }
        if (this.getElement().equals(ElementType.PSYCHIC)) {
            return ParticleTypes.ANGRY_VILLAGER;
        }
        if (this.getElement().equals(ElementType.BLAST)) {
            return ParticleTypes.EXPLOSION;
        }
        return ParticleTypes.FLAME;
    }

    public ParticleOptions getBlastParticles() {
        return blastParticles();
    }

    public int blastDamage() {
        RandomSource random = this.level().getRandom();
        if (this.getElement() == ElementType.BLAST) {
            return 10 + random.nextInt(5);
        }
        return 5 + random.nextInt(4);
    }

    public void doBlast() {
        AABB area = this.getBoundingBox().inflate(4.0);
        List<LivingEntity> entities = this.level().getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity target : entities) {
            if (shouldBlastEffectEntity(target)) {
                target.hurt(this.damageSources().mobAttack(this), this.blastDamage());
                doElementEffects(target);

                if (this.getElement() == ElementType.BLAST) {
                    blastEffect(target);
                }
            }
        }
        if (!this.level().isClientSide && this.level() instanceof ServerLevel serverLevel) {
            ParticleOptions type = this.getBlastParticles();
            ParticleMethods.ParticlesBurst(serverLevel, type, this.getX(), this.getY(), this.getZ(), 20, 2);
            ParticleMethods.ParticlesInBox(serverLevel, area, type, 40);
            this.playSound(SoundEvents.GENERIC_EXPLODE, 0.7f, 6.0f);
        }
    }

    public boolean shouldBlastEffectEntity(LivingEntity entity) {

        if (entity == this) return false;
        if (entity instanceof ArmorStand) return false;
        if (entity instanceof Player player && player.isCreative()) return false;

        LivingEntity owner = this.getOwner();
        if (owner == null) return true;
        if (entity == owner) return false;
        if (entity instanceof TamableAnimal tamable && tamable.isOwnedBy(owner)) return false;

        return true;
    }


    /**
     * Elemental Effects
     */
    public void doElementEffects(LivingEntity entity) {
        if (entity.level().isClientSide) {
            return;
        }

        if (this.getElement() == ElementType.BLAST) {
            return;
        }
        if (this.getElement() == ElementType.PSYCHIC) {
            psychicEffect(entity);
        }
        if (this.getElement() == ElementType.ICE) {
            iceEffect(entity);
        }
        if (this.getElement() == ElementType.LIGHTNING) {
            lightningEffect(entity);
        }
        if (this.getElement() == ElementType.FIRE) {
           fireEffect(entity);
        }
    }

    public void blastEffect(LivingEntity entity) {
        Vec3 direction = entity.position().subtract(this.position()).normalize();
        double strength = 0.5D;

        entity.setDeltaMovement(entity.getDeltaMovement().add(
                direction.x * strength,
                0.5D,
                direction.z * strength
        ));
    }
    public void psychicEffect(LivingEntity entity) {
        if (entity instanceof Mob mob) {
            mob.setTarget(null);
            List<LivingEntity> potentialTargets = mob.level().getEntitiesOfClass(
                    LivingEntity.class,
                    mob.getBoundingBox().inflate(8),
                    e -> e != mob && this.shouldBlastEffectEntity(e)
            );
            potentialTargets.stream()
                    .min(Comparator.comparingDouble(e -> e.distanceToSqr(mob))).ifPresent(mob::setTarget);
        }
    }
    public void iceEffect(LivingEntity entity) {
        entity.setTicksFrozen(600);
    }
    public void lightningEffect(LivingEntity entity) {
        entity.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4, false, true));
    }
    public void fireEffect(LivingEntity entity) {
        entity.setSecondsOnFire(5);
    }


    /**
     * Elemental spiders are immune to damage types that match their elements.
     */
    @Override
    public boolean hurt(DamageSource source, float amount) {

        if (this.getElement() == ElementType.BLAST) {
            if (source.is(DamageTypes.EXPLOSION) || source.is(DamageTypes.PLAYER_EXPLOSION)) {
                return false;
            }
        }
        if (this.getElement() == ElementType.ICE) {
            if (source.is(DamageTypes.FREEZE)) {
                return false;
            }
        }
        if (this.getElement() == ElementType.LIGHTNING) {
            if (source.is(DamageTypes.LIGHTNING_BOLT)) {
                return false;
            }
        }
        if (this.getElement() == ElementType.FIRE) {
            if (source.is(DamageTypes.IN_FIRE) || source.is(DamageTypes.ON_FIRE) || source.is(DamageTypes.HOT_FLOOR) || source.is(DamageTypes.LAVA)) {
                return false;
            }
        }

        //Takes minimal fall damage.
        if (source.is(DamageTypes.FALL)) {
            amount = Math.min(amount, 2.0F);
        }
        return super.hurt(source, amount);
    }

    @Override
    public boolean fireImmune() {
        return this.getElement() == ElementType.FIRE;
    }

    @Override
    public boolean canFreeze() {
        return !(this.getElement() == ElementType.ICE);
    }

    /**
     * The spider set synergizes with Alchemy Spiders, adding a chance for a 'bonus' spider to be
     * spawned for each piece that is worn. Bonus spiders won't drop an egg item when interacted with.
     */
    public void setIsBonus(Boolean isBonus) {
        this.entityData.set(BONUS, isBonus);
    }

    public boolean getIsBonus() {
        return this.entityData.get(BONUS);
    }

    /**
     * When the owner of an alchemy spider interacts with one, it will be despawned
     * and returned to the player as its spawn item.
     */
    public static String getSpiderType(AlchemySpiderEntity entity) {
        if (entity instanceof CloakedSpiderEntity) {
            return "cloaked";
        }
        if (entity instanceof DamagedSpiderEntity) {
            return "damaged";
        }
        return "jumping";
    }

    public Item getReturnItem() {
        String spiderType = getSpiderType(this);
        String element = this.getElement().name().toLowerCase();
        ResourceLocation id = new ResourceLocation(infested.MOD_ID, spiderType + "_" + element + "_spider_egg");
        return ForgeRegistries.ITEMS.getValue(id);
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if (!level().isClientSide && this.isOwnedBy(player)) {

            Item eggItem = getReturnItem();

            if (eggItem != null && !this.getIsBonus()) {
                ItemStack eggStack = new ItemStack(eggItem);
                if (!player.isCreative()) {
                    if (!player.getInventory().add(eggStack)) {
                        player.drop(eggStack, false);
                    }
                }
            } else if (eggItem == null && !this.getIsBonus()) {
                player.displayClientMessage(Component.literal("No matching Alchemy Spider Egg was found!"), true);
            }

            this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.5F, 1.0F);
            this.discard();
            return InteractionResult.SUCCESS;
        }

        return super.mobInteract(player, hand);
    }

    //Basic Spider behaviors below

    //Creature Type
    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }

    //Sound Events
    @Override
    public SoundEvent getAmbientSound() {
        return SoundEvents.SPIDER_AMBIENT;
    }

    @Override
    public void playStepSound(BlockPos pos, BlockState blockIn) {
        this.playSound(Objects.requireNonNull(SoundEvents.SPIDER_STEP), 0.15f, 1);
    }

    @Override
    public SoundEvent getHurtSound(DamageSource ds) {
        return SoundEvents.SPIDER_HURT;
    }

    @Override
    public SoundEvent getDeathSound() {
        return SoundEvents.SPIDER_DEATH;
    }

    //Spider Climbing Behavior
    private static final EntityDataAccessor<Byte> DATA_FLAGS_ID = SynchedEntityData.defineId(AlchemySpiderEntity.class, EntityDataSerializers.BYTE);

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
    public boolean canBeAffected(MobEffectInstance effect) {
        if (effect.getEffect() == MobEffects.POISON) {
            MobEffectEvent.Applicable event = new MobEffectEvent.Applicable(this, effect);
            MinecraftForge.EVENT_BUS.post(event);
            return event.getResult() == Event.Result.ALLOW;
        } else {
            return super.canBeAffected(effect);
        }
    }

    //Cobweb immunity
    @Override
    public void makeStuckInBlock(BlockState pState, Vec3 pMotionMultiplier) {
        if (!pState.is(Blocks.COBWEB)) {
            super.makeStuckInBlock(pState, pMotionMultiplier);
        }
    }
}
