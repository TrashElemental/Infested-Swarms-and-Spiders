package net.trashelemental.infested.util.event;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.item.custom.SwarmCellItem;

import java.util.List;

@Mod.EventBusSubscriber(modid = infested.MOD_ID)
public class TooltipEvent {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        Player player = event.getEntity();
        List<Component> tooltip = event.getToolTip();

        if (player != null && stack.getItem() instanceof SwarmCellItem item && isWearingAnyBeeSet(player)) {
            int bonus = item.getMaxMinions(player);
            tooltip.add(Component.literal("Bonus Minions: " + (bonus - item.getBaseMinions())).withStyle(ChatFormatting.YELLOW));
        }
    }

    private static boolean isWearingAnyBeeSet(Player player) {
        return player.getItemBySlot(EquipmentSlot.HEAD).getItem() == ModItems.BEE_HELMET.get() ||
                player.getItemBySlot(EquipmentSlot.CHEST).getItem() == ModItems.BEE_CHESTPLATE.get() ||
                player.getItemBySlot(EquipmentSlot.LEGS).getItem() == ModItems.BEE_LEGGINGS.get() ||
                player.getItemBySlot(EquipmentSlot.FEET).getItem() == ModItems.BEE_BOOTS.get();
    }
}


