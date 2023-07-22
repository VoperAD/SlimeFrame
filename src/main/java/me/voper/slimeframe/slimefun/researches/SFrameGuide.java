//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package me.voper.slimeframe.slimefun.researches;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.items.groups.FlexItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.groups.LockedItemGroup;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.recipes.RecipeType;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.attributes.RecipeDisplayItem;
import io.github.thebusybiscuit.slimefun4.core.guide.GuideHistory;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuide;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideImplementation;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideMode;
import io.github.thebusybiscuit.slimefun4.core.guide.options.SlimefunGuideSettings;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlock;
import io.github.thebusybiscuit.slimefun4.core.multiblocks.MultiBlockMachine;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.implementation.tasks.AsyncRecipeChoiceTask;
import io.github.thebusybiscuit.slimefun4.libraries.commons.lang.Validate;
import io.github.thebusybiscuit.slimefun4.libraries.dough.chat.ChatInput;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.CustomItemStack;
import io.github.thebusybiscuit.slimefun4.libraries.dough.items.ItemUtils;
import io.github.thebusybiscuit.slimefun4.libraries.dough.recipes.MinecraftRecipe;
import io.github.thebusybiscuit.slimefun4.utils.ChatUtils;
import io.github.thebusybiscuit.slimefun4.utils.ChestMenuUtils;
import io.github.thebusybiscuit.slimefun4.utils.itemstack.SlimefunGuideItem;
import me.mrCookieSlime.CSCoreLibPlugin.general.Inventory.ChestMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.Tag;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;
import org.bukkit.inventory.RecipeChoice;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.logging.Level;

@SuppressWarnings("deprecation")
// Waiting for the Slimefun Guide rewrite
public class SFrameGuide implements SlimefunGuideImplementation {
    private static final int MAX_ITEM_GROUPS = 36;
    private static final Sound sound;
    private final int[] recipeSlots = new int[]{3, 4, 5, 12, 13, 14, 21, 22, 23};
    private final ItemStack item;
    private final boolean showVanillaRecipes;
    private final boolean showHiddenItemGroupsInSearch;

    public SFrameGuide() {
        this.showVanillaRecipes = Slimefun.getCfg().getBoolean("guide.show-vanilla-recipes");
        this.showHiddenItemGroupsInSearch = Slimefun.getCfg().getBoolean("guide.show-hidden-item-groups-in-search");
        this.item = new SlimefunGuideItem(this, "&aSlimefun Guide &7(Chest GUI)");
    }

    @Nonnull
    public Sound getSound() {
        return sound;
    }

    @Nonnull
    public SlimefunGuideMode getMode() {
        return SlimefunGuideMode.SURVIVAL_MODE;
    }

    @Nonnull
    public ItemStack getItem() {
        return this.item;
    }

    protected final boolean isSurvivalMode() {
        return this.getMode() != SlimefunGuideMode.CHEAT_MODE;
    }

    @Nonnull
    protected List<ItemGroup> getVisibleItemGroups(@Nonnull Player p, @Nonnull PlayerProfile profile) {
        List<ItemGroup> groups = new LinkedList<>();
        Iterator<ItemGroup> iterator = Slimefun.getRegistry().getAllItemGroups().iterator();

        while(iterator.hasNext()) {
            ItemGroup group = iterator.next();

            try {
                if (group instanceof FlexItemGroup) {
                    if (((FlexItemGroup)group).isVisible(p, profile, this.getMode())) {
                        groups.add(group);
                    }
                } else if (!group.isHidden(p)) {
                    groups.add(group);
                }
            } catch (LinkageError | Exception ex) {
                SlimefunAddon addon = group.getAddon();
                if (addon != null) {
                    addon.getLogger().log(Level.SEVERE, ex, () -> "Could not display item group: " + group);
                } else {
                    Slimefun.logger().log(Level.SEVERE, ex, () -> "Could not display item group: " + group);
                }
            }
        }

        return groups;
    }

