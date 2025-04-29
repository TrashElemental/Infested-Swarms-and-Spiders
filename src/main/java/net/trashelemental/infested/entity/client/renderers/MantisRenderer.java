package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.MantisModel;
import net.trashelemental.infested.entity.custom.MantisEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class MantisRenderer extends GeoEntityRenderer<MantisEntity> {

    public MantisRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new MantisModel());
    }

    @Override
    public ResourceLocation getTextureLocation(MantisEntity entity) {
        return entity.getTexture();
    }

    @Override
    public void render(MantisEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        float scale = 1.0f;
        if (entity.isBaby()) { scale = 0.5f; }
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
