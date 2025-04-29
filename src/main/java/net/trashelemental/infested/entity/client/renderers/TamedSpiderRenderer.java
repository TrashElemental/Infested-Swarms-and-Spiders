package net.trashelemental.infested.entity.client.renderers;

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
import net.minecraft.world.item.DyeColor;
import net.trashelemental.infested.entity.ModEventBusClientEvents;
import net.trashelemental.infested.entity.client.models.ModSpiderModel;
import net.trashelemental.infested.entity.custom.TamedSpiderEntity;
import net.trashelemental.infested.infested;

import java.util.HashMap;
import java.util.Map;

public class TamedSpiderRenderer extends MobRenderer<TamedSpiderEntity, ModSpiderModel<TamedSpiderEntity>> {
    private static final Map<DyeColor, ResourceLocation> EYE_TEXTURES = new HashMap<>();

    static {
        for (DyeColor color : DyeColor.values()) {
            EYE_TEXTURES.put(color, new ResourceLocation(infested.MOD_ID, "textures/entity/spider_eye_" + color.getName() + ".png"));
        }
    }

    public TamedSpiderRenderer(EntityRendererProvider.Context context) {
        super(context, new ModSpiderModel<>(context.bakeLayer(ModEventBusClientEvents.MOD_SPIDER)), 0.3f);
        this.addLayer(new EmissiveLayer<>(this));
        this.addLayer(new ArmorLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(TamedSpiderEntity entity) {
        return entity.getTexture();
    }

    private static class EmissiveLayer<T extends TamedSpiderEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        public EmissiveLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            DyeColor eyeColor = entity.getEyeColor();
            ResourceLocation eyeTexture = EYE_TEXTURES.getOrDefault(eyeColor, EYE_TEXTURES.get(DyeColor.GREEN));

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.eyes(eyeTexture));
            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight, LivingEntityRenderer.getOverlayCoords(entity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    private static class ArmorLayer<T extends TamedSpiderEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

        private static final ResourceLocation ARMOR_TEXTURE = new ResourceLocation(infested.MOD_ID, "textures/entity/tamed_spider_armor.png");

        public ArmorLayer(RenderLayerParent<T, M> parent) {
            super(parent);
        }

        @Override
        public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity,
                           float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks,
                           float netHeadYaw, float headPitch) {

            VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutoutNoCull(ARMOR_TEXTURE));

            float scale = 0;

            poseStack.pushPose();

            if (entity.isArmored()) {
                scale = 1.05f;
            }

            poseStack.scale(scale, scale, scale);
            poseStack.translate(0.0F, -0.04F, 0F);

            this.getParentModel().renderToBuffer(poseStack, vertexConsumer, packedLight,
                    LivingEntityRenderer.getOverlayCoords(entity, 0.0F),
                    1.0F, 1.0F, 1.0F, 1.0F);

            poseStack.popPose();
        }
    }

    @Override
    public void render(TamedSpiderEntity entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        float scale = 0.6f;
        poseStack.pushPose();
        poseStack.scale(scale, scale, scale);
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.popPose();
    }
}

