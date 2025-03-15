package me.voper.slimeframe.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.md_5.bungee.api.ChatColor;

@ParametersAreNonnullByDefault
public final class MachineUtils {

    public static final ItemStack NO_SPACE = CustomItemStack.create(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "No space!");
    public static final ItemStack NO_ENERGY = CustomItemStack.create(Material.BARRIER, ChatColor.DARK_RED + "There isn't enough energy!");
    public static final ItemStack FAILED = CustomItemStack.create(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "Failed.");
    public static final ItemStack WAITING = CustomItemStack.create(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GOLD + "Waiting...");
    public static final ItemStack RUNNING = CustomItemStack.create(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Running...");
    public static final ItemStack STATUS = CustomItemStack.create(Material.PURPLE_STAINED_GLASS_PANE, ChatColor.DARK_PURPLE + "Status");
    public static final ItemStack INVALID_RECIPE = CustomItemStack.create(Material.BARRIER, ChatColor.DARK_RED + "Invalid recipe!");
    public static final ItemStack FINISHED = CustomItemStack.create(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Finished!");
    public static final ItemStack SELECTOR = CustomItemStack.create(SlimefunItems.RAINBOW_RUNE.item(), ChatColor.WHITE + "Selector");


    public static void replaceExistingItemViewer(BlockMenu menu, int slot, ItemStack item) {
        if (!menu.hasViewer()) return;
        menu.replaceExistingItem(slot, item);
    }

    public static void replaceExistingItemViewer(BlockMenu menu, int[] slots, ItemStack item) {
        for (int slot : slots) {
            replaceExistingItemViewer(menu, slot, item);
        }
    }

    public static ItemStack selectorItem(Material material) {
        return CustomItemStack.create(material, meta -> {
            PersistentDataAPI.set(meta, Keys.createKey("mark_wf"), PersistentDataType.BYTE, (byte) 1);
        });
    }

    public static ItemStack selectorItem(ItemStack item) {
        return CustomItemStack.create(item, meta -> {
            PersistentDataAPI.set(meta, Keys.createKey("mark_wf"), PersistentDataType.BYTE, (byte) 1);
        });
    }

}
