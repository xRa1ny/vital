package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * The main instance of the Vital framework.
 *
 * @param <T> The JavaPlugin instance.
 * @author xRa1ny
 */
@SuppressWarnings("unused")
@Log
public abstract class VitalCore<T extends JavaPlugin> {
    private static VitalCore<?> instance;

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

    @Getter
    private boolean enabled;

    /**
     * Constructs a new VitalCore instance.
     *
     * @param javaPlugin The JavaPlugin instance to associate with VitalCore.
     */
    public VitalCore(@NotNull T javaPlugin) {
        this.javaPlugin = javaPlugin;
        vitalComponentManager.registerVitalComponent(new VitalListenerManager(javaPlugin));
    }

    /**
     * Singleton access-point for all `VitalCore<T>` Instances.
     *
     * @param type Your Plugin's Main Class.
     * @param <T>  The Type of your Plugin's Main Class.
     * @return The VitalCore Instance.
     * @throws ClassCastException If the provided Type and `Vital<T>` Instance don't match.
     */
    @SuppressWarnings("unchecked")
    public static <T extends JavaPlugin> VitalCore<T> getVitalCoreInstance(@NotNull Class<T> type) {
        return (VitalCore<T>) instance;
    }

    /**
     * Singleton access-point for all `VitalCore<T>` Instances.
     * This Method will return a generically inaccurate Object.
     * For more accurate VitalCore Types use {@link VitalCore#getVitalCoreInstance(Class)}
     *
     * @return The VitalCore Instance.
     */
    public static VitalCore<?> getVitalCoreInstance() {
        return instance;
    }

    public final void enable() {
        if (enabled) {
            return;
        }

        instance = this;

        log.info("Enabling VitalCore<" + getJavaPlugin() + ">");
        onEnable();

        // hold all scanned and ready for dependency injected classes.
        final Map<Class<? extends VitalComponent>, Integer> prioritisedDependencyInjectableClasses = new HashMap<>();

        // scan for all classes annotated with `@VitalAutoRegistered` and attempt to register them on the base manager.
        // NOTE: this configuration is entirely user dependent, if a user wrongly implements their auto registered components,
        // with components that cannot be dependency injected, this registration will not work!!!
        for (Class<?> vitalComponentClass : new Reflections().getTypesAnnotatedWith(VitalAutoRegistered.class).stream()
                .filter(VitalComponent.class::isAssignableFrom)
                .toList()) {
            // assume every class is extended from `VitalComponent` since we filtered in our chain above.
            final Optional<? extends VitalComponent> optionalVitalComponent = (Optional<? extends VitalComponent>) VitalDIUtils.getDependencyInjectedInstance(vitalComponentClass);

            // display error if a dependency injected instance of our marked class could not be created, else register it von the base manager of vital.
            if (optionalVitalComponent.isEmpty()) {
                log.severe("Vital attempted to automatically register " + vitalComponentClass + " but failed!");
            } else {
                vitalComponentManager.registerVitalComponent(optionalVitalComponent.get());
            }
        }

        // loop over every registered manager and enable them.
        for (VitalComponentListManager<?> vitalComponentListManager : getVitalComponentManager().getVitalComponentList(VitalComponentListManager.class)) {
            vitalComponentListManager.enable();
        }

        enabled = true;
        log.info("VitalCore<" + getJavaPlugin() + "> enabled!");
        log.info("Hello from Vital!");
    }

    /**
     * Called when this VitalCore is enabled
     */
    public abstract void onEnable();
}
