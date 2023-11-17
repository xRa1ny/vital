package me.xra1ny.vital.core;

import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

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

    /**
     * Attempts to automatically register all `VitalListeners` in the specified package.
     *
     * @param packageName The package.
     */
    @SneakyThrows
    public void registerVitalListeners(@NotNull String packageName) {
        for(Class<? extends VitalListener> vitalListenerClass : new Reflections(packageName).getSubTypesOf(VitalListener.class)) {
            final VitalListener vitalListener = vitalListenerClass.getDeclaredConstructor().newInstance();

            registerVitalComponent(vitalListener);
        }
    }
}
