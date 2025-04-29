package net.trashelemental.infested.magic.effects.event;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.effects.ModMobEffects;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class AmbushAttackEvent {

    /**
     * When an attack is made while the attacker has the Ambush effect, the
     * attack deals extra damage and inflicts slowness. The attacker is given
     * the Ambush Cooldown effect. For each mantis sickle in the main or offhand,
     * the damage is increased.
     */

    private static final int AmbushBaseDamage = 6;
    private static final int BonusDamage = 3;

    @SubscribeEvent
    public static void onLivingAttack(LivingAttackEvent event) {
        if (event.getEntity() == null || event.getSource().getEntity() == null) return;
        if (!(event.getSource().getEntity() instanceof LivingEntity attacker)) return;
        if (!attacker.hasEffect(ModMobEffects.AMBUSH.get())) return;

        LivingEntity victim = event.getEntity();
        Level level = victim.level();

        if (level.isClientSide) return;

        attacker.removeEffect(MobEffects.INVISIBILITY);
        attacker.removeEffect(MobEffects.MOVEMENT_SLOWDOWN);
        attacker.removeEffect(ModMobEffects.AMBUSH.get());
        attacker.addEffect(new MobEffectInstance(ModMobEffects.AMBUSH_COOLDOWN.get(), 300, 0, false, false));

        int totalDamage = AmbushBaseDamage;

        Item mainHand = attacker.getItemBySlot(EquipmentSlot.MAINHAND).getItem();
        Item offHand = attacker.getItemBySlot(EquipmentSlot.OFFHAND).getItem();
        if (mainHand == ModItems.MANTIS_SICKLE.get()) {
            totalDamage += BonusDamage;
        }
        if (offHand == ModItems.MANTIS_SICKLE.get()) {
            totalDamage += BonusDamage;
        }

        victim.hurt(new DamageSource(level.registryAccess().registryOrThrow(
                Registries.DAMAGE_TYPE).getHolderOrThrow(DamageTypes.GENERIC)), totalDamage);
        victim.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 4));
    }
}
