package me.xra1ny.vital.core;

import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class VitalListenerManager extends VitalComponentListManager<VitalListener> {
    private final JavaPlugin javaPlugin;

    public VitalListenerManager(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalListener vitalListener) {
        Bukkit.getPluginManager().registerEvents(vitalListener, javaPlugin);
    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalListener vitalListener) {
        HandlerList.unregisterAll(vitalListener);
    }

    @Override
    public Class<VitalListener> managedType() {
        return VitalListener.class;
    }
}
