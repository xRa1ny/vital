package me.xra1ny.vital.core;

import me.xra1ny.essentia.inject.annotation.AfterInit;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A listener component for handling events in the Vital-Framework.
 *
 * @author xRa1ny
 */
public class VitalListener implements VitalComponent, Listener {
    @AfterInit
    public void afterInit(JavaPlugin javaPlugin) {
        Bukkit.getPluginManager().registerEvents(this, javaPlugin);
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
