package net.trashelemental.infested.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.trashelemental.infested.entity.ModEntities;

public class SpiderTrapBlock extends Block {
    public SpiderTrapBlock(Properties properties) {
        super(properties);
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, Level world, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
        super.neighborChanged(state, world, pos, block, fromPos, isMoving);

        if (world.getBestNeighborSignal(pos) > 0) {

            world.levelEvent(LevelEvent.PARTICLES_DESTROY_BLOCK, pos, Block.getId(world.getBlockState(pos)));
            world.destroyBlock(pos, false);

            if (world instanceof ServerLevel serverLevel) {
                RandomSource random = serverLevel.random;
                int count = Mth.nextInt(random, 3, 5);

                for (int i = 0; i < count; i++) {
                    Entity entity = ModEntities.ATTACK_SPIDER.get().create(serverLevel);
                    if (entity != null) {
                        entity.moveTo(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                        serverLevel.addFreshEntity(entity);
                    }
                }
            }
        }
    }
}
