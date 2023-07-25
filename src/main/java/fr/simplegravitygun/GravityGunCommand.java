package fr.simplegravitygun;

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
        if (sender instanceof Player player) {

            // Create the gravity gun item
            ItemStack gravityGun = new ItemStack(Material.STICK);
            ItemMeta gravityGunMeta = gravityGun.getItemMeta();
            gravityGunMeta.setDisplayName("Gravity Gun");
            gravityGun.setItemMeta(gravityGunMeta);

            // Give the gravity gun to the player
            player.getInventory().addItem(gravityGun);

            return true;
        }

        return false;
    }
}

