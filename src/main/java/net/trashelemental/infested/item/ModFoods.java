package net.trashelemental.infested.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class ModFoods {

    public static final FoodProperties RAW_GRUB = new FoodProperties.Builder().
            nutrition(2).
            saturationMod(0.6f).
            meat().
            build();

    public static final FoodProperties FRIED_GRUB = new FoodProperties.Builder().
            nutrition(8).
            saturationMod(1.6f).
            meat().
            build();

    public static final FoodProperties BUG_STEW = new FoodProperties.Builder().
            nutrition(8).
            saturationMod(0.6f).
            effect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 400, 0), 1.0F).
            build();

}
