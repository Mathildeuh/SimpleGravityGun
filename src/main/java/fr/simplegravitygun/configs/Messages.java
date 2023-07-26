package fr.simplegravitygun.configs;
import fr.simplegravitygun.SimpleGravityGun;
import org.bukkit.ChatColor;
public class Messages {
    static String block_catch;
    static String block_canceled;
    static String block_placed;
    static String block_placed_sneak;
    static String gun_released;
    static String already_using_gun;
    SimpleGravityGun plugin;
    public Messages(SimpleGravityGun plugin) {
        this.plugin = plugin;
        block_catch = plugin.getConfig().getString("messages.block-catch");
        block_placed_sneak = plugin.getConfig().getString("messages.block-placed-sneak");
        block_placed = plugin.getConfig().getString("messages.block-placed");
        gun_released = plugin.getConfig().getString("messages.gun-released");
        already_using_gun = plugin.getConfig().getString("messages.already-using-gun");

    }
    public static String getBlock_placed_sneak() {
        return ChatColor.translateAlternateColorCodes('&', block_placed_sneak);
    }
    public static String getAlready_using_gun() {
        return ChatColor.translateAlternateColorCodes('&', already_using_gun);
    }
    public static String getBlock_catch() {
        return ChatColor.translateAlternateColorCodes('&', block_catch);
    }
    public static String getBlock_canceled() {
        return ChatColor.translateAlternateColorCodes('&', block_canceled);
    }
    public static String getBlock_placed() {
        return ChatColor.translateAlternateColorCodes('&', block_placed);
    }
    public static String getGun_released() {
        return ChatColor.translateAlternateColorCodes('&', gun_released);
    }
}
