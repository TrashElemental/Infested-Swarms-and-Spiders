package net.trashelemental.infested.util.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.util.Random;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class GetBeeEggsFromBeehive {

    /**
    * When honey is harvested from a Beehive or Bee Nest, Bee Eggs have a chance to be dropped.
     */

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        BlockState blockState = event.getLevel().getBlockState(event.getPos());
        ItemStack tool = event.getItemStack();

        if (blockState.is(Blocks.BEEHIVE) || blockState.is(Blocks.BEE_NEST)) {
            int honeyLevel = blockState.getValue(BeehiveBlock.HONEY_LEVEL);

            if (tool.is(Items.SHEARS) && honeyLevel >= 5) {

                Random random = new Random();
                if (random.nextFloat() < 0.40f) {
                    ItemStack customItem = new ItemStack(ModItems.BEE_EGGS.get());
                    BlockPos blockPos = event.getPos();
                    Level level = event.getLevel();

                    if (!level.isClientSide) {
                        ItemEntity itemEntity = new ItemEntity(level, blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5, customItem);
                        level.addFreshEntity(itemEntity);
                    }
                }
            }
        }
    }
}
