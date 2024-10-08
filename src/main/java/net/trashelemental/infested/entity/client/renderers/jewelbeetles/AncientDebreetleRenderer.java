package net.trashelemental.infested.entity.client.renderers.jewelbeetles;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.ModEventBusEvents;
import net.trashelemental.infested.entity.client.models.JewelBeetleModel;
import net.trashelemental.infested.entity.custom.jewelbeetles.AncientDebreetleEntity;
import net.trashelemental.infested.infested;

public class AncientDebreetleRenderer extends MobRenderer<AncientDebreetleEntity, JewelBeetleModel<AncientDebreetleEntity>> {

    private static final ResourceLocation MAIN_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/debris_beetle.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/debris_beetle_glow.png");

    public AncientDebreetleRenderer(EntityRendererProvider.Context pContext) {
        super(pContext, new JewelBeetleModel<>(pContext.bakeLayer(ModEventBusEvents.ANCIENT_DEBREETLE_LAYER)), 0.3f);
        this.addLayer(new EmissiveLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AncientDebreetleEntity entity) {
        return MAIN_TEXTURE;
    }

    private static class EmissiveLayer<T extends AncientDebreetleEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        public EmissiveLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(EMISSIVE_TEXTURE));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void render(AncientDebreetleEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float scale = 0.5f;
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }
}
