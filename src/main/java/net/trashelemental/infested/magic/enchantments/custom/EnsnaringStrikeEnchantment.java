package net.trashelemental.infested.magic.enchantments.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

public class EnsnaringStrikeEnchantment extends Enchantment {

    public EnsnaringStrikeEnchantment(EquipmentSlot... slots) {
        super(Rarity.RARE, EnchantmentCategory.WEAPON, slots);
    }

    @Override
    public int getMaxLevel() {
        return 3;
    }
}

