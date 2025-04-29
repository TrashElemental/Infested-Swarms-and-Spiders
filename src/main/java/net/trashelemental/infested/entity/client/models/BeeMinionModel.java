package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import software.bernie.geckolib.model.GeoModel;

public class BeeMinionModel extends GeoModel<BeeMinionEntity> {
    @Override
    public ResourceLocation getModelResource(BeeMinionEntity bee) {
        return new ResourceLocation("infested", "geo/models/entity/bee.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(BeeMinionEntity bee) {

        if (bee.isAggressive()) {
            return new ResourceLocation("infested", "textures/entity/bee_angry.png");
        }

        return new ResourceLocation("infested","textures/entity/bee.png");

    }

    @Override
    public ResourceLocation getAnimationResource(BeeMinionEntity bee) {
        return new ResourceLocation("infested", "animations/entity/bee.animation.json");
    }
}
