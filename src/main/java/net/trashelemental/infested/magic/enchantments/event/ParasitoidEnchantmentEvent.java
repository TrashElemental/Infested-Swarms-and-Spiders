package net.trashelemental.infested.magic.enchantments.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.magic.enchantments.ModEnchantments;
import net.trashelemental.infested.entity.ai.MinionSpawnLogic;

@Mod.EventBusSubscriber
public class ParasitoidEnchantmentEvent {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {

            if (event.getEntity() instanceof TamableAnimal tamable && tamable.isOwnedBy(player)) return;
            if (event.getEntity() instanceof MinionEntity) return;


            Level level = player.level();
            BlockPos deathPos = event.getEntity().getOnPos();
            ItemStack weapon = player.getMainHandItem();
            ItemStack chestArmor = player.getItemBySlot(EquipmentSlot.CHEST);
            int enchantmentLevel = weapon.getEnchantmentLevel(ModEnchantments.PARASITOID.get());
            int lifespan = 300;

            if (chestArmor.is(ModItems.CHITIN_CHESTPLATE.get())) {
                enchantmentLevel += 1;
            }

            if (weapon.is(ModItems.STINGER_PONIARD.get())) {
                enchantmentLevel += 1;
            }

            if (spiderArmorConditions(level, player, event.getEntity(), deathPos)) {
                enchantmentLevel += 1;
                lifespan += 300;
            }

            int finalEnchantmentLevel = enchantmentLevel;
            int finalLifespan = lifespan;
            infested.queueServerWork(20, () -> {
                if (finalEnchantmentLevel > 0) {
                    MinionSpawnLogic.parasitoidActivate(level, player, deathPos, finalEnchantmentLevel, finalLifespan);
                    weapon.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.MAINHAND));
                }
            });

        }
    }

    private static boolean spiderArmorConditions(Level level, Player player, LivingEntity victim, BlockPos pos) {
        if (isWearingFullSpiderSet(player)) {
            return victim.hasEffect(MobEffects.MOVEMENT_SLOWDOWN) || isInCobwebOrTrap(level, pos);
        }
        return false;
    }

    private static boolean isWearingFullSpiderSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SPIDER_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SPIDER_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SPIDER_BOOTS.get();
    }

    private static boolean isInCobwebOrTrap(Level level, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        return state.is(Blocks.COBWEB) || state.is(ModBlocks.COBWEB_TRAP.get());
    }
}