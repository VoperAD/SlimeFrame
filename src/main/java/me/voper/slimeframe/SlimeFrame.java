package me.voper.slimeframe;

import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import me.voper.slimeframe.listeners.*;
import me.voper.slimeframe.managers.CommandsManager;
import me.voper.slimeframe.managers.RelicInventoryManager;
import me.voper.slimeframe.managers.SettingsManager;
import me.voper.slimeframe.managers.SupportedPluginManager;
import me.voper.slimeframe.slimefun.SFrameItems;
import me.voper.slimeframe.slimefun.datatypes.MerchantRecipeListDataType;
import me.voper.slimeframe.slimefun.researches.Researches;
import me.voper.slimeframe.tasks.ArmorMonitorTask;
import me.voper.slimeframe.tasks.CoolantRaknoidsTask;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import org.geysermc.geyser.api.GeyserApi;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class SlimeFrame extends JavaPlugin implements SlimefunAddon {

    private static SlimeFrame instance;

    private SettingsManager settingsManager;
    private RelicInventoryManager relicInventoryManager;
    private CommandsManager commandsManager;
    private SupportedPluginManager supportedPluginManager;
    private GeyserApi geyserApi;

    private final Set<SlimefunItem> freezingItems = new HashSet<>();
    private final Set<Block> placedBlocks = new HashSet<>();

    static {
        ConfigurationSerialization.registerClass(MerchantRecipeListDataType.SerializableSlimefunItemStack.class);
    }

    @Override
    public void onEnable() {
        instance = this;

        this.supportedPluginManager = new SupportedPluginManager();

        if (supportedPluginManager.isGeyser()) {
            geyserApi = GeyserApi.api();
        }

        this.settingsManager = new SettingsManager(this);
        this.relicInventoryManager = new RelicInventoryManager(this);
        this.commandsManager = new CommandsManager(this);

        this.relicInventoryManager.setup();
        this.commandsManager.setup();

        new SFrameItems(this).setup();

        Researches.setup();

        this.setupEvents();
        this.startTasks();
        this.logStart();
    }

    @Override
    public void onDisable() {
        relicInventoryManager.saveConfig();
        Bukkit.getScheduler().cancelTasks(this);
    }

    private void logStart() {
        getLogger().info("   _____ _ _                ______                        ");
        getLogger().info("  / ____| (_)              |  ____|                       ");
        getLogger().info(" | (___ | |_ _ __ ___   ___| |__ _ __ __ _ _ __ ___   ___ ");
        getLogger().info("  \\___ \\| | | '_ ` _ \\ / _ \\  __| '__/ _` | '_ ` _ \\ / _ \\");
        getLogger().info("  ____) | | | | | | | |  __/ |  | | | (_| | | | | | |  __/");
        getLogger().info(" |_____/|_|_|_| |_| |_|\\___|_|  |_|  \\__,_|_| |_| |_|\\___|");
        getLogger().info("==========================================================");
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/VoperAD/SlimeFrame/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    private void setupEvents() {
        new BlockPlaceListener(this);
        new RelicsListener(this);
        new RelicInventoryListener(this);
        new CoolantRaknoidsListener(this);
        new AdvancedMobDropListener(this);
        new VillagerTradeListener(this);
    }

    private void startTasks() {
        new ArmorMonitorTask(this);
        new CoolantRaknoidsTask(this);
    }

    public static SlimeFrame getInstance() {
        return instance;
    }

    public static SettingsManager getSettingsManager() {
        return instance.settingsManager;
    }

    public static RelicInventoryManager getRelicInventoryManager() {
        return instance.relicInventoryManager;
    }

    @Contract(pure = true)
    public static @Nullable GeyserApi getGeyserApi() {
        return instance.supportedPluginManager.isGeyser() ? instance.geyserApi : null;
    }

    public static SupportedPluginManager getSupportedPluginManager() {
        return instance.supportedPluginManager;
    }

    public static Set<SlimefunItem> getFreezingItems() { return instance.freezingItems; }

    public static Set<Block> getPlacedBlocks() { return instance.placedBlocks; }

}
