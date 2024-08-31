package net.trashelemental.infested;

import com.mojang.logging.LogUtils;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.trashelemental.infested.block.ModBlocks;
import net.trashelemental.infested.magic.brewing.ModPotions;
import net.trashelemental.infested.magic.effects.ModMobEffects;
import net.trashelemental.infested.magic.enchantments.ModEnchantments;
import net.trashelemental.infested.entity.ModEntities;
import net.trashelemental.infested.entity.client.renderers.*;
import net.trashelemental.infested.entity.client.renderers.jewelbeetles.AncientDebreetleRenderer;
import net.trashelemental.infested.entity.client.renderers.jewelbeetles.ChorusBeetleRenderer;
import net.trashelemental.infested.entity.client.renderers.jewelbeetles.HarvestBeetleRenderer;
import net.trashelemental.infested.entity.client.renderers.jewelbeetles.JewelBeetleRenderer;
import net.trashelemental.infested.entity.client.renderers.silverfish.AttackSilverfishRenderer;
import net.trashelemental.infested.entity.client.renderers.silverfish.TamedSilverfishRenderer;
import net.trashelemental.infested.entity.client.renderers.spiders.AttackSpiderRenderer;
import net.trashelemental.infested.entity.client.renderers.spiders.SpiderMinionRenderer;
import net.trashelemental.infested.entity.client.renderers.spiders.TamedSpiderRenderer;
import net.trashelemental.infested.item.ModCreativeModeTabs;
import net.trashelemental.infested.item.ModItems;
import net.trashelemental.infested.util.loot.ModLootModifiers;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

@Mod(infested.MOD_ID)
public class infested
{
    public static final String MOD_ID = "infested";

    private static final Logger LOGGER = LogUtils.getLogger();

    public infested()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModPotions.register(modEventBus);
        ModMobEffects.register(modEventBus);
        ModEnchantments.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {

    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }
    
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

            EntityRenderers.register(ModEntities.CRIMSON_BEETLE.get(), CrimsonBeetleRenderer::new);
            EntityRenderers.register(ModEntities.GRUB.get(), GrubRenderer::new);
            EntityRenderers.register(ModEntities.HARVEST_BEETLE.get(), HarvestBeetleRenderer::new);
            EntityRenderers.register(ModEntities.JEWEL_BEETLE.get(), JewelBeetleRenderer::new);
            EntityRenderers.register(ModEntities.CHORUS_BEETLE.get(), ChorusBeetleRenderer::new);
            EntityRenderers.register(ModEntities.ANCIENT_DEBREETLE.get(), AncientDebreetleRenderer::new);
            EntityRenderers.register(ModEntities.TAMED_SILVERFISH.get(), TamedSilverfishRenderer::new);
            EntityRenderers.register(ModEntities.ATTACK_SILVERFISH.get(), AttackSilverfishRenderer::new);
            EntityRenderers.register(ModEntities.BRILLIANT_BEETLE.get(), BrilliantBeetleRenderer::new);
            EntityRenderers.register(ModEntities.MANTIS.get(), MantisRenderer::new);
            EntityRenderers.register(ModEntities.ORCHID_MANTIS.get(), OrchidMantisRenderer::new);
            EntityRenderers.register(ModEntities.TAMED_SPIDER.get(), TamedSpiderRenderer::new);
            EntityRenderers.register(ModEntities.SPIDER_MINION.get(), SpiderMinionRenderer::new);
            EntityRenderers.register(ModEntities.ATTACK_SPIDER.get(), AttackSpiderRenderer::new);


        }
    }


    private static final Collection<AbstractMap.SimpleEntry<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

    public static void queueServerWork(int tickDelay, Runnable action) {
        workQueue.add(new AbstractMap.SimpleEntry<>(action, tickDelay));
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            List<AbstractMap.SimpleEntry<Runnable, Integer>> actionsToRun = new ArrayList<>();
            workQueue.forEach(work -> {
                work.setValue(work.getValue() - 1);
                if (work.getValue() <= 0) {
                    actionsToRun.add(work);
                }
            });
            actionsToRun.forEach(work -> work.getKey().run());
            workQueue.removeAll(actionsToRun);
        }
    }

}
