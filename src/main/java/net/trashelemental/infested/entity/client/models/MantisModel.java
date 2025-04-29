package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.trashelemental.infested.entity.custom.MantisEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class MantisModel extends GeoModel<MantisEntity> {

	@Override
	public ResourceLocation getModelResource(MantisEntity entity) {
		return new ResourceLocation("infested", "geo/models/entity/mantis.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(MantisEntity entity) {
		return entity.getTexture();
	}

	@Override
	public ResourceLocation getAnimationResource(MantisEntity entity) {
		return new ResourceLocation("infested", "animations/entity/mantis.animation.json");
	}

	@Override
	public void setCustomAnimations(MantisEntity animatable, long instanceId, AnimationState<MantisEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}