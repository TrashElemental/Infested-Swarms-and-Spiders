package net.trashelemental.infested.armor.custom;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import net.trashelemental.infested.armor.renderer.ChitinArmorRenderer;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.core.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.core.animation.AnimatableManager;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.util.GeckoLibUtil;

import java.util.function.Consumer;

public class ChitinArmorItem extends ArmorItem implements GeoAnimatable, GeoItem {

    public ChitinArmorItem(ArmorMaterial pMaterial, Type pType, Properties pProperties) {
        super(pMaterial, pType, pProperties);
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


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }

    @Override
    public void initializeClient(Consumer<IClientItemExtensions> consumer) {
        super.initializeClient(consumer);
        consumer.accept(new IClientItemExtensions() {
            private GeoArmorRenderer<?> renderer;

            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack,
                                                                   EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                if(renderer == null){
                    renderer = new ChitinArmorRenderer();
                }
                renderer.prepForRender(livingEntity, itemStack, equipmentSlot, original);
                return this.renderer;
            }
        });
    }

    @Override
    public double getTick(Object object) {
        return 0;
    }
}
