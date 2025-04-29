package net.trashelemental.infested.entity;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.entity.client.models.ModSpiderModel;
import net.trashelemental.infested.infested;

@Mod.EventBusSubscriber(modid = infested.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEventBusClientEvents {

    public static final ModelLayerLocation MOD_SPIDER = new ModelLayerLocation(
            new ResourceLocation(infested.MOD_ID, "mod_spider_layer"), "main");

    @SubscribeEvent
    public static void registerLayer(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(MOD_SPIDER, ModSpiderModel::createBodyLayer);

    }
}
