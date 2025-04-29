package net.trashelemental.infested.armor.renderer;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.armor.custom.ChitinArmorItem;
import net.trashelemental.infested.armor.models.ChitinArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class ChitinArmorRenderer extends GeoArmorRenderer<ChitinArmorItem> {

    public ChitinArmorRenderer() {
        super(new ChitinArmorModel());
    }

    @Override
    public ResourceLocation getTextureLocation(ChitinArmorItem armorItem) {
        return new ResourceLocation("infested","textures/models/armor/chitin_armor.png");
    }
}