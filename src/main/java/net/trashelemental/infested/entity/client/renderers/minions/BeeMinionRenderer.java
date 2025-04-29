package net.trashelemental.infested.entity.client.renderers.minions;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.BeeMinionModel;
import net.trashelemental.infested.entity.custom.minions.BeeMinionEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class BeeMinionRenderer extends GeoEntityRenderer<BeeMinionEntity> {

    public BeeMinionRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BeeMinionModel());
    }

    @Override
    public ResourceLocation getTextureLocation(BeeMinionEntity animatable) {
        if (animatable.isAggressive()) {
            return new ResourceLocation("infested", "textures/entity/bee_angry.png");
        }

        return new ResourceLocation("infested","textures/entity/bee.png");
    }

    @Override
    public void render(BeeMinionEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {


        poseStack.scale(0.5f, 0.5f, 0.5f);


        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
