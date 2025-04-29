package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.trashelemental.infested.entity.custom.CrimsonBeetleEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class CrimsonBeetleModel extends GeoModel<CrimsonBeetleEntity> {

	@Override
	public ResourceLocation getModelResource(CrimsonBeetleEntity entity) {
		return new ResourceLocation("infested", "geo/models/entity/crimson_beetle.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(CrimsonBeetleEntity entity) {
		return new ResourceLocation("infested", "textures/entity/crimson_beetle.png");
	}

	@Override
	public ResourceLocation getAnimationResource(CrimsonBeetleEntity entity) {
		return new ResourceLocation("infested", "animations/entity/crimson_beetle.animation.json");
	}

	@Override
	public void setCustomAnimations(CrimsonBeetleEntity animatable, long instanceId, AnimationState<CrimsonBeetleEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}