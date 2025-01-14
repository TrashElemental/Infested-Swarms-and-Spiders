package net.trashelemental.infested.armor.custom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.minecraftforge.registries.ForgeRegistries;
import net.trashelemental.infested.armor.models.SpiderArmorModel;
import net.trashelemental.infested.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.function.Consumer;

public abstract class SpiderArmorItem extends ArmorItem {
    public SpiderArmorItem(ArmorItem.Type type, Item.Properties properties) {
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
                return ForgeRegistries.SOUND_EVENTS.getValue(new ResourceLocation("item.armor.equip_leather"));
            }

            @Override
            public Ingredient getRepairIngredient() {
                return Ingredient.of(new ItemStack(Blocks.COBWEB));
            }

            @Override
            public String getName() {
                return "spider_armor";
            }

            @Override
            public float getToughness() {
                return 0f;
            }

            @Override
            public float getKnockbackResistance() {
                return 0f;
            }
        }, type, properties);
    }



    @Override
    public void onInventoryTick(ItemStack stack, Level level, Player player, int slotIndex, int selectedIndex) {
        super.onInventoryTick(stack, level, player, slotIndex, selectedIndex);

        if (level.isClientSide) return;

        //Spider Armor: Applies Night Vision when the player wears the helmet
        if (player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.SPIDER_HELMET.get()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 15 * 20, 0, false, false));
        }


    }



    public static class Helmet extends SpiderArmorItem {
        public Helmet() {
            super(ArmorItem.Type.HELMET, new Item.Properties());
        }

        @Override
        public void initializeClient(Consumer<IClientItemExtensions> consumer) {
            consumer.accept(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<LivingEntity> getHumanoidArmorModel(LivingEntity living, ItemStack stack, EquipmentSlot slot, HumanoidModel defaultModel) {
                    HumanoidModel<LivingEntity> armorModel = new HumanoidModel<>(new ModelPart(Collections.emptyList(),
                            Map.of("head", new SpiderArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(SpiderArmorModel.LAYER_LOCATION)).head, "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()), "body",
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
            return "infested:textures/entity/spider_armor_layer_1.png";
        }
    }


    public static class Chestplate extends SpiderArmorItem {
        public Chestplate() {
            super(ArmorItem.Type.CHESTPLATE, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/spider_armor_layer_1.png";
        }
    }

    public static class Leggings extends SpiderArmorItem {
        public Leggings() {
            super(ArmorItem.Type.LEGGINGS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/spider_armor_layer_2.png";
        }
    }

    public static class Boots extends SpiderArmorItem {
        public Boots() {
            super(ArmorItem.Type.BOOTS, new Item.Properties());
        }

        @Override
        public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
            return "infested:textures/entity/spider_armor_layer_1.png";
        }
    }

}
