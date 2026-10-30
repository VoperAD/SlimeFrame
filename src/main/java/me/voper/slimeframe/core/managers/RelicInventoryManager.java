package me.voper.slimeframe.core.managers;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import javax.annotation.Nonnull;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.implementation.items.relics.RelicInventory;

public class RelicInventoryManager {

    private static final String CONFIG_PREFIX = "inventories.";
    private static final String FILE_NAME = "relics-inventories.yml";
    private static final long AUTOSAVE_TICKS = 20L * 60;

    private final SlimeFrame plugin;
    private final Map<UUID, RelicInventory> relicInventories = new HashMap<>();
    private File file;
    private FileConfiguration configFile;
    private String lastSaved = "";

    public RelicInventoryManager(SlimeFrame plugin) {
        this.plugin = plugin;
    }

    public void setup() {
        this.file = new File(plugin.getDataFolder(), FILE_NAME);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        this.configFile = YamlConfiguration.loadConfiguration(file);
        this.lastSaved = configFile.saveToString();
        // Relics used to be saved only in onDisable, so a crash lost every relic earned since the last restart.
        // Check once a minute and write only when something changed.
        plugin.getServer().getScheduler().runTaskTimer(plugin, this::saveConfig, AUTOSAVE_TICKS, AUTOSAVE_TICKS);
    }

    public void saveConfig() {
        for (Map.Entry<UUID, RelicInventory> entry : relicInventories.entrySet()) {
            String path = CONFIG_PREFIX + entry.getKey() + ".";
            Inventory inventory = entry.getValue().getInventory();
            for (int i = 0; i < RelicInventory.SIZE; i++) {
                configFile.set(path + i, inventory.getItem(i));
            }
        }

        String data = configFile.saveToString();
        if (data.equals(lastSaved)) return;
        // Write a temp file and swap it in, so a crash mid-write cannot leave a truncated file
        Path target = file.toPath();
        Path tmp = target.resolveSibling(FILE_NAME + ".tmp");
        try {
            Files.writeString(tmp, data, StandardCharsets.UTF_8);
            try {
                Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
            }
            lastSaved = data;
        } catch (IOException e) {
            plugin.getLogger().log(Level.WARNING, "Could not save " + FILE_NAME, e);
        }
    }

    public RelicInventory getRelicInventory(@Nonnull Player player) {
        if (relicInventories.get(player.getUniqueId()) == null) {
            loadInventory(player);
        }
        return relicInventories.get(player.getUniqueId());
    }

    public void loadInventory(@Nonnull Player player) {
        if (configFile.contains(CONFIG_PREFIX + player.getUniqueId())) {
            RelicInventory relicInventory = new RelicInventory(player);
            Inventory inventory = relicInventory.getInventory();
            for (int i = 0; i < RelicInventory.SIZE; i++) {
                inventory.setItem(i, configFile.getItemStack(CONFIG_PREFIX + player.getUniqueId() + "." + i));
            }
            relicInventories.put(player.getUniqueId(), relicInventory);
        } else {
            relicInventories.put(player.getUniqueId(), new RelicInventory(player));
        }
    }

}
