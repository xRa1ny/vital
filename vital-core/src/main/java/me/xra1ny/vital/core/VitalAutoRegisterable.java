package me.xra1ny.vital.core;

import lombok.NonNull;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Interface specifying that any underlying child class is viable for an auto registration mechanism
 *
 * @see VitalDI
 * @see VitalAutoRegistered
 * @deprecated Since the introduction to dependency injection.
 */
@Deprecated
public interface VitalAutoRegisterable {
    /**
     * Method defining logic for automatic {@link VitalComponent} registration upon call.
     *
     * @param javaPluginType The class of your {@link JavaPlugin} for singleton access within Vital.
     */
    void autoRegister(@NonNull Class<? extends JavaPlugin> javaPluginType);
}
