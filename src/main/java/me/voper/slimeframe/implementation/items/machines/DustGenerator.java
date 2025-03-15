package me.voper.slimeframe.implementation.items.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import io.github.thebusybiscuit.slimefun4.libraries.dough.data.persistent.PersistentDataAPI;

import me.voper.slimeframe.utils.Keys;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.implementation.SlimefunItems;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;

import me.voper.slimeframe.implementation.items.abstracts.AbstractSelectorMachine;
import me.voper.slimeframe.utils.MachineUtils;

import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import net.md_5.bungee.api.ChatColor;

import org.bukkit.inventory.meta.ItemMeta;

public class DustGenerator extends AbstractSelectorMachine implements RecipeDisplayItem {

    private static final String BLOCK_KEY = "dust_selector";
    private static final Map<String, ItemStack> OUTPUT_MAPPER;
    private static final List<ItemStack> DUSTS = new ArrayList<>(List.of(
            MachineUtils.SELECTOR,
            MachineUtils.selectorItem(SlimefunItems.ALUMINUM_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.COPPER_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.GOLD_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.IRON_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.LEAD_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.MAGNESIUM_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.SILVER_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.TIN_DUST.item()),
            MachineUtils.selectorItem(SlimefunItems.ZINC_DUST.item())
    ));

    static {
        OUTPUT_MAPPER = new HashMap<>();
        DUSTS.subList(1, DUSTS.size()).forEach(item -> {
            ItemStack clone = item.clone();
            ItemMeta itemMeta = clone.getItemMeta();
            PersistentDataAPI.remove(itemMeta, Keys.createKey("mark_wf"));
            clone.setItemMeta(itemMeta);
            OUTPUT_MAPPER.put(item.getItemMeta().getDisplayName(), clone);
        });
    }

    public DustGenerator(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
        this.outputAmount = 2;
    }

    @Override
    protected ItemStack getProgressBar() {
        return new ItemStack(Material.STONE_PICKAXE);
    }

    @Override
    public void postRegister() {
        registerRecipe(1, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.REDSTONE));
        registerRecipe(1, new ItemStack(Material.COBBLED_DEEPSLATE), new ItemStack(Material.REDSTONE));
    }

    @Nonnull
    @Override
    public List<ItemStack> selectionList() {
        return DUSTS;
    }

    @Nonnull
    @Override
    public String getBlockKey() {
        return BLOCK_KEY;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        return menu.getItemInSlot(getSelectorSlot()).getType() != Material.FIREWORK_STAR;
    }

    @Override
    protected void onCraftConditionsNotMet(BlockMenu menu) {
        MachineUtils.replaceExistingItemViewer(menu, getStatusSlot(), CustomItemStack.create(Material.BARRIER, ChatColor.RED + "Select a dust to generate!"));
    }

    @Nonnull
    @Override
    protected ItemStack getOutput(@Nonnull ItemStack item) {
        return OUTPUT_MAPPER.get(item.getItemMeta().getDisplayName());
    }

    @Nonnull
    @Override
    public List<ItemStack> getDisplayRecipes() {
        List<ItemStack> dusts = new ArrayList<>(DUSTS);
        dusts.remove(MachineUtils.SELECTOR);

        List<ItemStack> displayRecipes = new ArrayList<>();
        for (ItemStack dust : dusts) {
            ItemStack clone = dust.clone();
            clone.setAmount(production * outputAmount);
            displayRecipes.add(new ItemStack(Material.COBBLESTONE));
            displayRecipes.add(clone);
            displayRecipes.add(new ItemStack(Material.COBBLED_DEEPSLATE));
            displayRecipes.add(clone);
        }

        return displayRecipes;
    }
}
