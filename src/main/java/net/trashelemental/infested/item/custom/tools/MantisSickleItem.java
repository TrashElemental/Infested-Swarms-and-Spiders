package net.trashelemental.infested.item.custom.tools;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.trashelemental.infested.item.ModToolTiers;

public class MantisSickleItem extends SwordItem {
    public MantisSickleItem(Tier tier, Properties properties) {
        super(ModToolTiers.MANTIS, 3, -2.4f, new Item.Properties());
    }
}
