package fr.simplegravitygun.events;

import fr.simplegravitygun.SimpleGravityGun;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class GravityGunEvent implements Listener {
    private final SimpleGravityGun plugin;
    private final HashMap<Player, FallingBlock> fallingBlockHashMap = new HashMap<>();
    private Player lastPlayer;
    private Block lastBlock;

    public GravityGunEvent(SimpleGravityGun plugin) {
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

        if (player.getItemInHand() != null && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(plugin.gravityGun.getItemMeta().getDisplayName())) {

            if (plugin.cooldown.containsKey(player)) {
                return;
            }
            plugin.cooldown.remove(player);
            plugin.cooldown.put(player, 1);

            if (block != null) {
                if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (lastPlayer == null || lastPlayer != player || lastBlock != block) {

                        if (fallingBlockHashMap.containsKey(player)) {
                            player.sendMessage("§cVous devez d'abord annuler le bloc précédent !");
                            return;
                        }
                        FallingBlock fallingBlock = block.getWorld().spawn(block.getLocation().add(0, 0.2, 0), FallingBlock.class);
                        fallingBlock.setGravity(false);
                        fallingBlock.setDropItem(true);
                        fallingBlock.setHurtEntities(false);
                        fallingBlock.setInvulnerable(true);
                        fallingBlock.setCustomNameVisible(true);
                        fallingBlock.setFreezeTicks(999999999);
                        player.sendMessage("§aBloc capturé !");
                        fallingBlockHashMap.put(player, fallingBlock);

                        block.setType(Material.AIR);

                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                // Vérifie si le joueur est accroupi
                                if (player.isSneaking()) {
                                    block.setType(fallingBlock.getBlockData().getMaterial());
                                    fallingBlock.remove();
                                    fallingBlockHashMap.remove(player);
                                    player.sendMessage("§cBloc annulé !");
                                    cancel();
                                    return;
                                } else {
                                    // Calcule la position cible et la vélocité
                                    org.bukkit.Location targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(2.5)).add(0, 1.2, 0);
                                    org.bukkit.util.Vector velocity = targetLocation.subtract(fallingBlock.getLocation()).toVector();
                                    fallingBlock.setVelocity(velocity);
                                    fallingBlock.setCustomName("Gravity Gun - " + fallingBlock.getLocation().getX() + "/" + fallingBlock.getLocation().getY() + "/" + fallingBlock.getLocation().getZ());

                                }
                                if (fallingBlock.isDead()) {
                                    fallingBlockHashMap.remove(player);
                                    player.sendMessage("§aBloc posé !");
                                    cancel();
                                }
                                if (!player.getItemInHand().getItemMeta().getAsString().equals(plugin.gravityGun.getItemMeta().getAsString())) {
                                    fallingBlock.remove();
                                    fallingBlockHashMap.remove(player);
                                    player.sendMessage("§cVous avez lacher le Gravity Gun !");
                                    cancel();
                                }
                            }
                        }.runTaskTimer(plugin, 0, 1);

                        lastPlayer = player;
                        lastBlock = block;
                    }
                }
            }
        }
    }

}

