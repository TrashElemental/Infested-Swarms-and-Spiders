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
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
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
import net.trashelemental.infested.entity.custom.projectile.SpiderEggSacEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

import java.util.function.Supplier;

public class EggSacItem extends Item {

    private final Supplier<EntityType<?>> entityTypeSupplier;
    private final int numberOfEntities;

    public EggSacItem(Properties pProperties, Supplier<EntityType<?>> entityTypeSupplier, int numberOfEntities) {
        super(pProperties.stacksTo(16));
        this.entityTypeSupplier = entityTypeSupplier;
        this.numberOfEntities = numberOfEntities;
    }

    public EntityType<?> getEntityType() {
        return entityTypeSupplier.get();
    }
    public int getNumberOfEntities() { return numberOfEntities; }

    /**
     * If the player is crouching, place the entities directly. Otherwise, throw the egg as a projectile that will spawn
     * the entities where it lands.
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
                        for (int i = 0; i < getNumberOfEntities(); i++) {
                            spawnMinions(level, spawnPos, player);
                        }

                        spawnVFX(level, spawnPos);

                        if (!player.getAbilities().instabuild) {
                            itemstack.shrink(1);
                            player.getCooldowns().addCooldown(itemstack.getItem(), 20);
                        }

                        return InteractionResultHolder.success(itemstack);
                    }
                }
            }
        }

        else {
            level.playSound(null, player.getX(), player.getY(), player.getZ(),
                    SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F,
                    0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));

            if (!level.isClientSide) {
                SpiderEggSacEntity eggSac = new SpiderEggSacEntity(level, player);
                eggSac.setItem(itemstack);
                eggSac.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(eggSac);
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
                player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private void spawnMinions(Level level, BlockPos pos, Player player) {
        if (!(level instanceof ServerLevel serverLevel)) return;
        Entity entity = getEntityType().create(serverLevel);
        if (!(entity instanceof MinionEntity minion)) return;

        SummonMethods.summonMinion(level, pos, minion, 300, false, player);
    }

    private void spawnVFX(Level level, BlockPos pos) {
        level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.PLAYERS, 0.3F, 1.4F);
    }
}
