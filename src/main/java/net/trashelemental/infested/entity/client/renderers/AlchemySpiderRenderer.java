package net.trashelemental.infested.entity.client.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
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
import net.trashelemental.infested.entity.custom.spider_alchemy.AlchemySpiderEntity;
import net.trashelemental.infested.entity.custom.spider_alchemy.DamagedSpiderEntity;
import net.trashelemental.infested.infested;

public class AlchemySpiderRenderer extends MobRenderer<AlchemySpiderEntity, ModSpiderModel<AlchemySpiderEntity>> {

    public AlchemySpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new ModSpiderModel<>(context.bakeLayer(ModEventBusClientEvents.MOD_SPIDER)), 0.3f);
        this.addLayer(new EmissiveLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(AlchemySpiderEntity entity) {
        return entity.getTexture();
    }

    private static class EmissiveLayer<T extends AlchemySpiderEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        public EmissiveLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            ResourceLocation emissiveTexture = entity.getEmissiveTexture();

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(emissiveTexture));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    @Override
    protected void setupRotations(AlchemySpiderEntity spider, PoseStack poseStack, float ageInTicks, float rotationYaw, float partialTicks) {
        super.setupRotations(spider, poseStack, ageInTicks, rotationYaw, partialTicks);

        if (spider instanceof DamagedSpiderEntity) {
            poseStack.translate(0.0F, spider.getBbHeight() + 0.1F, 0.0F);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180.0F));
        }
    }

    @Override
    public void render(AlchemySpiderEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        float scale = 0.6f;
        pPoseStack.pushPose();
        pPoseStack.scale(scale, scale, scale);
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
        pPoseStack.popPose();
    }
}

