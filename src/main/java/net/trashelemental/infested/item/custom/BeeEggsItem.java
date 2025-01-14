package net.trashelemental.infested.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;

import java.util.Random;

public class BeeEggsItem extends Item {
    public BeeEggsItem(Properties pProperties) {
        super(pProperties);
    }

    private static final Random RANDOM = new Random();

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        if (!(level instanceof ServerLevel)) {
            return InteractionResult.FAIL;
        }

        Player player = context.getPlayer();
        if (player == null) {
            return InteractionResult.FAIL;
        }

        ItemStack itemstack = context.getItemInHand();
        BlockPos blockPos = context.getClickedPos();
        Direction direction = context.getClickedFace();
        BlockState blockState = level.getBlockState(blockPos);

        BlockPos spawnPos;
        if (blockState.getCollisionShape(level, blockPos).isEmpty()) {
            spawnPos = blockPos;
        } else {
            spawnPos = blockPos.relative(direction);
        }

        if (!level.getBlockState(spawnPos).isAir()) {
            return InteractionResult.FAIL;
        }

        Random random = new Random();

        //If used on a bee nest or beehive, it will spawn a baby bee
        //a babee if you will
        if (blockState.is(Blocks.BEE_NEST) || blockState.is(Blocks.BEEHIVE)) {
            Bee bee = EntityType.BEE.create(level);
            if (bee != null) {
                bee.moveTo(spawnPos.getX() + random.nextDouble(), spawnPos.getY(), spawnPos.getZ() + random.nextDouble(), random.nextFloat() * 360F, 0);
                bee.setAge(-24000);
                level.addFreshEntity(bee);

                level.playSound(null, blockPos, SoundEvents.BEEHIVE_EXIT, SoundSource.BLOCKS, 1.0F, 1.0F);

                if (!player.getAbilities().instabuild) {
                    itemstack.shrink(1);
                }
            }
            return InteractionResult.sidedSuccess(level.isClientSide());
        }

        //Normal function
        for (int i = 0; i < 1 + random.nextInt(2); i++) {
            BeeMinionEntity bee = new BeeMinionEntity(ModEntities.BEE_MINION.get(), level);
            bee.setAge(300);
            bee.setTame(true);
            bee.moveTo(spawnPos.getX() + random.nextDouble(), spawnPos.getY(), spawnPos.getZ() + random.nextDouble(), random.nextFloat() * 360F, 0);
            level.addFreshEntity(bee);
            bee.setOwnerUUID(player.getUUID());
        }


        if (!player.isCreative()) {
            itemstack.shrink(1);
        }

        level.gameEvent(player, GameEvent.ENTITY_PLACE, blockPos);

        level.playSound(null, blockPos,
                SoundEvents.TURTLE_EGG_HATCH, SoundSource.PLAYERS, 1.0F, 3.0F);

        return InteractionResult.CONSUME;
    }

}
