package net.trashelemental.infested.armor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.trashelemental.infested.armor.models.ChitinArmorModel;
import net.trashelemental.infested.armor.models.SpiderArmorModel;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = {Dist.CLIENT})
public class ModArmorModels {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {

        event.registerLayerDefinition(ChitinArmorModel.LAYER_LOCATION, ChitinArmorModel::createBodyLayer);
        event.registerLayerDefinition(SpiderArmorModel.LAYER_LOCATION, SpiderArmorModel::createBodyLayer);

    }
}
