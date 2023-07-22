package me.voper.slimeframe.managers;

import co.aikar.commands.PaperCommandManager;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.commands.Commands;
import me.voper.slimeframe.slimefun.items.relics.Relic;

import java.util.Arrays;

public class CommandsManager extends PaperCommandManager {

    private final SlimeFrame plugin;
    private final SettingsManager sm;

    public CommandsManager(SlimeFrame plugin) {
        super(plugin);
        this.plugin = plugin;
        this.sm = SlimeFrame.getSettingsManager();
    }

    public void setup() {
        this.enableUnstableAPI("help");
        this.registerCommandReplacements();
        this.registerDependencies();
        this.registerCommandCompletions();
        this.registerCommand(new Commands());
    }

    private void registerDependencies() {
        this.registerDependency(SettingsManager.class, sm);
        this.registerDependency(RelicInventoryManager.class, SlimeFrame.getRelicInventoryManager());
    }

    private void registerCommandReplacements() {
        this.getCommandReplacements().addReplacements(
                "slimeframe", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.SLIMEFRAME_CMD)),
//                "info", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.INFO_CMD)),
//                "top", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.TOP_CMD)),
//                "gpoints", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.GIVEPOINTS_CMD)),
//                "rmpoints", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.REMOVEPOINTS_CMD)),
//                "rspoints", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.RESETPOINTS_CMD)),
                "inventory", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.INVENTORY_CMD)),
                "invsee", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.INVSEE_CMD)),
                "refine", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.REFINE_CMD)),
                "traces", String.join("|", this.sm.getStringList(SettingsManager.ConfigField.TRACES_CMD))
        );
    }

    private void registerCommandCompletions() {
        this.getCommandCompletions().registerCompletion("refinement", c -> Arrays.stream(Relic.Refinement.values()).map(Enum::name).toList());
    }

}
