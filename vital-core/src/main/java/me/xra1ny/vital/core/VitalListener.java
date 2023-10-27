package me.xra1ny.vital.core;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * A listener component for handling events in the Vital framework.
 * Implements both the VitalComponent and Bukkit's Listener interface.
 *
 * @author xRa1ny
 */
public class VitalListener implements VitalComponent, Listener {
    /**
     * Called when this VitalListener is registered.
     * Registers this listener with the Bukkit plugin manager.
     */
    @Override
    public void onRegistered() {

    }

    /**
     * Called when this VitalListener is unregistered.
     * Unregisters this listener from all events using Bukkit's HandlerList.
     */
    @Override
    public void onUnregistered() {

    }
}
