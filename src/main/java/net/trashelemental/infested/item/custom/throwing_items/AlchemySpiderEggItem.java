package net.trashelemental.infested.item.custom.throwing_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.trashelemental.infested.entity.custom.projectile.AlchemySpiderEggEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

import java.util.function.Supplier;

public class AlchemySpiderEggItem extends Item {

    private final Supplier<EntityType<?>> entityTypeSupplier;
    private final AlchemySpiderEntity.ElementType elementType;

    public AlchemySpiderEggItem(Properties pProperties, Supplier<EntityType<?>> entityTypeSupplier, AlchemySpiderEntity.ElementType elementType) {
        super(pProperties.stacksTo(16));
        this.entityTypeSupplier = entityTypeSupplier;
        this.elementType = elementType;
    }

    public EntityType<?> getEntityType() {
        return entityTypeSupplier.get();
    }

    /**
     * If the player is crouching, place the spider directly. Otherwise, throw the egg as a projectile that will spawn
     * the spider where it lands.
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        if (player.isCrouching()) {
            if (!level.isClientSide) {
                BlockHitResult hitResult = getPlayerPOVHitResult(level, player, ClipContext.Fluid.NONE);

                if (hitResult.getType() == HitResult.Type.BLOCK) {
                    BlockPos blockPos = hitResult.getBlockPos();
                    Direction face = hitResult.getDirection();
                    BlockState blockState = level.getBlockState(blockPos);

                    BlockPos spawnPos;
                    if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
                        spawnPos = blockPos;
                    } else {
                        spawnPos = blockPos.relative(face);
                    }

                    if (level.getBlockState(spawnPos).isAir()) {
                        spawnEntity(level, player, spawnPos, false);

                        int bonusChance = getBonusSpawnChance(player);
                        if (level.getRandom().nextInt(100) < bonusChance) {
                            spawnEntity(level, player, spawnPos, true);
                        }

                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                            player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                        }
                    }
                }
            }
        }

        else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!level.isClientSide) {
                AlchemySpiderEggEntity egg = new AlchemySpiderEggEntity(level, player, getEntityType(), elementType);
                egg.setItem(itemstack);
                egg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(egg);
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
                player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void spawnEntity(Level level, Player player, BlockPos pos, boolean isBonus) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Entity entity = getEntityType().create(serverLevel);
        if (!(entity instanceof AlchemySpiderEntity spider)) return;

        spider.setElement(elementType);
        spider.setIsBonus(isBonus);

        SummonMethods.summonMinion(level, pos.below(), spider, 0, true, player);
        spawnVFX(level, player, spider);
    }

    private void spawnVFX(Level level, Player player, LivingEntity entity) {
        ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.POOF,
                entity.getX(), entity.getY(), entity.getZ(), 5, 0.5);

        level.gameEvent(player, GameEvent.ENTITY_PLACE, entity.getOnPos());
        level.playSound(null, entity.getOnPos(),
                SoundEvents.SNIFFER_EGG_HATCH, SoundSource.PLAYERS, 0.3F, 1.0F);
    }

    public int getBonusSpawnChance(Player player) {
        int bonusSpawnChance = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SPIDER_CHESTPLATE.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SPIDER_LEGGINGS.get()) bonusSpawnChance++;
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SPIDER_BOOTS.get()) bonusSpawnChance++;

        return (bonusSpawnChance * 20);
    }
}
