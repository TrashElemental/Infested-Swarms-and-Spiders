package net.trashelemental.infested.compat.BetterCombat;

import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;
import net.trashelemental.infested.infested;
import net.trashelemental.infested.item.ModItems;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class BetterCombatWeaponPresetProvider implements DataProvider {
    private final PackOutput packOutput;
    private final List<CompletableFuture<?>> futures = new ArrayList<>();
    private CachedOutput cache;
    private Path outputFolder;

    public BetterCombatWeaponPresetProvider(PackOutput packOutput) {
        this.packOutput = packOutput;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        this.cache = cache;
        this.outputFolder = packOutput.getOutputFolder();

        registerWeapon(ModItems.MANTIS_SICKLE, "sickle");
        registerWeapon(ModItems.SPIDER_SICA, "dagger");
        registerWeapon(ModItems.STINGER_PONIARD, "rapier");

        return CompletableFuture.allOf(futures.toArray(new CompletableFuture<?>[0]));
    }

    private void registerWeapon(RegistryObject<Item> item, String preset) {
        JsonObject json = new JsonObject();
        json.addProperty("parent", "bettercombat:" + preset);

        Path path = outputFolder.resolve("data/" + infested.MOD_ID + "/weapon_attributes/" + item.getId().getPath() + ".json");

        futures.add(DataProvider.saveStable(cache, json, path));
    }

    @Override
    public String getName() {
        return "Better Combat Weapon Attributes";
    }
}
