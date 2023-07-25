package fr.simplegravitygun;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class GravityGunEvent implements Listener {
    private final JavaPlugin plugin;
    private Player lastPlayer;
    private Block lastBlock;

    public GravityGunEvent(JavaPlugin plugin) {
        this.plugin = plugin;
        this.lastPlayer = null;
        this.lastBlock = null;
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
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if (lastPlayer == null || lastPlayer != player || lastBlock != block) {
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
                                org.bukkit.Location targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(4));
                                org.bukkit.util.Vector velocity = targetLocation.subtract(fallingBlock.getLocation()).toVector().multiply(1.0);
                                fallingBlock.setVelocity(velocity);
                            }
                        }
                    }.runTaskTimer(plugin, 0, 1);

                    lastPlayer = player;
                    lastBlock = block;
                }
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                // Vérifie si le bloc n'est pas déjà pris
                if (block == lastBlock) {
                    org.bukkit.util.Vector velocity = player.getLocation().getDirection().multiply(8);
                    block.getWorld().spawnFallingBlock(block.getLocation(), block.getBlockData()).setVelocity(velocity);
                }
            }
        }
    } else {
        // Si le joueur n'a pas le "gravity gun" dans la main, le bloc revient à sa position initiale
        if (block != null && block.getType() != Material.AIR) {
            block.setType(block.getType());
        }
    }
}
}