    public void openMainMenu(PlayerProfile profile, int page) {
        Player p = profile.getPlayer();
        if (p != null) {
            if (this.isSurvivalMode()) {
                GuideHistory history = profile.getGuideHistory();
                history.clear();
                history.setMainMenuPage(page);
            }

            ChestMenu menu = this.create(p);
            List<ItemGroup> itemGroups = this.getVisibleItemGroups(p, profile);
            int index = 9;
            this.createHeader(p, profile, menu);

            int target;
            for(target = 36 * (page - 1) - 1; target < itemGroups.size() - 1 && index < 45; ++index) {
                ++target;
                ItemGroup group = (ItemGroup)itemGroups.get(target);
                this.showItemGroup(menu, p, profile, group, index);
            }

            int pages = target == itemGroups.size() - 1 ? page : (itemGroups.size() - 1) / 36 + 1;
            menu.addItem(46, ChestMenuUtils.getPreviousButton(p, page, pages));
            menu.addMenuClickHandler(46, (pl, slot, item, action) -> {
                int next = page - 1;
                if (next != page && next > 0) {
                    this.openMainMenu(profile, next);
                }

                return false;
            });
            menu.addItem(52, ChestMenuUtils.getNextButton(p, page, pages));
            menu.addMenuClickHandler(52, (pl, slot, item, action) -> {
                int next = page + 1;
                if (next != page && next <= pages) {
                    this.openMainMenu(profile, next);
                }

                return false;
            });
            menu.open(new Player[]{p});
        }
    }

    private void showItemGroup(ChestMenu menu, Player p, PlayerProfile profile, ItemGroup group, int index) {
        if (group instanceof LockedItemGroup && this.isSurvivalMode() && !((LockedItemGroup)group).hasUnlocked(p, profile)) {
            List<String> lore = new ArrayList<>();
            lore.add("");
            Iterator iterator = Slimefun.getLocalization().getMessages(p, "guide.locked-itemgroup").iterator();

            while(iterator.hasNext()) {
                String line = (String)iterator.next();
                lore.add(ChatColor.WHITE + line);
            }

            lore.add("");
            iterator = ((LockedItemGroup)group).getParents().iterator();

            while(iterator.hasNext()) {
                ItemGroup parent = (ItemGroup)iterator.next();
                lore.add(parent.getItem(p).getItemMeta().getDisplayName());
            }

            menu.addItem(index, new CustomItemStack(Material.BARRIER, "&4" + Slimefun.getLocalization().getMessage(p, "guide.locked") + " &7- &f" + group.getItem(p).getItemMeta().getDisplayName(), (String[])lore.toArray(new String[0])));
            menu.addMenuClickHandler(index, ChestMenuUtils.getEmptyClickHandler());
        } else {
            menu.addItem(index, group.getItem(p));
            menu.addMenuClickHandler(index, (pl, slot, item, action) -> {
                this.openItemGroup(profile, group, 1);
                return false;
            });
        }

    }

    @ParametersAreNonnullByDefault
    public void openItemGroup(PlayerProfile profile, ItemGroup itemGroup, int page) {
        Player p = profile.getPlayer();
        if (p != null) {
            if (itemGroup instanceof FlexItemGroup) {
                ((FlexItemGroup)itemGroup).open(p, profile, this.getMode());
            } else {
                if (this.isSurvivalMode()) {
                    profile.getGuideHistory().add(itemGroup, page);
                }

                ChestMenu menu = this.create(p);
                this.createHeader(p, profile, menu);
                this.addBackButton(menu, 1, p, profile);
                int pages = (itemGroup.getItems().size() - 1) / 36 + 1;
                menu.addItem(46, ChestMenuUtils.getPreviousButton(p, page, pages));
                menu.addMenuClickHandler(46, (pl, slot, item, action) -> {
                    int next = page - 1;
                    if (next != page && next > 0) {
                        this.openItemGroup(profile, itemGroup, next);
                    }

                    return false;
                });
                menu.addItem(52, ChestMenuUtils.getNextButton(p, page, pages));
                menu.addMenuClickHandler(52, (pl, slot, item, action) -> {
                    int next = page + 1;
                    if (next != page && next <= pages) {
                        this.openItemGroup(profile, itemGroup, next);
                    }

                    return false;
                });
                int index = 9;
                int itemGroupIndex = 36 * (page - 1);

                for(int i = 0; i < 36; ++i) {
                    int target = itemGroupIndex + i;
                    if (target >= itemGroup.getItems().size()) {
                        break;
                    }

                    SlimefunItem sfitem = itemGroup.getItems().get(target);
                    if (!sfitem.isDisabledIn(p.getWorld())) {
                        this.displaySlimefunItem(menu, itemGroup, p, profile, sfitem, page, index);
                        ++index;
                    }
                }

                menu.open(p);
            }
        }
    }

