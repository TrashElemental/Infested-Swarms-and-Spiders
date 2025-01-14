package net.trashelemental.infested.entity.client.renderers.minions;

import net.minecraft.client.model.BeeModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.trashelemental.infested.entity.custom.minions.AttackBeeEntity;
import org.jetbrains.annotations.NotNull;

public class AttackBeeRenderer extends MobRenderer<AttackBeeEntity, BeeModel<AttackBeeEntity>> {
    public AttackBeeRenderer(EntityRendererProvider.Context context) {
        super(context, new BeeModel<>(context.bakeLayer(ModelLayers.BEE)), 0.3f);
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull AttackBeeEntity entity) {
        if (entity.isAggressive()) {
            return new ResourceLocation("infested","textures/entity/bee_angry.png");
        }

        return new ResourceLocation("infested","textures/entity/bee.png");
    }
}
