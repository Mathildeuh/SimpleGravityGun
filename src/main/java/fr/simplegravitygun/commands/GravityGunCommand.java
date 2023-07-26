package fr.simplegravitygun.commands;

import fr.simplegravitygun.SimpleGravityGun;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GravityGunCommand implements CommandExecutor {
    private final SimpleGravityGun plugin;
    public GravityGunCommand(SimpleGravityGun simpleGravityGun) {
        this.plugin = simpleGravityGun;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        // Give the gravity gun to the player
        player.getInventory().addItem(plugin.gravityGun);
        return true;
    }
}

