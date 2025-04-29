package net.trashelemental.infested.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SilverfishMinionEntity;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;
import net.trashelemental.infested.item.ModItems;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModDispenserExtensions {

    @SubscribeEvent
    public static void init(FMLCommonSetupEvent event) {

        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.SILVERFISH_EGGS.get(), new OptionalDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack stack) {

                Level world = blockSource.getLevel();
                Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos dispenserPos = blockSource.getPos();

                BlockPos spawnPos = dispenserPos.relative(direction);

                if (world instanceof ServerLevel serverWorld) {
                    SilverfishMinionEntity silverfish = ModEntities.SILVERFISH_MINION.get().create(serverWorld);
                    if (silverfish != null) {
                        silverfish.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, direction.toYRot(), 0.0F);
                        serverWorld.addFreshEntity(silverfish);
                    }
                }

                ItemStack itemstack = stack.copy();
                itemstack.shrink(1);

                return itemstack;
            }
        }));


        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.BEE_EGGS.get(), new OptionalDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack stack) {

                Level world = blockSource.getLevel();
                Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos dispenserPos = blockSource.getPos();

                BlockPos spawnPos = dispenserPos.relative(direction);

                if (world instanceof ServerLevel serverWorld) {
                    BeeMinionEntity bee = ModEntities.BEE_MINION.get().create(serverWorld);
                    if (bee != null) {
                        bee.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, direction.toYRot(), 0.0F);
                        serverWorld.addFreshEntity(bee);
                    }
                }

                ItemStack itemstack = stack.copy();
                itemstack.shrink(1);

                return itemstack;
            }
        }));


        event.enqueueWork(() -> DispenserBlock.registerBehavior(ModItems.SPIDER_EGG.get(), new OptionalDispenseItemBehavior() {
            @Override
            public ItemStack execute(BlockSource blockSource, ItemStack stack) {

                Level world = blockSource.getLevel();
                Direction direction = blockSource.getBlockState().getValue(DispenserBlock.FACING);
                BlockPos dispenserPos = blockSource.getPos();

                BlockPos spawnPos = dispenserPos.relative(direction);

                if (world instanceof ServerLevel serverWorld) {
                    SpiderMinionEntity spider = ModEntities.SPIDER_MINION.get().create(serverWorld);
                    if (spider != null) {
                        spider.moveTo(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, direction.toYRot(), 0.0F);
                        serverWorld.addFreshEntity(spider);
                    }
                }

                ItemStack itemstack = stack.copy();
                itemstack.shrink(1);

                return itemstack;
            }
        }));
    }

}
