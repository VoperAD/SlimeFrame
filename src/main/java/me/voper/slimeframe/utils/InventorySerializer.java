package me.voper.slimeframe.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventorySerializer {

    @Nonnull
    public static String itemStackArrayToBase64(@Nonnull final ItemStack[] itemStacks) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream);

            objectOutputStream.writeInt(itemStacks.length);

            for (ItemStack itemStack : itemStacks) {
                objectOutputStream.writeObject(itemStack);
            }

            objectOutputStream.flush();
            objectOutputStream.close();

            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save item stacks", e);
        }
    }

    @Nonnull
    public static String toBase64(@Nonnull final Inventory inventory) throws IllegalStateException {
        try {
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final BukkitObjectOutputStream objectOutputStream = new BukkitObjectOutputStream(outputStream);

            objectOutputStream.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                objectOutputStream.writeObject(inventory.getItem(i));
            }

            objectOutputStream.flush();
            objectOutputStream.close();

            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to save items.", e);
        }
    }

    @Nonnull
    public static Inventory fromBase64(@Nonnull final String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);
            final Inventory inventory = Bukkit.createInventory(null, 54);

            for (int i = 0; i < objectInputStream.readInt(); i++) {
                inventory.setItem(i, (ItemStack) objectInputStream.readObject());
            }

            objectInputStream.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    public static Inventory relicsInventoryFromBase64(final String data, final Player p) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);
            final Inventory inventory = Bukkit.createInventory(p, 54, ChatColor.BLUE + p.getName() + "'s Relics Inventory");

            for (int i = 0; i < objectInputStream.readInt(); i++) {
                inventory.setItem(i, (ItemStack) objectInputStream.readObject());
            }

            objectInputStream.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }

    @Nonnull
    public static ItemStack[] itemStackArrayFromBase64(@Nonnull final String data) throws IOException {
        try {
            final ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            final BukkitObjectInputStream objectInputStream = new BukkitObjectInputStream(inputStream);
            final ItemStack[] items = new ItemStack[objectInputStream.readInt()];

            for (int i = 0; i < items.length; i++) {
                items[i] = (ItemStack) objectInputStream.readObject();
            }

            objectInputStream.close();
            return items;
        } catch (ClassNotFoundException e) {
            throw new IOException("Unable to decode class type.", e);
        }
    }



}