    private void displaySlimefunItem(ChestMenu menu, ItemGroup itemGroup, Player p, PlayerProfile profile, SlimefunItem sfitem, int page, int index) {
        Research research = sfitem.getResearch();
        if (this.isSurvivalMode() && !hasPermission(p, sfitem)) {
            List<String> message = Slimefun.getPermissionsService().getLore(sfitem);
            menu.addItem(index, new CustomItemStack(ChestMenuUtils.getNoPermissionItem(), sfitem.getItemName(), message.toArray(new String[0])));
            menu.addMenuClickHandler(index, ChestMenuUtils.getEmptyClickHandler());
        } else if (this.isSurvivalMode() && research != null && !profile.hasUnlocked(research)) {
            if (research instanceof MasteryResearch masteryResearch) {
                menu.addItem(index, new CustomItemStack(ChestMenuUtils.getNotResearchedItem(), ChatColor.WHITE + ItemUtils.getItemName(sfitem.getItem()), "&4&l" + Slimefun.getLocalization().getMessage(p, "guide.locked"),
                        "",
                        "&a> Click to unlock",
                        "",
                        "&7Cost: &b" + research.getCost() + " Level(s)",
                        "&7Required: &bMastery Level " + masteryResearch.getMasteryLevelRequired()
                ));
            } else {
                menu.addItem(index, new CustomItemStack(ChestMenuUtils.getNotResearchedItem(), ChatColor.WHITE + ItemUtils.getItemName(sfitem.getItem()), "&4&l" + Slimefun.getLocalization().getMessage(p, "guide.locked"),
                        "",
                        "&a> Click to unlock",
                        "",
                        "&7Cost: &b" + research.getCost() + " Level(s)"
                ));
            }
            menu.addMenuClickHandler(index, (pl, slot, item, action) -> {
                research.unlockFromGuide(this, p, profile, sfitem, itemGroup, page);
                return false;
            });
        } else {
            menu.addItem(index, sfitem.getItem());
            menu.addMenuClickHandler(index, (pl, slot, item, action) -> {
                try {
                    if (this.isSurvivalMode()) {
                        this.displayItem(profile, sfitem, true);
                    } else if (pl.hasPermission("slimefun.cheat.items")) {
                        if (sfitem instanceof MultiBlockMachine) {
                            Slimefun.getLocalization().sendMessage(pl, "guide.cheat.no-multiblocks");
                        } else {
                            ItemStack clonedItem = sfitem.getItem().clone();
                            if (action.isShiftClicked()) {
                                clonedItem.setAmount(clonedItem.getMaxStackSize());
                            }

                            pl.getInventory().addItem(clonedItem);
                        }
                    } else {
                        Slimefun.getLocalization().sendMessage(pl, "messages.no-permission", true);
                    }
                } catch (LinkageError | Exception var8) {
                    this.printErrorMessage(pl, var8);
                }

                return false;
            });
        }

    }

