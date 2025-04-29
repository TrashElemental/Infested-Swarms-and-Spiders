package net.trashelemental.infested.entity.client.models;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.trashelemental.infested.entity.custom.GrubEntity;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.core.animatable.model.CoreGeoBone;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.model.data.EntityModelData;

public class GrubModel extends GeoModel<GrubEntity> {

	@Override
	public ResourceLocation getModelResource(GrubEntity entity) {
		return new ResourceLocation("infested", "geo/models/entity/grub.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(GrubEntity entity) {
		return new ResourceLocation("infested", "textures/entity/grub.png");
	}

	@Override
	public ResourceLocation getAnimationResource(GrubEntity entity) {
		return new ResourceLocation("infested", "animations/entity/grub.animation.json");
	}

	@Override
	public void setCustomAnimations(GrubEntity animatable, long instanceId, AnimationState<GrubEntity> animationState) {
		CoreGeoBone head = getAnimationProcessor().getBone("Head");

		if (head != null) {
			EntityModelData entityData = animationState.getData(DataTickets.ENTITY_MODEL_DATA);

			head.setRotX(entityData.headPitch() * Mth.DEG_TO_RAD);
			head.setRotY(entityData.netHeadYaw() * Mth.DEG_TO_RAD);
		}
	}
}