package fr.simplegravitygun;

import fr.simplegravitygun.commands.GravityGunCommand;
import fr.simplegravitygun.events.GravityGunEvent;
import fr.simplegravitygun.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public final class SimpleGravityGun extends JavaPlugin {

    public HashMap<Player, Integer> cooldown = new HashMap<>();

    public ItemStack gravityGun = new ItemBuilder(Material.STICK)
            .setName(ChatColor.translateAlternateColorCodes('&', "&fGravityGun"))
            .setLore(
                    ChatColor.translateAlternateColorCodes('&', "&7Clique droit pour déplacer un bloc"),
                    ChatColor.translateAlternateColorCodes('&', "&7Clique droit en étant accroupi pour annuler")
            )
            .toItemStack();
    public BukkitRunnable cooldownTimer = new BukkitRunnable() {
        @Override
        public void run() {
            // make cooldown for a command
            for (Player player : cooldown.keySet()) {
                System.out.println(cooldown.get(player));

                if (cooldown.get(player) > 0) {
                    cooldown.put(player, cooldown.get(player) - 1);
                } else {
                    cooldown.remove(player);
                }
            }

        }
    };

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Ajoute une commande pour donner un bâton enchanté nommé GravityGun
        getCommand("gravitygun").setExecutor(new GravityGunCommand(this));

        // Ajoute l'événement pour le gravitygun dans un fichier nommé GravityGunEvent.class
        getServer().getPluginManager().registerEvents(new GravityGunEvent(this), this);

        cooldownTimer.runTaskTimer(this, 20, 10);

    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
    }
}


