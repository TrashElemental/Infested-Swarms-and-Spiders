package net.trashelemental.infested.entity.ai;

import net.minecraft.core.BlockPos;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;
import net.trashelemental.infested.entity.custom.MantisEntity;
import net.trashelemental.infested.entity.custom.TamedSpiderEntity;

@Mod.EventBusSubscriber
public class TryToEscapeSuffocation {

    @SubscribeEvent
    public static void TryToEscapeSuffocation(LivingHurtEvent event) {
        Entity entity = event.getEntity();
        DamageSource damage = event.getSource();

        if (!(damage.is(DamageTypes.IN_WALL))) {
            return;
        }

        if (isModEntity(entity) && entity instanceof TamableAnimal tamableEntity && isTamedEntity(tamableEntity)) {
            BlockPos targetPos = findSafePosition(entity);

            if (targetPos != null) {
                entity.teleportTo(targetPos.getX(), targetPos.getY(), targetPos.getZ());
            }
        }
    }

    private static BlockPos findSafePosition(Entity entity) {
        BlockPos currentPos = entity.blockPosition();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = 1; dy <= 3; dy++) {
                for (int dz = -2; dz <= 2; dz++) {
                    BlockPos targetPos = currentPos.offset(dx, dy, dz);
                    if (isSafePosition(entity.level(), targetPos)) {
                        return targetPos;
                    }
                }
            }
        }

        return null;
    }

    private static boolean isSafePosition(Level level, BlockPos pos) {
        return level.getBlockState(pos).isAir() && level.getBlockState(pos.above()).isAir();
    }

    public static boolean isModEntity(Entity entity) {
        return entity instanceof MantisEntity ||
                entity instanceof TamedSpiderEntity ||
                entity instanceof BrilliantBeetleEntity;
    }

    public static boolean isTamedEntity(TamableAnimal entity) {
        return entity.isTame();
    }
}
