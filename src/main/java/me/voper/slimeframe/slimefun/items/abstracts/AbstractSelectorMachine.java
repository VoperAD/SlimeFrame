package me.voper.slimeframe.slimefun.items.abstracts;

import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItemStack;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenu;
import me.mrCookieSlime.Slimefun.api.inventory.BlockMenuPreset;
import me.voper.slimeframe.slimefun.items.machines.MachineDesign;
import me.voper.slimeframe.utils.MachineUtils;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public abstract class AbstractSelectorMachine extends AbstractProcessorMachine {

    protected AbstractSelectorMachine(ItemGroup itemGroup, SlimefunItemStack item, RecipeType recipeType, ItemStack[] recipe) {
        super(itemGroup, item, recipeType, recipe);
    }

    @Override
    protected void createMenu(BlockMenuPreset preset) {
        super.createMenu(preset);
        preset.addItem(getSelectorSlot(), MachineUtils.SELECTOR, ChestMenuUtils.getEmptyClickHandler());
    }

    @Override
    protected void onNewInstance(BlockMenu menu, Block b) {
        super.onNewInstance(menu, b);
        menu.addMenuClickHandler(getSelectorSlot(), onSelectorClick(menu));
    }

    @SuppressWarnings("deprecation")
    protected abstract ChestMenu.MenuClickHandler onSelectorClick(BlockMenu menu);

    public int getSelectorSlot() {
        return MachineDesign.SELECTOR_MACHINE.selectorSlot();
    }

}
