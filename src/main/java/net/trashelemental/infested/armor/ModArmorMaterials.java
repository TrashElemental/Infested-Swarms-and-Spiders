package net.trashelemental.infested.armor;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.util.function.Supplier;

public enum ModArmorMaterials implements ArmorMaterial {

    CHITIN("chitin", 15, new int[]{2, 6, 5, 2}, 15,
            SoundEvents.ARMOR_EQUIP_TURTLE, 1f, 0.1f, () -> Ingredient.of(ModItems.CHITIN.get())),
    SPIDER("spider", 15, new int[]{2, 6, 5, 2}, 15,
    SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, () -> Ingredient.of(Items.COBWEB)),
    BEE("bee", 15, new int[]{2, 6, 5, 2}, 15,
    SoundEvents.ARMOR_EQUIP_LEATHER, 0f, 0f, () -> Ingredient.of(Items.HONEYCOMB))

    ;

    private final String name;
    private final int durabilityMultiplier;
    private final int[] protectionAmounts;
    private final int enchantmentValue;
    private final SoundEvent equipSound;
    private final float toughness;
    private final float knockbackResistance;
    private final Supplier<Ingredient> repairIngredient;

    private static final int[] BASE_DURABILITY = {11, 16, 16, 13};

    ModArmorMaterials(String name, int durabilityMultiplier, int[] protectionAmounts, int enchantmentValue, SoundEvent equipSound, float toughness, float knockbackResistance, Supplier<Ingredient> repairIngredient) {
        this.name = name;
        this.durabilityMultiplier = durabilityMultiplier;
        this.protectionAmounts = protectionAmounts;
        this.enchantmentValue = enchantmentValue;
        this.equipSound = equipSound;
        this.toughness = toughness;
        this.knockbackResistance = knockbackResistance;
        this.repairIngredient = repairIngredient;
    }

    @Override
    public int getDurabilityForType(ArmorItem.Type pType) {
        return BASE_DURABILITY[pType.ordinal()] * durabilityMultiplier;
    }

    @Override
    public int getDefenseForType(ArmorItem.Type pType) {
        return this.protectionAmounts[pType.ordinal()];
    }

    @Override
    public int getEnchantmentValue() {
        return enchantmentValue;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }

    @Override
    public String getName() {
        return infested.MOD_ID + ":" + this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
