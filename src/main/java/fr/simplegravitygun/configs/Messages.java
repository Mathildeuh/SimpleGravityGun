package fr.simplegravitygun.configs;

import fr.simplegravitygun.SimpleGravityGun;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Messages {

    SimpleGravityGun plugin;
    static String block_catch;
    static String block_canceled;
    static String block_placed;
    static String gun_released;
    static  String cant_use_now;

    public Messages(SimpleGravityGun plugin) {
        this.plugin = plugin;

        block_catch = plugin.getConfig().getString("messages.block-catch");
        block_canceled = plugin.getConfig().getString("messages.block-canceled");
        block_placed = plugin.getConfig().getString("messages.block-placed");
        gun_released = plugin.getConfig().getString("messages.gun-released");
        cant_use_now = plugin.getConfig().getString("messages.cant-use-now");

    }

    public static String getCant_use_now() {
        return ChatColor.translateAlternateColorCodes('&', cant_use_now);
    }

    public static String getBlock_catch() {
        return ChatColor.translateAlternateColorCodes('&', block_catch);
    }

    public static String getBlock_canceled() {
        return ChatColor.translateAlternateColorCodes('&',block_canceled);
    }

    public static String getBlock_placed() {
        return ChatColor.translateAlternateColorCodes('&',block_placed);
    }

    public static String getGun_released() {
        return ChatColor.translateAlternateColorCodes('&',gun_released);
    }
}
