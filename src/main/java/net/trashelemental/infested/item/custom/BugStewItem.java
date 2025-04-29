package net.trashelemental.infested.item.custom;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BugStewItem extends Item {
    public BugStewItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {

        tooltipComponents.add(Component.translatable("tooltip.infested.bug_stew").withStyle(ChatFormatting.BLUE));

        super.appendHoverText(stack, level, tooltipComponents, tooltipFlag);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack itemstack = super.finishUsingItem(stack, world, entity);

        if (entity instanceof Player player) {
            if (!player.isCreative()) {
                if (!player.getInventory().add(new ItemStack(Items.BOWL))) {
                    player.drop(new ItemStack(Items.BOWL), false);
                }
            }
        }

        return itemstack;
    }

}
