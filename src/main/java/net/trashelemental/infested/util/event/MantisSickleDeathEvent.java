package net.trashelemental.infested.util.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.visual.particle.ParticleMethods;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class MantisSickleDeathEvent {

    /**
     * When the player kills an enemy with a Mantis Sickle in either hand,
     * they will receive a small amount of healing, and restore some hunger,
     * if either are not full.
     */

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        Entity entity = event.getEntity();
        Level level = entity.level();
        Entity attacker = event.getSource().getEntity();

        if (!(level instanceof ServerLevel serverLevel)) return;
        if (!(attacker instanceof Player player)) return;

        Item mainhand = player.getItemInHand(InteractionHand.MAIN_HAND).getItem();
        Item offhand = player.getItemInHand(InteractionHand.OFF_HAND).getItem();

        if (mainhand == ModItems.MANTIS_SICKLE.get() || offhand == ModItems.MANTIS_SICKLE.get()) {

            int multiplier = 0;
            if (mainhand == ModItems.MANTIS_SICKLE.get()) multiplier++;
            if (offhand == ModItems.MANTIS_SICKLE.get()) multiplier++;
            int healing = 2 * multiplier;
            int hunger = 3 * multiplier;
            float saturation = 0.3f * multiplier;
            boolean shouldPlayEffects = false;

            if (player.getHealth() != player.getMaxHealth()) {
                player.heal(healing);
                shouldPlayEffects = true;
            }

            if (player.getFoodData().getFoodLevel() < 20) {
                player.getFoodData().eat(hunger, saturation);
                shouldPlayEffects = true;
            }

            if (shouldPlayEffects) {
                playEffects(serverLevel, player);
            }
        }
    }

    private static void playEffects(Level level, Player player) {
        BlockPos pos = player.blockPosition();

        level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.5F, 1.0F);
        infested.queueServerWork(5, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.5F, 1.0F));
        infested.queueServerWork(10, () -> level.playSound(null, pos, SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, 0.5F, 1.0F));

        ParticleMethods.ParticlesAroundServerSide(level, ParticleTypes.HAPPY_VILLAGER,
                player.getX(), player.getY() + 1, player.getZ(), 5, 1);
    }
}
