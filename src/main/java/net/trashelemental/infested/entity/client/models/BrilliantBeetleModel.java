package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class BrilliantBeetleModel extends GeoModel<BrilliantBeetleEntity> {

	@Override
	public ResourceLocation getModelResource(BrilliantBeetleEntity entity) {
		return new ResourceLocation("infested", "geo/models/entity/brilliant_beetle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BrilliantBeetleEntity entity) {
		return new ResourceLocation("infested", "textures/entity/brilliant_beetle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BrilliantBeetleEntity entity) {
		return new ResourceLocation("infested", "animations/entity/brilliant_beetle.animation.json");
	}

	@Override
	public void setCustomAnimations(BrilliantBeetleEntity animatable, long instanceId, AnimationState<BrilliantBeetleEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}