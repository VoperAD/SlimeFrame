package me.voper.slimeframe.slimefun.researches;

import io.github.thebusybiscuit.slimefun4.api.events.PlayerPreResearchEvent;
import io.github.thebusybiscuit.slimefun4.api.items.ItemGroup;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.api.player.PlayerProfile;
import io.github.thebusybiscuit.slimefun4.api.researches.Research;
import io.github.thebusybiscuit.slimefun4.core.guide.SlimefunGuideImplementation;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import lombok.Getter;
import me.voper.slimeframe.SlimeFrame;
import me.voper.slimeframe.managers.SettingsManager;
import me.voper.slimeframe.utils.ChatUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@Getter
@ParametersAreNonnullByDefault
public class MasteryResearch extends Research {

    private final int masteryLevelRequired;
    private final int masteryExpReward;

    public MasteryResearch(@Nonnull NamespacedKey key, int id, @Nonnull String defaultName, int defaultCost, int masteryLevelRequired, int masteryExpReward) {
        super(key, id, defaultName, defaultCost);
        this.masteryLevelRequired = masteryLevelRequired;
        this.masteryExpReward = masteryExpReward;
    }

    @Override
    public boolean canUnlock(@Nonnull Player p) {
        if (!this.isEnabled()) {
            return true;
        } else {
            boolean creativeResearch = p.getGameMode() == GameMode.CREATIVE && Slimefun.getRegistry().isFreeCreativeResearchingEnabled();
            return creativeResearch || (SlimeFrame.getWarframeDataManager().getWarframePlayer(p).getMasteryLevel() >= getMasteryLevelRequired() && p.getLevel() >= this.getCost());
        }
    }

    @Override
    public void unlockFromGuide(SlimefunGuideImplementation guide, Player player, PlayerProfile profile, SlimefunItem sfItem, ItemGroup itemGroup, int page) {
        if (!Slimefun.getRegistry().getCurrentlyResearchingPlayers().contains(player.getUniqueId())) {
            if (profile.hasUnlocked(this)) {
                guide.openItemGroup(profile, itemGroup, page);
            } else {
                PlayerPreResearchEvent event = new PlayerPreResearchEvent(player, this, sfItem);
                Bukkit.getPluginManager().callEvent(event);
                if (!event.isCancelled()) {
                    if (this.canUnlock(player)) {
                        guide.unlockItem(player, sfItem, (pl) -> {
                            guide.openItemGroup(profile, itemGroup, page);
                        });
                    } else {

                        if (player.getLevel() < this.getCost()) {
                            Slimefun.getLocalization().sendMessage(player, "messages.not-enough-xp", true);
                        }

                        if (SlimeFrame.getWarframeDataManager().getWarframePlayer(player).getMasteryLevel() < masteryLevelRequired) {
                            List<String> stringList = SlimeFrame.getSettingsManager().getStringList(SettingsManager.ConfigField.NO_MASTERY_LEVEL);
                            stringList.forEach(string -> {
                                string = string.replaceAll("%mastery%", String.valueOf(masteryLevelRequired));
                                ChatUtils.sendMessage(player, string);
                            });
                        }

                    }
                }
            }
        }
    }
}
