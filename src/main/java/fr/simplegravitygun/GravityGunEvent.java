package fr.simplegravitygun;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GravityGunEvent implements Listener {
    private final JavaPlugin plugin;

    public GravityGunEvent(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Handles the player interact event.
     *
     * @param event The player interact event
     */
@EventHandler
public void onPlayerInteract(PlayerInteractEvent event) {
    Player player = event.getPlayer();
    Block block = event.getClickedBlock();
    
    // Vérifie si le joueur a le "gravity gun" dans la main
    if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
        // Vérifie si le bloc cliqué n'est pas nul
        if (block != null) {
            FallingBlock fallingBlock = block.getWorld().spawn(block.getLocation(), FallingBlock.class);
            block.setType(Material.AIR);

            new BukkitRunnable() {
                @Override
                public void run() {
                    // Vérifie si le joueur est accroupi
                    if (player.isSneaking()) {
                        fallingBlock.remove();
                        block.setType(fallingBlock.getBlockData().getMaterial());
                        cancel();
                    } else {
                        // Calcule la position cible et la vélocité
                        org.bukkit.Location targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(2));
                        org.bukkit.util.Vector velocity = targetLocation.subtract(fallingBlock.getLocation()).toVector().multiply(0.1);
                        fallingBlock.setVelocity(velocity);
                    }
                }
            }.runTaskTimerAsynchronously(plugin, 0, 1);
        }
    } else {
        // Si le joueur n'a pas le "gravity gun" dans la main, le bloc revient à sa position initiale
        if (block != null);
    }
}
}

