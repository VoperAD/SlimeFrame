package me.voper.slimeframe.implementation.listeners;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.core.attributes.AdvancedMobDrop;

public class AdvancedMobDropListener implements Listener {

    private final SlimeFrame plugin;

    public AdvancedMobDropListener(SlimeFrame plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityKill(EntityDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player p = e.getEntity().getKiller();

            Set<ItemStack> customDrops = Slimefun.getRegistry().getMobDrops().get(e.getEntityType());

            if (customDrops != null && !customDrops.isEmpty()) {
                for (ItemStack drop : customDrops) {
                    if (canDrop(p, drop, e.getEntityType())) {
                        e.getDrops().add(drop.clone());
                    }
                }
            }
        }
    }


    private boolean canDrop(Player p, ItemStack item, EntityType entity) {
        SlimefunItem sfItem = SlimefunItem.getByItem(item);
        // Only SlimeFrame's own drops are handled here, and a locked one is withheld silently. This used to call
        // canUse(p, true) on every registered mob drop, so each kill sent a message per locked drop of that mob
        // (on top of Slimefun's own MobDropListener).
        if (!(sfItem instanceof AdvancedMobDrop advancedMobDrop)) return false;

        // Will be handled by the Slimefun RandomMobDrop listener
        if (advancedMobDrop.getMobDropChance() != 0) return false;

        if (!sfItem.canUse(p, false)) return false;

        int random = ThreadLocalRandom.current().nextInt(100);

        return advancedMobDrop.getMobDropChance(entity) > random;
    }

}
