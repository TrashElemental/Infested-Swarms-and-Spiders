package net.trashelemental.infested.datagen.tags;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {
    public ModItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_, CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, infested.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        addForgeTags();
        addNeoforgeTags();

    }

    private void addForgeTags() {

        tag(ItemTags.create(new ResourceLocation("forge", "swords"))).add(ModItems.MANTIS_SICKLE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "swords"))).add(ModItems.SPIDER_SICA.get());
        tag(ItemTags.create(new ResourceLocation("forge", "swords"))).add(ModItems.STINGER_PONIARD.get());

        //Armor sets
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.SPIDER_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.SPIDER_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.SPIDER_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.SPIDER_BOOTS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.CHITIN_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.CHITIN_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.CHITIN_BOOTS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.BEE_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.BEE_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.BEE_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors"))).add(ModItems.BEE_BOOTS.get());

        tag(ItemTags.create(new ResourceLocation("forge", "armors/helmets"))).add(ModItems.SPIDER_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chestplates"))).add(ModItems.SPIDER_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chest_armors"))).add(ModItems.SPIDER_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/leggings"))).add(ModItems.SPIDER_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/boots"))).add(ModItems.SPIDER_BOOTS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/helmets"))).add(ModItems.CHITIN_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chestplates"))).add(ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chest_armors"))).add(ModItems.CHITIN_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/leggings"))).add(ModItems.CHITIN_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/boots"))).add(ModItems.CHITIN_BOOTS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/helmets"))).add(ModItems.BEE_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chestplates"))).add(ModItems.BEE_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/chest_armors"))).add(ModItems.BEE_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/leggings"))).add(ModItems.BEE_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("forge", "armors/boots"))).add(ModItems.BEE_BOOTS.get());
    }

    private void addNeoforgeTags() {

        tag(ItemTags.create(new ResourceLocation("neoforge", "swords"))).add(ModItems.MANTIS_SICKLE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "tools/swords"))).add(ModItems.MANTIS_SICKLE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "swords"))).add(ModItems.SPIDER_SICA.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "tools/swords"))).add(ModItems.SPIDER_SICA.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "swords"))).add(ModItems.STINGER_PONIARD.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "tools/swords"))).add(ModItems.STINGER_PONIARD.get());

        //Armor sets
        tag(ItemTags.create(new ResourceLocation("neoforge", "armors/helmets"))).add(
                ModItems.SPIDER_HELMET.get(),
                ModItems.CHITIN_HELMET.get(),
                ModItems.BEE_HELMET.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "armors/chestplates"))).add(
                ModItems.SPIDER_CHESTPLATE.get(),
                ModItems.CHITIN_CHESTPLATE.get(),
                ModItems.BEE_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "armors/chest_armors"))).add(
                ModItems.SPIDER_CHESTPLATE.get(),
                ModItems.CHITIN_CHESTPLATE.get(),
                ModItems.BEE_CHESTPLATE.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "armors/leggings"))).add
                (ModItems.SPIDER_LEGGINGS.get(),
                ModItems.CHITIN_LEGGINGS.get(),
                        ModItems.BEE_LEGGINGS.get());
        tag(ItemTags.create(new ResourceLocation("neoforge", "armors/boots"))).add(
                ModItems.SPIDER_BOOTS.get(),
                ModItems.CHITIN_BOOTS.get(),
                ModItems.BEE_BOOTS.get());
    }
}
