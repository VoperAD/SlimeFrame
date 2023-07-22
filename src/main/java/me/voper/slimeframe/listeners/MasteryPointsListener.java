package me.voper.slimeframe.listeners;

import io.github.thebusybiscuit.slimefun4.api.events.ResearchUnlockEvent;
import me.voper.slimeframe.SlimeFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

// Every action that changes a player's mastery points
public final class MasteryPointsListener implements Listener {

    private final SlimeFrame plugin;

    public MasteryPointsListener(SlimeFrame plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onResearchUnlock(ResearchUnlockEvent e) {
//        if(e.getResearch().getKey().equals(Keys.ORES_RESEARCH)) {
//            PlayerData.incXP(e.getPlayer(), 100);
//        }
    }

}