    @ParametersAreNonnullByDefault
    public void openSearch(PlayerProfile profile, String input, boolean addToHistory) {
        Player p = profile.getPlayer();
        if (p != null) {
            ChestMenu menu = new ChestMenu(Slimefun.getLocalization().getMessage(p, "guide.search.inventory").replace("%item%", ChatUtils.crop(ChatColor.WHITE, input)));
            String searchTerm = input.toLowerCase(Locale.ROOT);
            if (addToHistory) {
                profile.getGuideHistory().add(searchTerm);
            }

            menu.setEmptySlotsClickable(false);
            this.createHeader(p, profile, menu);
            this.addBackButton(menu, 1, p, profile);
            int index = 9;
            Iterator<SlimefunItem> iterator = Slimefun.getRegistry().getEnabledSlimefunItems().iterator();

            while(iterator.hasNext()) {
                SlimefunItem slimefunItem = iterator.next();
                if (index == 44) {
                    break;
                }

                if (!slimefunItem.isHidden() && this.isItemGroupAccessible(p, slimefunItem) && this.isSearchFilterApplicable(slimefunItem, searchTerm)) {
                    ItemStack itemstack = new CustomItemStack(slimefunItem.getItem(), (meta) -> {
                        ItemGroup itemGroup = slimefunItem.getItemGroup();
                        meta.setLore(Arrays.asList("", ChatColor.DARK_GRAY + "⇨ " + ChatColor.WHITE + itemGroup.getDisplayName(p)));
                        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS);
                    });
                    menu.addItem(index, itemstack);
                    menu.addMenuClickHandler(index, (pl, slot, itm, action) -> {
                        try {
                            if (!this.isSurvivalMode()) {
                                pl.getInventory().addItem(new ItemStack[]{slimefunItem.getItem().clone()});
                            } else {
                                this.displayItem(profile, slimefunItem, true);
                            }
                        } catch (LinkageError | Exception var8) {
                            this.printErrorMessage(pl, var8);
                        }

                        return false;
                    });
                    ++index;
                }
            }

            menu.open(new Player[]{p});
        }
    }

    @ParametersAreNonnullByDefault
    private boolean isItemGroupAccessible(Player p, SlimefunItem slimefunItem) {
        return this.showHiddenItemGroupsInSearch || slimefunItem.getItemGroup().isAccessible(p);
    }

    @ParametersAreNonnullByDefault
    private boolean isSearchFilterApplicable(SlimefunItem slimefunItem, String searchTerm) {
        String itemName = ChatColor.stripColor(slimefunItem.getItemName()).toLowerCase(Locale.ROOT);
        return !itemName.isEmpty() && (itemName.equals(searchTerm) || itemName.contains(searchTerm));
    }

    @ParametersAreNonnullByDefault
    public void displayItem(PlayerProfile profile, ItemStack item, int index, boolean addToHistory) {
        Player p = profile.getPlayer();
        if (p != null && item != null && item.getType() != Material.AIR) {
            SlimefunItem sfItem = SlimefunItem.getByItem(item);
            if (sfItem != null) {
                this.displayItem(profile, sfItem, addToHistory);
            } else if (this.showVanillaRecipes) {
                Recipe[] recipes = Slimefun.getMinecraftRecipeService().getRecipesFor(item);
                if (recipes.length != 0) {
                    this.showMinecraftRecipe(recipes, index, item, profile, p, addToHistory);
                }
            }
        }
    }

    private void showMinecraftRecipe(Recipe[] recipes, int index, ItemStack item, PlayerProfile profile, Player p, boolean addToHistory) {
        Recipe recipe = recipes[index];
        ItemStack[] recipeItems = new ItemStack[9];
        RecipeType recipeType = RecipeType.NULL;
        ItemStack result = null;
        Optional<MinecraftRecipe<? super Recipe>> optional = MinecraftRecipe.of(recipe);
        AsyncRecipeChoiceTask task = new AsyncRecipeChoiceTask();
        if (optional.isPresent()) {
            this.showRecipeChoices(recipe, recipeItems, task);
            recipeType = new RecipeType((MinecraftRecipe)optional.get());
            result = recipe.getResult();
        } else {
            recipeItems = new ItemStack[]{null, null, null, null, new CustomItemStack(Material.BARRIER, "&4We are somehow unable to show you this Recipe :/", new String[0]), null, null, null, null};
        }

        ChestMenu menu = this.create(p);
        if (addToHistory) {
            profile.getGuideHistory().add(item, index);
        }

        this.displayItem(menu, profile, p, item, result, recipeType, recipeItems, task);
        if (recipes.length > 1) {
            for(int i = 27; i < 36; ++i) {
                menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
            }

            menu.addItem(28, ChestMenuUtils.getPreviousButton(p, index + 1, recipes.length), (pl, slot, action, stack) -> {
                if (index > 0) {
                    this.showMinecraftRecipe(recipes, index - 1, item, profile, p, true);
                }

                return false;
            });
            menu.addItem(34, ChestMenuUtils.getNextButton(p, index + 1, recipes.length), (pl, slot, action, stack) -> {
                if (index < recipes.length - 1) {
                    this.showMinecraftRecipe(recipes, index + 1, item, profile, p, true);
                }

                return false;
            });
        }

        menu.open(new Player[]{p});
        if (!task.isEmpty()) {
            task.start(menu.toInventory());
        }

    }

    private <T extends Recipe> void showRecipeChoices(T recipe, ItemStack[] recipeItems, AsyncRecipeChoiceTask task) {
        RecipeChoice[] choices = Slimefun.getMinecraftRecipeService().getRecipeShape(recipe);
        if (choices.length == 1 && choices[0] instanceof RecipeChoice.MaterialChoice) {
            recipeItems[4] = new ItemStack((Material)((RecipeChoice.MaterialChoice)choices[0]).getChoices().get(0));
            if (((RecipeChoice.MaterialChoice)choices[0]).getChoices().size() > 1) {
                task.add(this.recipeSlots[4], (RecipeChoice.MaterialChoice)choices[0]);
            }
        } else {
            for(int i = 0; i < choices.length; ++i) {
                if (choices[i] instanceof RecipeChoice.MaterialChoice) {
                    recipeItems[i] = new ItemStack((Material)((RecipeChoice.MaterialChoice)choices[i]).getChoices().get(0));
                    if (((RecipeChoice.MaterialChoice)choices[i]).getChoices().size() > 1) {
                        task.add(this.recipeSlots[i], (RecipeChoice.MaterialChoice)choices[i]);
                    }
                }
            }
        }

    }

    @ParametersAreNonnullByDefault
    public void displayItem(PlayerProfile profile, SlimefunItem item, boolean addToHistory) {
        Player p = profile.getPlayer();
        if (p != null) {
            ChestMenu menu = this.create(p);
            Optional<String> wiki = item.getWikipage();
            if (wiki.isPresent()) {
                menu.addItem(8, new CustomItemStack(Material.KNOWLEDGE_BOOK, ChatColor.WHITE + Slimefun.getLocalization().getMessage(p, "guide.tooltips.wiki"), "", ChatColor.GRAY + "⇨ " + ChatColor.GREEN + Slimefun.getLocalization().getMessage(p, "guide.tooltips.open-itemgroup")));
                menu.addMenuClickHandler(8, (pl, slot, itemstack, action) -> {
                    pl.closeInventory();
                    ChatUtils.sendURL(pl, wiki.get());
                    return false;
                });
            }

            AsyncRecipeChoiceTask task = new AsyncRecipeChoiceTask();
            if (addToHistory) {
                profile.getGuideHistory().add(item);
            }

            ItemStack result = item.getRecipeOutput();
            RecipeType recipeType = item.getRecipeType();
            ItemStack[] recipe = item.getRecipe();
            this.displayItem(menu, profile, p, item, result, recipeType, recipe, task);
            if (item instanceof RecipeDisplayItem) {
                this.displayRecipes(p, profile, menu, (RecipeDisplayItem)item, 0);
            }

            menu.open(p);
            if (!task.isEmpty()) {
                task.start(menu.toInventory());
            }

        }
    }

    private void displayItem(ChestMenu menu, PlayerProfile profile, Player p, Object item, ItemStack output, RecipeType recipeType, ItemStack[] recipe, AsyncRecipeChoiceTask task) {
        this.addBackButton(menu, 0, p, profile);
        ChestMenu.MenuClickHandler clickHandler = (pl, slot, itemstack, action) -> {
            try {
                if (itemstack != null && itemstack.getType() != Material.BARRIER) {
                    this.displayItem(profile, itemstack, 0, true);
                }
            } catch (LinkageError | Exception var7) {
                this.printErrorMessage(pl, var7);
            }

            return false;
        };
        boolean isSlimefunRecipe = item instanceof SlimefunItem;

        for(int i = 0; i < 9; ++i) {
            ItemStack recipeItem = getDisplayItem(p, isSlimefunRecipe, recipe[i]);
            menu.addItem(this.recipeSlots[i], recipeItem, clickHandler);
            if (recipeItem != null && item instanceof MultiBlockMachine) {
                Iterator iterator = MultiBlock.getSupportedTags().iterator();

                while(iterator.hasNext()) {
                    Tag<Material> tag = (Tag)iterator.next();
                    if (tag.isTagged(recipeItem.getType())) {
                        task.add(this.recipeSlots[i], tag);
                        break;
                    }
                }
            }
        }

        menu.addItem(10, recipeType.getItem(p), ChestMenuUtils.getEmptyClickHandler());
        menu.addItem(16, output, ChestMenuUtils.getEmptyClickHandler());
    }

    @ParametersAreNonnullByDefault
    public void createHeader(Player p, PlayerProfile profile, ChestMenu menu) {
        Validate.notNull(p, "The Player cannot be null!");
        Validate.notNull(profile, "The Profile cannot be null!");
        Validate.notNull(menu, "The Inventory cannot be null!");

        int i;
        for(i = 0; i < 9; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

        menu.addItem(1, ChestMenuUtils.getMenuButton(p));
        menu.addMenuClickHandler(1, (pl, slot, item, action) -> {
            SlimefunGuideSettings.openSettings(pl, pl.getInventory().getItemInMainHand());
            return false;
        });
        menu.addItem(7, ChestMenuUtils.getSearchButton(p));
        menu.addMenuClickHandler(7, (pl, slot, item, action) -> {
            pl.closeInventory();
            Slimefun.getLocalization().sendMessage(pl, "guide.search.message");
            ChatInput.waitForPlayer(Slimefun.instance(), pl, (msg) -> {
                SlimefunGuide.openSearch(profile, msg, this.getMode(), this.isSurvivalMode());
            });
            return false;
        });

        for(i = 45; i < 54; ++i) {
            menu.addItem(i, ChestMenuUtils.getBackground(), ChestMenuUtils.getEmptyClickHandler());
        }

    }

    private void addBackButton(ChestMenu menu, int slot, Player p, PlayerProfile profile) {
        GuideHistory history = profile.getGuideHistory();
        if (this.isSurvivalMode() && history.size() > 1) {
            menu.addItem(slot, new CustomItemStack(ChestMenuUtils.getBackButton(p, "", "&fLeft Click: &7Go back to previous Page", "&fShift + left Click: &7Go back to Main Menu")));
            menu.addMenuClickHandler(slot, (pl, s, is, action) -> {
                if (action.isShiftClicked()) {
                    this.openMainMenu(profile, profile.getGuideHistory().getMainMenuPage());
                } else {
                    history.goBack(this);
                }

                return false;
            });
        } else {
            menu.addItem(slot, new CustomItemStack(ChestMenuUtils.getBackButton(p, "", ChatColor.GRAY + Slimefun.getLocalization().getMessage(p, "guide.back.guide"))));
            menu.addMenuClickHandler(slot, (pl, s, is, action) -> {
                this.openMainMenu(profile, profile.getGuideHistory().getMainMenuPage());
                return false;
            });
        }

    }

    @ParametersAreNonnullByDefault
    @Nonnull
    private static ItemStack getDisplayItem(Player p, boolean isSlimefunRecipe, ItemStack item) {
        if (isSlimefunRecipe) {
            SlimefunItem slimefunItem = SlimefunItem.getByItem(item);
            if (slimefunItem == null) {
                return item;
            } else {
                String lore = hasPermission(p, slimefunItem) ? "&fNeeds to be unlocked in " + slimefunItem.getItemGroup().getDisplayName(p) : "&fNo Permission";
                return slimefunItem.canUse(p, false) ? item : new CustomItemStack(Material.BARRIER, ItemUtils.getItemName(item), "&4&l" + Slimefun.getLocalization().getMessage(p, "guide.locked"), "", lore);
            }
        } else {
            return item;
        }
    }

    @ParametersAreNonnullByDefault
    private void displayRecipes(Player p, PlayerProfile profile, ChestMenu menu, RecipeDisplayItem sfItem, int page) {
        List<ItemStack> recipes = sfItem.getDisplayRecipes();
        if (!recipes.isEmpty()) {
            menu.addItem(53, null);
            int i;
            if (page == 0) {
                for(i = 27; i < 36; ++i) {
                    menu.replaceExistingItem(i, new CustomItemStack(ChestMenuUtils.getBackground(), sfItem.getRecipeSectionLabel(p), new String[0]));
                    menu.addMenuClickHandler(i, ChestMenuUtils.getEmptyClickHandler());
                }
            }

            i = (recipes.size() - 1) / 18 + 1;
            menu.replaceExistingItem(28, ChestMenuUtils.getPreviousButton(p, page + 1, i));
            menu.addMenuClickHandler(28, (pl, slotx, itemstack, action) -> {
                if (page > 0) {
                    this.displayRecipes(pl, profile, menu, sfItem, page - 1);
                    pl.playSound(pl.getLocation(), sound, 1.0F, 1.0F);
                }

                return false;
            });
            menu.replaceExistingItem(34, ChestMenuUtils.getNextButton(p, page + 1, i));
            menu.addMenuClickHandler(34, (pl, slotx, itemstack, action) -> {
                if (recipes.size() > 18 * (page + 1)) {
                    this.displayRecipes(pl, profile, menu, sfItem, page + 1);
                    pl.playSound(pl.getLocation(), sound, 1.0F, 1.0F);
                }

                return false;
            });
            int inputs = 36;
            int outputs = 45;

            for(int j = 0; j < 18; ++j) {
                int slot;
                if (j % 2 == 0) {
                    slot = inputs++;
                } else {
                    slot = outputs++;
                }

                this.addDisplayRecipe(menu, profile, recipes, slot, j, page);
            }
        }

    }

    private void addDisplayRecipe(ChestMenu menu, PlayerProfile profile, List<ItemStack> recipes, int slot, int i, int page) {
        if (i + page * 18 < recipes.size()) {
            ItemStack displayItem = (ItemStack)recipes.get(i + page * 18);
            if (displayItem != null) {
                displayItem = displayItem.clone();
            }

            menu.replaceExistingItem(slot, displayItem);
            if (page == 0) {
                menu.addMenuClickHandler(slot, (pl, s, itemstack, action) -> {
                    this.displayItem(profile, itemstack, 0, true);
                    return false;
                });
            }
        } else {
            menu.replaceExistingItem(slot, (ItemStack)null);
            menu.addMenuClickHandler(slot, ChestMenuUtils.getEmptyClickHandler());
        }

    }

    @ParametersAreNonnullByDefault
    private static boolean hasPermission(Player p, SlimefunItem item) {
        return Slimefun.getPermissionsService().hasPermission(p, item);
    }

    @Nonnull
    private ChestMenu create(@Nonnull Player p) {
        ChestMenu menu = new ChestMenu(Slimefun.getLocalization().getMessage(p, "guide.title.main"));
        menu.setEmptySlotsClickable(false);
        menu.addMenuOpeningHandler((pl) -> {
            pl.playSound(pl.getLocation(), sound, 1.0F, 1.0F);
        });
        return menu;
    }

    @ParametersAreNonnullByDefault
    private void printErrorMessage(Player p, Throwable x) {
        p.sendMessage(ChatColor.DARK_RED + "An internal server error has occurred. Please inform an admin, check the console for further info.");
        Slimefun.logger().log(Level.SEVERE, "An error has occurred while trying to open a SlimefunItem in the guide!", x);
    }

    static {
        sound = Sound.ITEM_BOOK_PAGE_TURN;
    }
}
