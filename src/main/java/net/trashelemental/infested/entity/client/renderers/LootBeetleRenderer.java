package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.client.models.LootBeetleModel;
import net.trashelemental.infested.entity.custom.loot_beetles.LootBeetleEntity;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class LootBeetleRenderer extends GeoEntityRenderer<LootBeetleEntity> {

    public LootBeetleRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new LootBeetleModel());
        this.addRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(LootBeetleEntity entity) {
        return entity.getTexture();
    }

    @Override
    public void render(LootBeetleEntity entity, float entityYaw, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight) {

        float scale = 0.5f;
        poseStack.scale(scale, scale, scale);

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
