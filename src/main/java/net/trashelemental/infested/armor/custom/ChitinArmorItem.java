package net.trashelemental.infested.armor.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.armor.models.ChitinArmorModel;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class ChitinArmorItem extends ArmorItem {
    public ChitinArmorItem(ArmorItem.Type type, Item.Properties properties) {
        super(new ArmorMaterial() {
            @Override
            public int getDurabilityForType(ArmorItem.Type type) {
                return new int[]{13, 15, 16, 11}[type.getSlot().getIndex()] * 15;
            }

            @Override
            public int getDefenseForType(ArmorItem.Type type) {
                return new int[]{2, 5, 6, 2}[type.getSlot().getIndex()];
            }

            @Override
            public int getEnchantmentValue() {
                return 15;
            }

            @Override
            public SoundEvent getEquipSound() {
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_turtle"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(ModItems.CHITIN.get()));
            }

            @Override
            public String getName() {
                return "chitin_armor";
            }

            @Override
            public float getToughness() {
                return 1f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0.1f;
            }
        }, type, properties);
    }



    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);

        if (level.isClientSide) return;

        //Chitin Armor: Applies Ambush when the player crouches while wearing the full set
        if (isWearingFullChitinSet(player)) {
            if (player.isCrouching()) {
                if (!player.hasEffect(ModMobEffects.AMBUSH_COOLDOWN.get())) {
                    player.addEffect(new MobEffectInstance(ModMobEffects.AMBUSH.get(), 15 * 20));
                }
            }
        }

    }

    private boolean isWearingFullChitinSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.CHITIN_HELMET.get() &&
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.CHITIN_CHESTPLATE.get() &&
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.CHITIN_LEGGINGS.get() &&
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.CHITIN_BOOTS.get();
    }


    public static class Helmet extends ChitinArmorItem {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel<LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                            Map.of("head", new ChitinArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(ChitinArmorModel.LAYER_LOCATION)).head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_arm",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()), "right_leg", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "left_leg",
                                    new ModelPart(Collections.emptyList(), Collections.emptyMap()))));
                    armorModel.crouching = living.isShiftKeyDown();
                    armorModel.riding = defaultModel.riding;
                    armorModel.young = living.isBaby();
                    return armorModel;
                }
            });
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/chitin_armor_layer_1.png";
        }
    }

    public static class Chestplate extends ChitinArmorItem {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/chitin_armor_layer_1.png";
        }
    }

    public static class Leggings extends ChitinArmorItem {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/chitin_armor_layer_2.png";
        }
    }

    public static class Boots extends ChitinArmorItem {
        public Boots() {
            super(ArmorItem.Type.BOOTS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/chitin_armor_layer_1.png";
        }
    }



}
