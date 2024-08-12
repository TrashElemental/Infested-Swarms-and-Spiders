package net.trashelemental.infested.entity.client.renderers;

import net.minecraft.client.model.SilverfishModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.AttackSilverfishEntity;
import org.jetbrains.annotations.NotNull;

public class AttackSilverfishRenderer extends MobRenderer<AttackSilverfishEntity, SilverfishModel<AttackSilverfishEntity>> {
    public AttackSilverfishRenderer(EntityRendererProvider.Context context) {
        super(context, new SilverfishModel<>(context.bakeLayer(ModelLayers.SILVERFISH)), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AttackSilverfishEntity entity) {
        return new ResourceLocation("textures/entity/silverfish.png");
    }
}

