package net.trashelemental.infested.entity.custom.projectile;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

public class CobwebBombEntity extends ThrowableItemProjectile {

    public CobwebBombEntity(EntityType<? extends ThrowableItemProjectile> pEntityType, Level pLevel) {
        super(pEntityType, pLevel);
    }

    public CobwebBombEntity(Level pLevel) {
        super(ModEntities.COBWEB_BOMB.get(), pLevel);
    }

    public CobwebBombEntity(Level level, LivingEntity shooter) {
        super(ModEntities.COBWEB_BOMB.get(), shooter, level);
    }

    @Override
    protected Item getDefaultItem() {
        return ModItems.COBWEB_BOMB.get();
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        super.onHitBlock(result);
        if (!level().isClientSide) {
            BlockPos pos = result.getBlockPos().relative(result.getDirection());
            cobwebTrapActivate(level(), pos);
            spawnVFX(pos);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!level().isClientSide) {
            BlockPos pos = result.getEntity().blockPosition();
            cobwebTrapActivate(level(), pos);
            spawnVFX(pos);
            this.discard();
        }
    }

    private void cobwebTrapActivate(Level level, BlockPos pos) {
        Block block = ModBlocks.COBWEB_TRAP.get();

        for (int x = -1; x <= 1; x++) {
            for (int z = -1; z <= 1; z++) {
                for (int y = -1; y <= 1; y++) {
                    BlockPos targetPos = pos.offset(x, y, z);

                    if (level.isEmptyBlock(targetPos)) {
                        BlockState trapState = block.defaultBlockState();
                        if (trapState.canSurvive(level, targetPos)) {
                            level.setBlock(targetPos, trapState, 3);
                        }
                    }
                }
            }
        }
    }

    private void spawnVFX(BlockPos pos) {
        ParticleMethods.ParticlesAroundServerSide(this.level(), ParticleTypes.POOF,
                pos.getX(), pos.getY(), pos.getZ(), 5, 0.5);

        this.level().gameEvent(this, GameEvent.ENTITY_PLACE, pos);
        this.level().playSound(null, pos,
                SoundEvents.ROOTS_BREAK, SoundSource.PLAYERS, 0.5F, 1.0F);
    }
}
