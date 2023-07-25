package fr.simplegravitygun.commands;

import fr.simplegravitygun.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GravityGunCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }

        ItemStack gravityGun = new ItemBuilder(Material.STICK)
                .setName(ChatColor.translateAlternateColorCodes('&', "&fGravityGun"))
                .setLore(
                        ChatColor.translateAlternateColorCodes('&', "&7Clique droit pour déplacer un bloc"),
                        ChatColor.translateAlternateColorCodes('&',"&7Clique droit en étant accroupi pour annuler")
                )
                .toItemStack();

        // Give the gravity gun to the player
        player.getInventory().addItem(gravityGun);

        return true;
    }
}

