package fr.simplegravitygun;
import fr.simplegravitygun.commands.GravityGunCommand;
import fr.simplegravitygun.configs.Messages;
import fr.simplegravitygun.events.FallingBlockTouchGroundEvent;
import fr.simplegravitygun.events.GravityGunEvent;
import fr.simplegravitygun.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;
public final class SimpleGravityGun extends JavaPlugin {
    public boolean place = false;
    public HashMap<Player, Integer> cooldown = new HashMap<>();
    public BukkitRunnable cooldownTimer = new BukkitRunnable() {
        @Override
        public void run() {
            // make cooldown for a command
            for (Player player : cooldown.keySet()) {

                if (cooldown.get(player) > 0) {
                    cooldown.put(player, cooldown.get(player) - 1);
                } else {
                    cooldown.remove(player);
                }
            }
        }
    };
    Material item = Material.getMaterial(getConfig().getString("gravitygun.item").toUpperCase());
    String name = getConfig().getString("gravitygun.name");
    public ItemStack gravityGun = new ItemBuilder(item)
            .setName(ChatColor.translateAlternateColorCodes('&', name))
            .setLore(getLore())
            .toItemStack();
    public Color getRGBFromConfig() {
        return Color.fromRGB(getConfig().getInt("settings.lazer-color.r"), getConfig().getInt("settings.lazer-color.g"), getConfig().getInt("settings.lazer-color.b"));
    }
    public Boolean lazerEnabled() {
        return getConfig().getBoolean("settings.enable-lazer");
    }
    private List<String> getLore() {
        List<String> lore = getConfig().getStringList("gravitygun.lore");
        lore.replaceAll(textToTranslate -> ChatColor.translateAlternateColorCodes('&', textToTranslate));
        return lore;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        // Ajoute une commande pour donner un bâton enchanté nommé GravityGun
        getCommand("gravitygun").setExecutor(new GravityGunCommand(this));
        // Ajoute l'événement pour le gravitygun dans un fichier nommé GravityGunEvent.class
        getServer().getPluginManager().registerEvents(new GravityGunEvent(this), this);
        getServer().getPluginManager().registerEvents(new FallingBlockTouchGroundEvent(this), this);
        cooldownTimer.runTaskTimer(this, 20, 10);
        new Messages(this);
        setupMode();
    }
    public void setupMode() {
        String mode = getConfig().getString("settings.mode");
        assert mode != null;
        this.place = mode.equalsIgnoreCase("place");
    }
}


