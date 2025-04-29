package net.trashelemental.infested.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.util.ModTags;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;

public class MinionSpawnLogic {

    public static void spawnMinion(Level level, Player player, BlockPos pos, int lifespan) {
        EntityType<? extends MinionEntity> minionType = determineMinionType(player);

        MinionEntity minionToSpawn = minionType.create(level);
        if (minionToSpawn != null) {
            SummonMethods.summonMinion(level, pos, minionToSpawn, lifespan, false, player);
        }
    }

    private static EntityType<? extends MinionEntity> determineMinionType(Player player) {
        ItemStack chestArmor = player.getItemBySlot(EquipmentSlot.CHEST);
        ItemStack weapon = player.getItemBySlot(EquipmentSlot.MAINHAND);

        if (chestArmor.is(ModTags.Items.SUMMONS_SPIDER) || weapon.is(ModTags.Items.SUMMONS_SPIDER)) {
            return ModEntities.SPIDER_MINION.get();
        } else if (chestArmor.is(ModTags.Items.SUMMONS_BEE) || weapon.is(ModTags.Items.SUMMONS_BEE)) {
            return ModEntities.BEE_MINION.get();
        }

        return ModEntities.SILVERFISH_MINION.get();
    }

    public static void infestedActivate(Level level, Player player, int minionCount, int lifespan) {
        if (level.isClientSide) return;

        BlockPos playerPos = player.getOnPos();
        for (int i = 0; i < minionCount; i++) {
            spawnMinion(level, player, playerPos, lifespan);
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.BEEHIVE_EXIT, SoundSource.NEUTRAL, 1.0F, 1.0F);
    }

    public static void parasitoidActivate(Level level, Player player, BlockPos pos, int minionCount, int lifespan) {
        if (level.isClientSide) return;

        for (int i = 0; i < minionCount; i++) {
            spawnMinion(level, player, pos, lifespan);
        }

        level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.SNIFFER_EGG_HATCH, SoundSource.NEUTRAL, 1.0F, 3.0F);
    }
}
