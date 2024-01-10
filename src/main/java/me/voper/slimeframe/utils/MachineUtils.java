package me.voper.slimeframe.utils;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.md_5.bungee.api.ChatColor;

@ParametersAreNonnullByDefault
public final class MachineUtils {

    public static final ItemStack NO_SPACE = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "No space!");
    public static final ItemStack NO_ENERGY = new CustomItemStack(Material.BARRIER, ChatColor.DARK_RED + "There isn't enough energy!");
    public static final ItemStack FAILED = new CustomItemStack(Material.RED_STAINED_GLASS_PANE, ChatColor.DARK_RED + "Failed.");
    public static final ItemStack WAITING = new CustomItemStack(Material.ORANGE_STAINED_GLASS_PANE, ChatColor.GOLD + "Waiting...");
    public static final ItemStack RUNNING = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Running...");
    public static final ItemStack STATUS = new CustomItemStack(Material.PURPLE_STAINED_GLASS_PANE, ChatColor.DARK_PURPLE + "Status");
    public static final ItemStack INVALID_RECIPE = new CustomItemStack(Material.BARRIER, ChatColor.DARK_RED + "Invalid recipe!");
    public static final ItemStack FINISHED = new CustomItemStack(Material.GREEN_STAINED_GLASS_PANE, ChatColor.GREEN + "Finished!");
    public static final ItemStack SELECTOR = new CustomItemStack(SlimefunItems.RAINBOW_RUNE, ChatColor.WHITE + "Selector");


    public static void replaceExistingItemViewer(BlockMenu menu, int slot, ItemStack item) {
        if (!menu.hasViewer()) return;
        menu.replaceExistingItem(slot, item);
    }

    public static void replaceExistingItemViewer(BlockMenu menu, int[] slots, ItemStack item) {
        for (int slot : slots) {
            replaceExistingItemViewer(menu, slot, item);
        }
    }


}
