package net.trashelemental.infested.item.custom.throwing_items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.entity.custom.projectile.CobwebBombEntity;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Supplier;

public class CobwebBombItem extends Item {

    public CobwebBombItem(Properties pProperties) {
        super(pProperties.stacksTo(16));
    }

    /**
     * If the player is crouching, place the cobwebs directly. Otherwise, throw the bomb as a projectile that will place
     * the cobwebs where it lands.
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

                    if (tryPlaceCobwebTraps(level, spawnPos)) {
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
                CobwebBombEntity bomb = new CobwebBombEntity(level, player);
                bomb.setItem(itemstack);
                bomb.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
                level.addFreshEntity(bomb);
            }

            player.awardStat(Stats.ITEM_USED.get(this));

            if (!player.getAbilities().instabuild) {
                itemstack.shrink(1);
                player.getCooldowns().addCooldown(itemstack.getItem(), 20);
            }
        }
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    private boolean tryPlaceCobwebTraps(Level level, BlockPos centerPos) {
        Block block = ModBlocks.COBWEB_TRAP.get();
        boolean place = false;

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos targetPos = centerPos.offset(x, y, z);

                    if (level.isEmptyBlock(targetPos)) {
                        BlockState trapState = block.defaultBlockState();
                        if (trapState.canSurvive(level, targetPos)) {
                            level.setBlock(targetPos, trapState, 3);
                            place = true;
                        }
                    }
                }
            }
        }
        return place;
    }

    private void spawnVFX(Level level, BlockPos pos) {
        ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.POOF,
                pos.getX(), pos.getY(), pos.getZ(), 5, 0.5);

        level.playSound(null, pos, SoundEvents.ROOTS_BREAK, SoundSource.PLAYERS, 0.5F, 1.0F);
    }
}
