package me.voper.slimeframe.implementation.items.relics;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.voper.slimeframe.implementation.SFrameStacks;
import me.voper.slimeframe.utils.ChatUtils;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.chat.hover.content.Text;

@Getter
public class RelicInventory {

    public static final int SIZE = 54;
    private final Inventory inventory;
    private final UUID owner;

    public RelicInventory(@Nonnull Player owner) {
        this.owner = owner.getUniqueId();
        this.inventory = Bukkit.createInventory(owner, SIZE, ChatColor.WHITE + owner.getName() + "'s Relics Inventory");
    }

    public void addRelic(@Nonnull RelicItemStack relic) {
        HashMap<Integer, ItemStack> result = inventory.addItem(relic);

        Player player = Bukkit.getPlayer(owner);
        if (player == null) return;

        if (result.isEmpty()) {
            BaseComponent[] baseComponents = new ComponentBuilder("You have just received a ").color(ChatColor.GREEN)
                    .append(relic.getDisplayName())
                    .append(" Relic. Click this message or check your personal Relic Inventory - /sframe inventory").color(ChatColor.GREEN)
                    .create();

            TextComponent message = new TextComponent(baseComponents);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/sframe inventory"));
            message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Text(new ComponentBuilder("Open Relic Inventory").create())));

            ChatUtils.sendMessage(player, message);
        } else {
            ChatUtils.sendMessage(player, ChatColor.RED + "You couldn't receive a relic because your relic inventory is full");
        }
    }

    public void addRandomRelic(@Nonnull Relic.Era era) {
        addRelic(switch (era) {
            case LITH -> SFrameStacks.RANDOM_LITH_RELICS.getRandom();
            case MESO -> SFrameStacks.RANDOM_MESO_RELICS.getRandom();
            case NEO -> SFrameStacks.RANDOM_NEO_RELICS.getRandom();
            case AXI -> SFrameStacks.RANDOM_AXI_RELICS.getRandom();
        });
    }

    public void addRandomRelic() {
        Relic.Era[] values = Relic.Era.values();
        addRandomRelic(values[ThreadLocalRandom.current().nextInt(values.length)]);
    }

    public void open() {
        Player player = Bukkit.getPlayer(owner);
        if (player != null) {
            player.openInventory(inventory);
        }
    }

}
