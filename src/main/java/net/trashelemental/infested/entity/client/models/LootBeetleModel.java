package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.loot_beetles.LootBeetleEntity;
import software.bernie.geckolib.model.GeoModel;

public class LootBeetleModel extends GeoModel<LootBeetleEntity> {

	@Override
	public ResourceLocation getModelResource(LootBeetleEntity entity) {
		return new ResourceLocation("infested", "geo/models/entity/loot_beetle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(LootBeetleEntity entity) {
		return entity.getTexture();
	}

	@Override
	public ResourceLocation getAnimationResource(LootBeetleEntity entity) {
		return new ResourceLocation("infested", "animations/entity/loot_beetle.animation.json");
	}
}