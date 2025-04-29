package net.trashelemental.infested.util.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.Config;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;
import net.trashelemental.infested.entity.custom.MantisEntity;
import net.trashelemental.infested.entity.custom.TamedSpiderEntity;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;
import net.trashelemental.infested.util.ModTags;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class UtilEvents {

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract event) {
        if (event.getHand() != event.getEntity().getUsedItemHand()) return;

        Player player = event.getEntity();
        Entity target = event.getTarget();
        ItemStack itemStack = player.getMainHandItem();

        if (!(target instanceof LivingEntity livingTarget)) return;

        if (itemStack.is(ModTags.Items.ARTHROPOD_FOOD)) {
            if (isTamedArthropod(livingTarget, player) && needsHealing(livingTarget)) {
                if (!event.getLevel().isClientSide) {
                    healWithParticlesAndSound(livingTarget, 10.0f, event.getLevel(), player, itemStack);
                }
                event.setCancellationResult(InteractionResult.SUCCESS);
                event.setCanceled(true);
                return;
            }
        }

        if (Config.FEED_MOD_PETS_MEAT.get() && !(itemStack.is(ModItems.RAW_GRUB.get()))) {
            if (isModPet(livingTarget, player) && needsHealing(livingTarget)) {
                FoodProperties food = itemStack.getFoodProperties(livingTarget);
                if (food != null && food.isMeat()) {
                    if (!event.getLevel().isClientSide) {
                        healWithParticlesAndSound(livingTarget, 5.0f, event.getLevel(), player, itemStack);
                    }
                    event.setCancellationResult(InteractionResult.SUCCESS);
                    event.setCanceled(true);
                }
            }
        }
    }

    private static boolean isTamedArthropod(LivingEntity entity, Player player) {
        return (entity.getMobType() == MobType.ARTHROPOD || isCommonArthropodName(entity.getName().getString()))
                && entity instanceof TamableAnimal tamable && tamable.isOwnedBy(player);
    }

    private static boolean isModPet(LivingEntity entity, Player player) {
        return (entity instanceof TamedSpiderEntity
                || entity instanceof MantisEntity
                || entity instanceof BrilliantBeetleEntity)
                && entity instanceof TamableAnimal tamable && tamable.isOwnedBy(player);
    }

    private static boolean needsHealing(LivingEntity entity) {
        return entity.getHealth() < entity.getMaxHealth();
    }

    private static void healWithParticlesAndSound(LivingEntity entity, float amount, Level level, Player player, ItemStack stack) {
        entity.heal(amount);
        ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.HAPPY_VILLAGER,
                entity.getX(), entity.getY() + 1, entity.getZ(), 10, 1);
        playEatSound(level, entity);
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
    }

    public static void playEatSound(Level level, LivingEntity entity) {
        BlockPos pos = entity.blockPosition();

        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F);
        infested.queueServerWork(5, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));
        infested.queueServerWork(10, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 1.0F, 1.0F));

    }

    //some common bug names because y'all don't know how to tag your gosh darn arthropods
    private static boolean isCommonArthropodName(String name) {
        String lowerCaseName = name.toLowerCase();
        return lowerCaseName.contains("spider") ||
                lowerCaseName.contains("beetle") ||
                lowerCaseName.contains("bee") ||
                lowerCaseName.contains("grasshopper") ||
                lowerCaseName.contains("dragonfly") ||
                lowerCaseName.contains("bug") ||
                lowerCaseName.contains("insect") ||
                lowerCaseName.contains("swarmer");
    }

}
