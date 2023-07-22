package me.voper.slimeframe.data.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import lombok.NonNull;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.managers.SettingsManager;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public final class WarframeDataManager {

    private final SlimeFrame plugin;
    private final SettingsManager sm;
    private final Gson gson;
    @Getter private List<WarframePlayer> warframePlayers;
    @Getter private List<WarframePlayer> topPlayers;
    private File file;

    public WarframeDataManager(@NonNull SlimeFrame plugin) {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(WarframePlayer.class, new WarframePlayerAdapter())
                .create();

        this.plugin = plugin;
        this.sm = SlimeFrame.getSettingsManager();
    }

    public void setup() {
        this.file = new File(plugin.getDataFolder().getAbsolutePath() + "/playersdata.json");
        try {
            if (file.createNewFile()) {
                warframePlayers = new ArrayList<>();
            } else {
                FileReader reader = new FileReader(file);
                WarframePlayer[] players = gson.fromJson(reader, WarframePlayer[].class);
                warframePlayers = players != null ? new ArrayList<>(Arrays.asList(players)) : new ArrayList<>();
                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.updateTopPlayers();
    }

    public void save() {
        try (FileWriter writer = new FileWriter(file, false)) {
            gson.toJson(warframePlayers.toArray(), writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Nonnull
    public WarframePlayer getWarframePlayer(@Nonnull Player player) {
        for (WarframePlayer wfPlayer: warframePlayers) {
            if (!player.getUniqueId().equals(wfPlayer.getUuid())) continue;
            return wfPlayer;
        }

        WarframePlayer warframePlayer = new WarframePlayer(player);
        warframePlayers.add(warframePlayer);
        return warframePlayer;
    }

    public void deleteWarframePlayer(@Nonnull Player player) {
        warframePlayers.removeIf(warframePlayer -> warframePlayer.getUuid().equals(player.getUniqueId()));
    }

    public void resetAll() {
        warframePlayers.forEach(WarframePlayer::reset);
        save();
    }

    public void updateTopPlayers() {
        if (warframePlayers.isEmpty()) {
            topPlayers = new ArrayList<>();
        } else {
            topPlayers = warframePlayers.stream()
                    .sorted(Collections.reverseOrder())
                    .toList();
        }
    }

}
