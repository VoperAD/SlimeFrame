package me.voper.slimeframe.slimefun.items.machines;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.voper.slimeframe.slimefun.items.abstracts.AbstractMachine;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public class PotionsFactory extends AbstractMachine {

    protected PotionsFactory(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected boolean process(BlockMenu menu, Block b) {
        return false;
    }

    @Override
    protected boolean checkCraftConditions(BlockMenu menu) {
        Material type = menu.getBlock().getRelative(BlockFace.DOWN).getType();
        return type == Material.WATER_CAULDRON || type == Material.WATER;
    }

    @Override
    protected void onNewInstance(@Nonnull BlockMenu menu, @Nonnull Block b) {

    }

    @Override
    protected void createMenu(@Nonnull BlockMenuPreset preset) {

    }

    @Nonnull
    @Override
    public int[] getInputSlots() {
        return new int[0];
    }

    @Nonnull
    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }

    @Override
    public int getStatusSlot() {
        return 0;
    }

}
