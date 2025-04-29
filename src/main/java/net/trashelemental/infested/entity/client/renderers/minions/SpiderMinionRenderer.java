package net.trashelemental.infested.entity.client.renderers.minions;

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
import net.trashelemental.infested.entity.ModEventBusClientEvents;
import net.trashelemental.infested.entity.client.models.ModSpiderModel;
import net.trashelemental.infested.entity.custom.minions.SpiderMinionEntity;
import net.trashelemental.infested.infested;

public class SpiderMinionRenderer extends MobRenderer<SpiderMinionEntity, ModSpiderModel<SpiderMinionEntity>> {
    private static final ResourceLocation MAIN_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/cave_spider.png");
    private static final ResourceLocation EMISSIVE_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_green.png");
    private static final ResourceLocation EMISSIVE_TEXTURE_ATTACK = new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_red.png");

    public SpiderMinionRenderer(EntityRendererProvider.Context context) {
        super(context, new ModSpiderModel<>(context.bakeLayer(ModEventBusClientEvents.MOD_SPIDER)), 0.3f);
        this.addLayer(new EmissiveLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(SpiderMinionEntity entity) {
        return MAIN_TEXTURE;
    }

    private static class EmissiveLayer<T extends SpiderMinionEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        public EmissiveLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation emissiveTexture = entity.shouldUseRedEyes() ? EMISSIVE_TEXTURE_ATTACK : EMISSIVE_TEXTURE;

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(emissiveTexture));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    public void render(SpiderMinionEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float scale = 0.3f;
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }
}

