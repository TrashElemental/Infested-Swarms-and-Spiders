package net.trashelemental.infested.armor.renderer;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.armor.custom.SpiderArmorItem;
import net.trashelemental.infested.armor.models.SpiderArmorModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class SpiderArmorRenderer extends GeoArmorRenderer<SpiderArmorItem> {

    public SpiderArmorRenderer() {
        super(new SpiderArmorModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderArmorItem armorItem) {
        return new ResourceLocation("infested","textures/models/armor/spider_armor.png");
    }
}