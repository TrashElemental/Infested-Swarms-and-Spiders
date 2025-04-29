package net.trashelemental.infested.magic.enchantments.event;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.magic.enchantments.ModEnchantments;

@Mod.EventBusSubscriber
public class EnsnaringStrikeEnchantmentEvent {

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {

            ItemStack weapon = player.getMainHandItem();
            if (weapon.isEmpty()) return;

            int enchantmentLevel = EnchantmentHelper.getEnchantments(weapon)
                    .getOrDefault(ModEnchantments.ENSNARING_STRIKE.get(), 0);

            if (enchantmentLevel > 0) {
                int duration = 20 * enchantmentLevel;
                event.getEntity().addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, duration, 3));

                if (player.getAbilities().instabuild) {
                    weapon.hurtAndBreak(5, player, (e) -> e.broadcastBreakEvent(player.getUsedItemHand()));
                }
            }
        }
    }
}
