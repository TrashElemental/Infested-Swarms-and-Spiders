package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.BrilliantBeetleModel;
import net.trashelemental.infested.entity.custom.BrilliantBeetleEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class BrilliantBeetleRenderer extends GeoEntityRenderer<BrilliantBeetleEntity> {

    public BrilliantBeetleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new BrilliantBeetleModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(BrilliantBeetleEntity entity) {
        return new ResourceLocation("infested", "textures/entity/brilliant_beetle.png");
    }

    @Override
    public void render(BrilliantBeetleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        float scale = 1.0f;
        if (entity.isBaby()) { scale = 0.5f; }
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
