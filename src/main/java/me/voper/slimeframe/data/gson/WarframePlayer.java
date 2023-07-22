package me.voper.slimeframe.data.gson;

import lombok.Data;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.UUID;

@Data
@ParametersAreNonnullByDefault
public class WarframePlayer implements Comparable<WarframePlayer> {

    private final UUID uuid;
    private final String playerName;
    private int masteryLevel;
    private int masteryExp;

    public WarframePlayer(UUID uuid, String playerName, int masteryLevel, int masteryExp) {
        this.uuid = uuid;
        this.playerName = playerName;
        this.masteryLevel = masteryLevel;
        this.masteryExp = masteryExp;
    }

    public WarframePlayer(UUID uuid, String playerName) {
        this(uuid, playerName, 0, 0);
    }

    public WarframePlayer(Player player) {
        this(player.getUniqueId(), player.getName(), 0, 0);
    }

    public void incrementLevel() {
        this.masteryLevel += 1;
    }

    public void incrementExperience(int value) {
        this.masteryExp += value;
        // TODO if value > exp required to level up, level up
    }

    public void reset() {
        setMasteryExp(0);
        setMasteryLevel(0);
    }

    @Override
    public int compareTo(@Nonnull WarframePlayer o) {
        return masteryLevel == o.masteryLevel ?
                masteryExp - o.masteryExp :
                masteryLevel - o.masteryLevel;
    }
}
