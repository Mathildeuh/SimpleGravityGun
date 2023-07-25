package fr.simplegravitygun;

import fr.simplegravitygun.commands.GravityGunCommand;
import fr.simplegravitygun.events.GravityGunEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class SimpleGravityGun extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic

        // Ajoute une commande pour donner un bâton enchanté nommé GravityGun
        getCommand("gravitygun").setExecutor(new GravityGunCommand());

        // Ajoute l'événement pour le gravitygun dans un fichier nommé GravityGunEvent.class
        getServer().getPluginManager().registerEvents(new GravityGunEvent(this), this);
    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
    }
}


