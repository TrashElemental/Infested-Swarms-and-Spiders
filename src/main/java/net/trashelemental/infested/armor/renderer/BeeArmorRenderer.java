package net.trashelemental.infested.armor.renderer;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.armor.custom.BeeArmorItem;
import net.trashelemental.infested.armor.custom.ChitinArmorItem;
import net.trashelemental.infested.armor.models.BeeArmorModel;
import net.trashelemental.infested.armor.models.ChitinArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class BeeArmorRenderer extends GeoArmorRenderer<BeeArmorItem> {

    public BeeArmorRenderer() {
        super(new BeeArmorModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BeeArmorItem armorItem) {
        return new ResourceLocation("infested","textures/models/armor/bee_armor.png");
    }
}