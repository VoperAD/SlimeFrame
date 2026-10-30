package me.voper.slimeframe.implementation.listeners;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.core.managers.RelicInventoryManager;
import me.voper.slimeframe.implementation.items.relics.RelicInventory;

public class RelicInventoryListener implements Listener {

    private final SlimeFrame plugin;
    private final RelicInventoryManager relicInventoryManager = SlimeFrame.getRelicInventoryManager();

    public RelicInventoryListener(SlimeFrame plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        RelicInventory relicInventory = relicInventoryManager.getRelicInventory(p);
        if (!e.getInventory().equals(relicInventory.getInventory())) return;

        Inventory clickedInventory = e.getClickedInventory();
        if (clickedInventory == null) return;

        if (clickedInventory.equals(relicInventory.getInventory())) {
            e.setCancelled(true);
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem == null || currentItem.getType() == Material.AIR) return;
            ItemStack clone = currentItem.clone();
            if (!e.isShiftClick()) clone.setAmount(1);
            int wanted = clone.getAmount();

            // Take out only what actually fit. Nothing used to be removed when part of a shift-clicked stack was
            // left over, so the part that did fit was duplicated. The slot is written back explicitly.
            HashMap<Integer, ItemStack> nonAddedItems = p.getInventory().addItem(clone);
            int moved = wanted - nonAddedItems.values().stream().mapToInt(ItemStack::getAmount).sum();
            if (moved > 0) {
                int rest = currentItem.getAmount() - moved;
                if (rest > 0) {
                    ItemStack remaining = currentItem.clone();
                    remaining.setAmount(rest);
                    clickedInventory.setItem(e.getSlot(), remaining);
                } else {
                    clickedInventory.setItem(e.getSlot(), null);
                }
            }
            if (moved < wanted) p.sendMessage(ChatColor.RED + "Your inventory is full!");
            return;
        }

        if (e.isShiftClick()) e.setCancelled(true);
    }

    // The relic inventory only gives relics out. Dragging used to bypass the click guard, so any item could be
    // stored there (a 54-slot backpack that opens anywhere and is kept on death).
    @EventHandler(ignoreCancelled = true)
    public void onInventoryDrag(InventoryDragEvent e) {
        if (!(e.getWhoClicked() instanceof Player p)) return;
        Inventory relicInventory = relicInventoryManager.getRelicInventory(p).getInventory();
        if (!e.getInventory().equals(relicInventory)) return;
        int topSize = e.getView().getTopInventory().getSize();
        for (int rawSlot : e.getRawSlots()) {
            if (rawSlot < topSize) {
                e.setCancelled(true);
                return;
            }
        }
    }

}
