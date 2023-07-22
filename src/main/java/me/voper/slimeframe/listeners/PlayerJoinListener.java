package me.voper.slimeframe.listeners;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.data.gson.WarframeDataManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinListener implements Listener {

    private final SlimeFrame plugin;
    private final WarframeDataManager dataManager;

    public PlayerJoinListener(SlimeFrame plugin) {
        this.plugin = plugin;
        this.dataManager = SlimeFrame.getWarframeDataManager();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
    }

}
