package net.trashelemental.infested.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.ConsumeItemTrigger;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.util.function.Consumer;

public class ModAdvancementGenerator implements ForgeAdvancementProvider.AdvancementGenerator {

    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> writer, ExistingFileHelper existingFileHelper) {

        Advancement SpiderEggGet = Advancement.Builder.advancement()
                .display(
                        ModItems.SPIDER_EGG.get(), // Icon
                        Component.translatable("advancements.spider_egg_get.title"), // Title
                        Component.translatable("advancements.spider_egg_get.description"), // Description
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), // Background
                        FrameType.GOAL, // Frame Type
                        true, // Show Toast
                        true, // Announce to Chat
                        false // Hidden
                )
                .parent(new ResourceLocation("minecraft:husbandry/tame_an_animal"))
                .addCriterion("has_spider_egg", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SPIDER_EGG.get()))
                .save(writer, new ResourceLocation(infested.MOD_ID, "spider_egg_get"), existingFileHelper);


        Advancement SilverfishEggsGet = Advancement.Builder.advancement()
                .display(
                        ModItems.SILVERFISH_EGGS.get(), // Icon
                        Component.translatable("advancements.silverfish_eggs_get.title"), // Title
                        Component.translatable("advancements.silverfish_eggs_get.description"), // Description
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), // Background
                        FrameType.GOAL, // Frame Type
                        true, // Show Toast
                        true, // Announce to Chat
                        false // Hidden
                )
                .parent(new ResourceLocation("minecraft:husbandry/tame_an_animal"))
                .addCriterion("has_silverfish_eggs", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SILVERFISH_EGGS.get()))
                .save(writer, new ResourceLocation(infested.MOD_ID, "silverfish_eggs_get"), existingFileHelper);


        Advancement InsectTemplateGet = Advancement.Builder.advancement()
                .display(
                        ModItems.INSECT_TEMPLATE.get(), // Icon
                        Component.translatable("advancements.insect_template_get.title"), // Title
                        Component.translatable("advancements.insect_template_get.description"), // Description
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), // Background
                        FrameType.GOAL, // Frame Type
                        true, // Show Toast
                        true, // Announce to Chat
                        false // Hidden
                )
                .parent(new ResourceLocation("minecraft:adventure/trim_with_any_armor_pattern"))
                .addCriterion("has_insect_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.INSECT_TEMPLATE.get()))
                .save(writer, new ResourceLocation(infested.MOD_ID, "insect_template_get"), existingFileHelper);


        Advancement SpiderTemplateGet = Advancement.Builder.advancement()
                .display(
                        ModItems.SPIDER_TEMPLATE.get(), // Icon
                        Component.translatable("advancements.spider_template_get.title"), // Title
                        Component.translatable("advancements.spider_template_get.description"), // Description
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), // Background
                        FrameType.GOAL, // Frame Type
                        true, // Show Toast
                        true, // Announce to Chat
                        false // Hidden
                )
                .parent(new ResourceLocation("minecraft:adventure/trim_with_any_armor_pattern"))
                .addCriterion("has_spider_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.SPIDER_TEMPLATE.get()))
                .save(writer, new ResourceLocation(infested.MOD_ID, "spider_template_get"), existingFileHelper);


        Advancement FriedGrubEat = Advancement.Builder.advancement()
                .display(
                        ModItems.FRIED_GRUB.get(), // Icon
                        Component.translatable("advancements.fried_grub_eat.title"), // Title
                        Component.translatable("advancements.fried_grub_eat.description"), // Description
                        new ResourceLocation("textures/gui/advancements/backgrounds/stone.png"), // Background
                        FrameType.GOAL, // Frame Type
                        true, // Show Toast
                        true, // Announce to Chat
                        false // Hidden
                )
                .parent(new ResourceLocation("minecraft:husbandry/balanced_diet"))
                .addCriterion("has_eaten_fried_grub", ConsumeItemTrigger.TriggerInstance.usedItem(ModItems.FRIED_GRUB.get()))
                .save(writer, new ResourceLocation(infested.MOD_ID, "fried_grub_eat"), existingFileHelper);

    }
}
