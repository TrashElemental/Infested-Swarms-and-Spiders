package net.trashelemental.infested.armor.models;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.armor.custom.SpiderArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class SpiderArmorModel extends GeoModel<SpiderArmorItem> {

    @Override
    public ResourceLocation getModelResource(SpiderArmorItem animatable) {
    return new ResourceLocation("infested","geo/models/armor/spider_armor.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(SpiderArmorItem animatable) {
        return new ResourceLocation("infested","textures/models/armor/tamed_spider_armor.png");
    }

    @Override
    public ResourceLocation getAnimationResource(SpiderArmorItem animatable) {
        return null;
    }


}