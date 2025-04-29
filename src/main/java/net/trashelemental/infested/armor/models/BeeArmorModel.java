package net.trashelemental.infested.armor.models;

import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.armor.custom.BeeArmorItem;
import net.trashelemental.infested.armor.custom.ChitinArmorItem;
import software.bernie.geckolib.model.GeoModel;

public class BeeArmorModel extends GeoModel<BeeArmorItem> {

	@Override
	public ResourceLocation getModelResource(BeeArmorItem animatable) {
		return new ResourceLocation("infested","geo/models/armor/bee_armor.geo.json");
	}

	@Override
	public ResourceLocation getTextureResource(BeeArmorItem animatable) {
		return new ResourceLocation("infested","textures/models/armor/bee_armor.png");
	}

	@Override
	public ResourceLocation getAnimationResource(BeeArmorItem animatable) {
		return null;
	}
}