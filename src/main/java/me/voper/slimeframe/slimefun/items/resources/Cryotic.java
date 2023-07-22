package me.voper.slimeframe.slimefun.items.resources;

import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.NotPlaceable;
import me.voper.slimeframe.slimefun.SFrameStacks;
import me.voper.slimeframe.slimefun.attributes.FreezingItem;
import me.voper.slimeframe.slimefun.groups.Groups;
import me.voper.slimeframe.slimefun.items.machines.CryoticExtractor;
import me.voper.slimeframe.utils.Utils;

public class Cryotic extends SlimefunItem implements NotPlaceable, FreezingItem {

    public Cryotic() {
        super(Groups.RESOURCES, SFrameStacks.CRYOTIC, CryoticExtractor.RECIPE_TYPE, Utils.NULL_ITEMS_ARRAY);
        this.disenchantable = false;
        this.enchantable = false;
    }

    @Override
    public double getFreezingDamage() {
        return 3;
    }
}
