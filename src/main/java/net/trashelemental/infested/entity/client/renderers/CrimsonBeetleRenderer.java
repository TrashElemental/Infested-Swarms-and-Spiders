package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.CrimsonBeetleModel;
import net.trashelemental.infested.entity.custom.CrimsonBeetleEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class CrimsonBeetleRenderer extends GeoEntityRenderer<CrimsonBeetleEntity> {

    public CrimsonBeetleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new CrimsonBeetleModel());
    }

    @Override
    public ResourceLocation getTextureLocation(CrimsonBeetleEntity entity) {
        return new ResourceLocation("infested", "textures/entity/crimson_beetle.png");
    }

    @Override
    public void render(CrimsonBeetleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
