package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.essentia.inject.DIContainer;
import me.xra1ny.essentia.inject.EssentiaInject;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.*;

/**
 * The main instance of the Vital-Framework.
 *
 * @param <T> The JavaPlugin type.
 * @author xRa1ny
 */
@SuppressWarnings("unused")
@Log
public abstract class VitalCore<T extends JavaPlugin> implements DIContainer {
    @Getter
    @NonNull
    private final Map<Class<?>, Object> componentClassObjectMap = new HashMap<>();

    @Override
    public void unregisterComponent(@NonNull Class<?> type) {
        componentClassObjectMap.remove(type);
    }

    @Override
    public void unregisterComponent(@NonNull Object o) {
        componentClassObjectMap.remove(o.getClass(), o);

        if(o instanceof VitalComponent vitalComponent) {
            vitalComponent.onUnregistered();
        }
    }

    @Override
    public void registerComponent(@NonNull Object o) {
        if(isRegistered(o)) {
            return;
        }

        componentClassObjectMap.put(o.getClass(), o);

        if(o instanceof VitalComponent vitalComponent) {
            vitalComponent.onRegistered();
        }
    }

    private static VitalCore<?> instance;

    /**
     * Holds a list of all classes registered on this classpath for later use of dependency injection.
     *
     * @apiNote This implementation is used to improve performance across many managers since scanning takes some time.
     */
    @Getter
    @NonNull
    private static final Set<Class<? extends VitalComponent>> scannedClassSet = new Reflections().getSubTypesOf(VitalComponent.class);

    /**
     * The JavaPlugin instance associated with this {@link VitalCore}.
     */
    @Getter
    @NonNull
    private final T javaPlugin;

    @Getter
    private boolean enabled;

    /**
     * Constructs a new {@link VitalCore} instance.
     *
     * @param javaPlugin The {@link JavaPlugin} instance to associate with {@link VitalCore}.
     */
    public VitalCore(@NonNull T javaPlugin) {
        this.javaPlugin = javaPlugin;
        instance = this;
        registerComponent(this);
        registerComponent(javaPlugin);
    }

    /**
     * Singleton access-point for all {@link VitalCore} instances.
     *
     * @param type Your plugin's main class.
     * @param <T>  The type of your plugin's main class.
     * @return The {@link VitalCore} instance.
     * @throws ClassCastException If the provided type and {@link VitalCore} plugin instance don't match.
     */
    @SuppressWarnings("unchecked")
    public static <T extends JavaPlugin> VitalCore<T> getVitalCoreInstance(@NonNull Class<T> type) {
        return (VitalCore<T>) instance;
    }

    /**
     * Singleton access-point for {@link VitalCore} instance.
     * This method will return a generically inaccurate Object.
     * For more accurate {@link VitalCore} types use {@link VitalCore#getVitalCoreInstance(Class)}
     *
     * @return The {@link VitalCore} instance.
     */
    public static VitalCore<?> getVitalCoreInstance() {
        return instance;
    }

    /**
     * Enables the Vital-Framework, initialising needed systems.
     */
    @SneakyThrows // TODO
    public final void enable() {
        if (enabled) {
            return;
        }

        log.info("Enabling VitalCore<" + getJavaPlugin() + ">");

        onEnable();

        // register both plugin package and Vital's package.
        EssentiaInject.run(this, javaPlugin.getClass().getPackageName(), "me.xra1ny.vital");

        enabled = true;
        log.info("VitalCore<" + getJavaPlugin() + "> enabled!");
        log.info("Hello from Vital!");
    }

    /**
     * Called when this VitalCore is enabled
     */
    public abstract void onEnable();

    /**
     * Called when this VitalCore is disabled.
     */
    public abstract void onDisable();

    public Optional<VitalComponent> getComponent(@NotNull UUID uniqueId) {
        return getComponentList().stream()
                .filter(VitalComponent.class::isInstance)
                .map(VitalComponent.class::cast)
                .filter(component -> component.getUniqueId().equals(uniqueId))
                .findFirst();
    }

    public boolean isUsingSubModule(@NonNull Class<? extends VitalSubModule> subModuleType) {
        return isRegistered(subModuleType);
    }

    @NonNull
    public List<? extends VitalSubModule> getUsedSubModules() {
        return getComponentList(VitalSubModule.class);
    }

    @NonNull
    public List<String> getUsedSubModuleNames() {
        return getUsedSubModules().stream()
                .map(VitalSubModule::getName)
                .toList();
    }
}
