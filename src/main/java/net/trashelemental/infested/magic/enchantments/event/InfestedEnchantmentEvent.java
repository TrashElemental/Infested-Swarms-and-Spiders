package net.trashelemental.infested.magic.enchantments.event;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.enchantments.ModEnchantments;
import net.trashelemental.infested.entity.ai.MinionSpawnLogic;

@Mod.EventBusSubscriber
public class InfestedEnchantmentEvent {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof LivingEntity attacker && event.getEntity() instanceof Player player) {

            Level level = player.level();
            ItemStack chestArmor = player.getItemBySlot(EquipmentSlot.CHEST);
            int enchantmentLevel = chestArmor.getEnchantmentLevel(ModEnchantments.INFESTED.get());

            if (chestArmor.is(ModItems.CHITIN_CHESTPLATE.get())) {
                enchantmentLevel += 1;
            }

            if (enchantmentLevel > 0) {
                MinionSpawnLogic.infestedActivate(level, player, enchantmentLevel, 200);
                chestArmor.hurtAndBreak(2, player, (entity) -> entity.broadcastBreakEvent(EquipmentSlot.CHEST));
            }

        }
    }

    @SubscribeEvent
    public static void whenEntityBlocksWithShield(ShieldBlockEvent event) {
        if (event != null && event.getEntity() instanceof Player player) {
            Level level = player.level();
            ItemStack shield = player.getUseItem();
            int enchantmentLevel = shield.getEnchantmentLevel(ModEnchantments.INFESTED.get());

            if (enchantmentLevel > 0) {
                MinionSpawnLogic.infestedActivate(level, player, enchantmentLevel, 200);
                shield.hurtAndBreak(2, player, (entity1) -> entity1.broadcastBreakEvent(EquipmentSlot.OFFHAND));
            }
        }
    }
}