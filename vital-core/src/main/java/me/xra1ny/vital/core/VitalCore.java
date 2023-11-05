package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The main instance of the Vital framework.
 *
 * @param <T> The JavaPlugin instance.
 * @author xRa1ny
 */
@Log
public abstract class VitalCore<T extends JavaPlugin> {
    private static final List<VitalCore<?>> vitalCoreList = new ArrayList<>();

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

        vitalCoreList.add(this);
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

    /**
     * Singleton access-point for all `VitalCore<T>` Instances.
     *
     * @param type Your Plugin's Main Class.
     * @return An Optional holding either the VitalCore Instance tied to the specified Main Class, or null.
     * @param <T> The Type of your Plugin's Main Class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends JavaPlugin> Optional<VitalCore<T>> getVitalCoreInstance(@NotNull Class<T> type) {
        return vitalCoreList.stream()
                .filter(vitalCore -> vitalCore.getJavaPlugin().getClass().equals(type))
                .map(vitalCore -> (VitalCore<T>) vitalCore)
                .findAny();
    }
}
