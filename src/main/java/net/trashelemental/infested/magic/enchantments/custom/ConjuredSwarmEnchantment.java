package net.trashelemental.infested.magic.enchantments.custom;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import net.trashelemental.infested.item.custom.SwarmCellItem;

public class ConjuredSwarmEnchantment extends Enchantment {
    public ConjuredSwarmEnchantment(EquipmentSlot... slots) {
        super(Rarity.COMMON, EnchantmentCategory.BREAKABLE, slots);
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean canEnchant(ItemStack item) {
        return item.getItem() instanceof SwarmCellItem;
    }

}

