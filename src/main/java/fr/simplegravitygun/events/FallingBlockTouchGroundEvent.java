package fr.simplegravitygun.events;
import fr.simplegravitygun.SimpleGravityGun;
import fr.simplegravitygun.configs.Messages;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;
public class FallingBlockTouchGroundEvent implements Listener {
    SimpleGravityGun plugin;
    public FallingBlockTouchGroundEvent(SimpleGravityGun simpleGravityGun) {
        this.plugin = simpleGravityGun;
    }
    @EventHandler
    public void onBlockChange(EntityChangeBlockEvent e) {
        if (e.getEntity() instanceof FallingBlock fallingBlock && GravityGunEvent.fallingBlockHashMap.containsValue(fallingBlock)) {
            for (Player player : GravityGunEvent.fallingBlockHashMap.keySet()) {
                if (GravityGunEvent.fallingBlockHashMap.get(player) == fallingBlock) {
                    fallingBlock.remove();
                    GravityGunEvent.fallingBlockHashMap.remove(player);
                    player.sendMessage(Messages.getBlock_placed());
                    return;
                }
            }
        }
    }
}