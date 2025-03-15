package me.voper.slimeframe.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.ParametersAreNonnullByDefault;

import org.bukkit.inventory.ItemStack;

import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@ParametersAreNonnullByDefault
public class RandomItemStacks {

    @Getter
    private final List<ItemStack> items = new ArrayList<>();

    public RandomItemStacks(ItemStack[] itemStacks) {
        this.items.addAll(List.of(itemStacks));
    }

    public RandomItemStacks(RandomItemStacks[] randomItemStacksArray) {
        for (RandomItemStacks randomItemStack : randomItemStacksArray) {
            this.items.addAll(randomItemStack.items);
        }
    }

    public ItemStack getRandom() {
        Validate.notEmpty(items, "Items list must not be empty.");
        return items.size() == 1 ? items.get(0) : items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }

    public void add(ItemStack item) {
        this.items.add(item);
    }

}
