package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.GrubModel;
import net.trashelemental.infested.entity.custom.GrubEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class GrubRenderer extends GeoEntityRenderer<GrubEntity> {

    public GrubRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new GrubModel());
    }

    @Override
    public ResourceLocation getTextureLocation(GrubEntity entity) {
        return new ResourceLocation("infested", "textures/entity/grub.png");
    }

    @Override
    public void render(GrubEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
