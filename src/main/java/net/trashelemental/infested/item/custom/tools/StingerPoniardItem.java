package net.trashelemental.infested.item.custom.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.item.ModToolTiers;
import net.trashelemental.infested.junkyard_lib.entity.MinionEntity;
import net.trashelemental.infested.junkyard_lib.entity.method.SummonMethods;

public class StingerPoniardItem extends SwordItem {
    public StingerPoniardItem(Tier tier, Properties properties) {
        super(ModToolTiers.MANTIS, 3, -2.4f, new Properties());
    }

    private double getSummonChance(Player player) {
        double baseChance = 0;

        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.BEE_HELMET.get()) baseChance++;
        if (player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.BEE_CHESTPLATE.get()) baseChance++;
        if (player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.BEE_LEGGINGS.get()) baseChance++;
        if (player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.BEE_BOOTS.get()) baseChance++;

        return baseChance * 0.125;
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {

        if (attacker instanceof Player player &&
                !(target instanceof TamableAnimal tamable && tamable.isOwnedBy(attacker)) &&
                !(target instanceof MinionEntity)) {

            Level level = player.level();
            BlockPos pos = player.blockPosition();
            if (level.random.nextDouble() < getSummonChance(player)) {
                BeeMinionEntity bee = new BeeMinionEntity(ModEntities.BEE_MINION.get(), level);
                SummonMethods.summonMinion(level, pos, bee, 300, false, player);
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.BEEHIVE_ENTER, SoundSource.PLAYERS, 0.5F, 1F);
            }
        }
        return super.hurtEnemy(stack, target, attacker);
    }
}
