package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.Reflections;

import java.util.Optional;
import java.util.Set;

/**
 * The main instance of the Vital-Framework.
 *
 * @param <T> The JavaPlugin type.
 * @author xRa1ny
 */
@SuppressWarnings("unused")
@Log
public abstract class VitalCore<T extends JavaPlugin> extends VitalComponentListManager<VitalComponent> {
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

    @Getter
    private boolean usingVitalConfigs;

    @Getter
    private boolean usingVitalHolograms;

    @Getter
    private boolean usingVitalPlayers;

    @Getter
    private boolean usingVitalCommands;

    @Getter
    private boolean usingVitalItems;

    @Getter
    private boolean usingVitalInventories;

    @Getter
    private boolean usingVitalDatabases;

    @Getter
    private boolean usingVitalMinigames;

    @Getter
    private boolean usingVitalUtils;

    /**
     * Constructs a new {@link VitalCore} instance.
     *
     * @param javaPlugin The {@link JavaPlugin} instance to associate with {@link VitalCore}.
     */
    public VitalCore(@NonNull T javaPlugin) {
        this.javaPlugin = javaPlugin;
        instance = this;
        registerVitalComponent(new VitalListenerManager(javaPlugin));
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

    @Override
    public void onVitalComponentRegistered(@NonNull VitalComponent vitalComponent) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalComponent vitalComponent) {

    }

    @Override
    public Class<VitalComponent> managedType() {
        return VitalComponent.class;
    }

    /**
     * Enables the Vital-Framework, initialising needed systems.
     */
    public final void enable() {
        if (enabled) {
            return;
        }

        log.info("Enabling VitalCore<" + getJavaPlugin() + ">");

        try {
            Class.forName("me.xra1ny.vital.configs.VitalConfigManager");

            usingVitalConfigs = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.holograms.VitalHologramManager");

            usingVitalHolograms = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.players.VitalPlayerManager");

            usingVitalPlayers = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.commands.VitalCommandManager");

            usingVitalCommands = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.items.VitalItemStackManager");

            usingVitalItems = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.inventories.VitalInventoryListener");

            usingVitalInventories = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.databases.VitalDatabaseManager");

            usingVitalDatabases = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.minigames.VitalMinigameManager");

            usingVitalDatabases = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.xra1ny.vital.utils.VitalUtils");

            usingVitalUtils = true;
        }catch (ClassNotFoundException ignored) {}

        onEnable();

        // scan for all classes annotated with `@VitalAutoRegistered` and attempt to register them on the base manager.
        // NOTE: this configuration is entirely user dependent, if a user wrongly implements their auto registered components,
        // with components that cannot be dependency injected, this registration will not work!!!
        for (Class<? extends VitalComponent> vitalComponentClass : new Reflections().getTypesAnnotatedWith(VitalAutoRegistered.class).stream()
                .filter(VitalComponent.class::isAssignableFrom)
                .map(VitalComponent.class.getClass()::cast)
                .toList()) {
            // assume every class is extended from `VitalComponent` since we filtered in our chain above.
            final Optional<? extends VitalComponent> optionalVitalComponent = VitalDIUtils.getDependencyInjectedInstance(vitalComponentClass);

            // display error if a dependency injected instance of our marked class could not be created, else register it von the base manager of vital.
            if (optionalVitalComponent.isEmpty()) {
                log.severe("Vital attempted to automatically register " + vitalComponentClass + " but failed!");
            } else {
                registerVitalComponent(optionalVitalComponent.get());
            }
        }

        // loop over every registered manager and enable them.
        for (VitalComponentListManager<?> vitalComponentListManager : getVitalComponentList(VitalComponentListManager.class)) {
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

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
