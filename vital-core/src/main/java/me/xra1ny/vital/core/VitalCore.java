package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * The main instance of the Vital framework.
 *
 * @param <T> The JavaPlugin instance.
 * @author xRa1ny
 */
@Log
public abstract class VitalCore<T extends JavaPlugin> {

    /**
     * The JavaPlugin instance associated with this VitalCore.
     */
    @Getter(onMethod = @__(@NotNull))
    private final T javaPlugin;

    /**
     * The management component for handling Vital components.
     */
    @Getter(onMethod = @__(@NotNull))
    private final VitalComponentManager vitalComponentManager = new VitalComponentManager();

    private boolean enabled;

    /**
     * Constructs a new VitalCore instance.
     *
     * @param javaPlugin The JavaPlugin instance to associate with VitalCore.
     */
    public VitalCore(@NotNull T javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    public final void enable() {
        if(enabled) {
            return;
        }

        enabled = true;

        log.info("Enabling VitalCore<" + getJavaPlugin() + ">");
        onEnable();
        log.info("VitalCore<" + getJavaPlugin() + "> enabled!");
        log.info("Hello from Vital!");
    }

    /**
     * Called when this VitalCore is enabled
     */
    public abstract void onEnable();
}
