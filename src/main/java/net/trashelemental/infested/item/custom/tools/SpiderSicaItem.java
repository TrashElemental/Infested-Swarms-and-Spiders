package net.trashelemental.infested.item.custom.tools;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.item.ModToolTiers;

public class SpiderSicaItem extends SwordItem {
    public SpiderSicaItem(Tier tier, Properties properties) {
        super(ModToolTiers.MANTIS, 2, -1.5f, new Properties());
    }

    private double getPoisonChance(Player player) {
        double poisonChance = 0.3;
        double baseBonus = 0;
        double setIncrease = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get()) setIncrease++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SPIDER_CHESTPLATE.get()) setIncrease++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SPIDER_LEGGINGS.get()) setIncrease++;
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SPIDER_BOOTS.get()) setIncrease++;

        double bonusPoisonChance = (setIncrease * 0.1) + baseBonus;

        return poisonChance + bonusPoisonChance;
    }

    private boolean isWearingFullSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.SPIDER_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.SPIDER_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.SPIDER_BOOTS.get();
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        int bonusNightDamage = 2;

        if (attacker instanceof Player player) {

            Level level = player.level();
            if (!level.isDay()) {

                float newHealth = Math.max(target.getHealth() - bonusNightDamage, 0);
                target.setHealth(newHealth);
            }

            if (level.random.nextDouble() < getPoisonChance(player)) {
                int amplifier = isWearingFullSet(player) ? 1 : 0;
                target.addEffect(new MobEffectInstance(MobEffects.POISON, 140, amplifier));
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
