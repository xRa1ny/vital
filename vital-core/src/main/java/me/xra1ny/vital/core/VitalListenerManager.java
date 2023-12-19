package me.xra1ny.vital.core;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The default listener manager of the Vital-Framework.
 *
 * @author xRa1ny
 */
@Log
@VitalDI
@VitalAutoRegistered
public final class VitalListenerManager extends VitalComponentListManager<VitalListener> {
    private final JavaPlugin javaPlugin;

    /**
     * Construct a new listener manager instance for the supplied {@link JavaPlugin} instance.
     *
     * @param javaPlugin The {@link JavaPlugin} instance for this manager.
     */
    public VitalListenerManager(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void onRegistered() {
        log.info("VitalListenerManager online!");
    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NonNull VitalListener vitalListener) {
        Bukkit.getPluginManager().registerEvents(vitalListener, javaPlugin);
    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalListener vitalListener) {
        HandlerList.unregisterAll(vitalListener);
    }

    @Override
    public Class<VitalListener> managedType() {
        return VitalListener.class;
    }
}
