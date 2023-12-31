package fr.simplegravitygun.events;

import fr.simplegravitygun.SimpleGravityGun;
import fr.simplegravitygun.configs.Messages;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.text.DecimalFormat;
import java.util.HashMap;

public class GravityGunEvent implements Listener {
    public static final HashMap<Player, FallingBlock> fallingBlockHashMap = new HashMap<>();
    private final SimpleGravityGun plugin;
    public GravityGunEvent(SimpleGravityGun plugin) {
        this.plugin = plugin;
    }
    Vector getDirectionBetweenLocations(Location Start, Location End) {
        Vector from = Start.toVector();
        Vector to = End.toVector();
        return to.subtract(from);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        Block block = e.getClickedBlock();
        if (player.getItemInHand() != null && player.getItemInHand().getType() == plugin.gravityGun.getType() && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(plugin.gravityGun.getItemMeta().getDisplayName())) {
            e.setCancelled(true);
            if (plugin.cooldown.containsKey(player)) {
                return;
            }
            plugin.cooldown.remove(player);
            plugin.cooldown.put(player, 1);
            if (block != null) {
                if (block.isLiquid() || block.isPassable() || block.isEmpty()) return;
                if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    if (fallingBlockHashMap.containsKey(player)) {
                        player.sendMessage(Messages.getAlready_using_gun());
                        return;
                    }
                    // Spawn le falling block
                    FallingBlock fallingBlock = block.getWorld().spawn(block.getLocation().add(0, 0.01, 0), FallingBlock.class);
                    fallingBlock.setGravity(false);
                    fallingBlock.setDropItem(true);
                    fallingBlock.setInvulnerable(true);
                    fallingBlock.setPortalCooldown(99999999);
                    fallingBlock.setCustomNameVisible(true);
                    fallingBlock.setFreezeTicks(999999999);
                    player.sendMessage(Messages.getBlock_catch());
                    fallingBlockHashMap.put(player, fallingBlock);
                    player.playSound(player.getLocation(), Sound.ENTITY_VILLAGER_YES, 1, 1);
                    block.setType(Material.AIR);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.isSneaking() && player.getGameMode() != GameMode.CREATIVE) {
                                destroyFallingBlock(fallingBlock, player);
                                player.sendMessage(Messages.getBlock_placed_sneak());
                                cancel();
                                return;
                            } else {
                                // Spawn le laser ou non
                                if (plugin.lazerEnabled()) {
                                    lazer(fallingBlock, player);
                                }
                                // Déplace le bloc
                                org.bukkit.Location targetLocation = player.getLocation().add(player.getLocation().getDirection().multiply(3)).add(0, 1.2, 0);
                                org.bukkit.util.Vector velocity = targetLocation.subtract(fallingBlock.getLocation()).toVector();
                                fallingBlock.setVelocity(velocity);
                                // Display name du falling block
                                Double x = fallingBlock.getLocation().getX();
                                Double y = fallingBlock.getLocation().getY();
                                Double z = fallingBlock.getLocation().getZ();
                                DecimalFormat f = new DecimalFormat("##.00");
                                fallingBlock.setCustomName("Gravity Gun - " + f.format(x) + "/" + f.format(y) + "/" + f.format(z));
                            }
                            if (!(player.getItemInHand() != null && player.getItemInHand().getType() == plugin.gravityGun.getType() && player.getItemInHand().hasItemMeta() && player.getItemInHand().getItemMeta().hasDisplayName() && player.getInventory().getItemInHand().getItemMeta().getDisplayName().equals(plugin.gravityGun.getItemMeta().getDisplayName()))) {
                                destroyFallingBlock(fallingBlock, player);
                                player.sendMessage(Messages.getGun_released());
                                cancel();
                            }
                            if (fallingBlock.isOnGround()) {
                                destroyFallingBlock(fallingBlock, player);
                                player.sendMessage(Messages.getBlock_placed());
                                cancel();
                            }
                        }
                    }.runTaskTimer(plugin, 0, 1);
                }
            }
        }
    }
    public void destroyFallingBlock(FallingBlock fallingBlock, Player player) {
        if (!plugin.place) {
            fallingBlock.setGravity(true);
            fallingBlock.setDropItem(true);
            fallingBlock.setHurtEntities(true);
            fallingBlock.setInvulnerable(false);
            fallingBlock.setCustomNameVisible(false);
        } else {
            fallingBlock.getLocation().getBlock().setType(fallingBlock.getBlockData().getMaterial());
            fallingBlock.remove();
        }
        fallingBlockHashMap.remove(player);
    }
    public void lazer(FallingBlock fallingBlock, Player player) {
        if (!fallingBlockHashMap.containsKey(player)) return;
        Location loc1 = fallingBlock.getLocation();
        Location loc2 = player.getLocation().add(0, 0.9, 0);
        Vector vector = getDirectionBetweenLocations(loc1, loc2).normalize(); //make sure it has length one at start
        for (double i = 0; i <= loc1.distance(loc2); i += 0.1) {
            Vector addition = new Vector().copy(vector).multiply(i);
            Location newLoc = loc1.clone().add(addition).add(0, 0.5, 0);
            loc1.getWorld().spawnParticle(Particle.REDSTONE, newLoc, 0, 0, 2, 0, 1, new Particle.DustOptions(plugin.getRGBFromConfig(), 0.5F));
        }
    }
}
