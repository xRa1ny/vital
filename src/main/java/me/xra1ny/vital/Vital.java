package me.xra1ny.vital;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.core.VitalDIUtils;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.databases.VitalDatabaseManager;
import me.xra1ny.vital.holograms.VitalHologramConfig;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.inventories.VitalInventoryListener;
import me.xra1ny.vital.items.VitalItemStackCooldownHandler;
import me.xra1ny.vital.items.VitalItemStackListener;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.players.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Optional;

/**
 * The main class of the Vital-Framework.
 *
 * @param <T> Your plugins main class.
 * @author xRa1ny
 * @apiNote This class is used for a complete package of Vital.
 */
@SuppressWarnings("unused")
public final class Vital<T extends JavaPlugin> extends VitalCore<T> {
    /**
     * Constructs a new instance of the Vital-Framework.
     *
     * @param javaPlugin The {@link JavaPlugin} this Vital-Framework instance belongs to.
     */
    public Vital(@NonNull T javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEnable() {
        int vitalPlayerTimeout = 0;
        boolean vitalDatabaseEnabled = false;

        // register config and hologram management.
        if (isUsingVitalConfigs()) {
            final VitalConfigManager vitalConfigManager = new VitalConfigManager();
            final DefaultVitalConfig defaultVitalConfig = new DefaultVitalConfig(getJavaPlugin());

            registerVitalComponent(vitalConfigManager);
            vitalConfigManager.registerVitalComponent(defaultVitalConfig);

            vitalPlayerTimeout = defaultVitalConfig.vitalPlayerTimeout;
            vitalDatabaseEnabled = defaultVitalConfig.vitalDatabaseEnabled;

            // register hologram management.
            if(isUsingVitalHolograms()) {
                // Register VitalHologramManagement
                final VitalHologramConfig vitalHologramConfig = new VitalHologramConfig(getJavaPlugin());
                final VitalHologramManager vitalHologramManager = new VitalHologramManager(getJavaPlugin(), vitalHologramConfig);

                vitalConfigManager.registerVitalComponent(vitalHologramConfig);
                registerVitalComponent(vitalHologramManager);
            }
        }

        // register player management.
        if(isUsingVitalPlayers()) {
            final List<VitalPlayerManager> vitalPlayerManagerList = getVitalComponentList(VitalPlayerManager.class);

            if (vitalPlayerManagerList.isEmpty()) {
                final VitalListenerManager vitalListenerManager = getVitalListenerManager().get();
                final DefaultVitalPlayerManager defaultVitalPlayerManager = new DefaultVitalPlayerManager(vitalPlayerTimeout);
                final DefaultVitalPlayerListener defaultVitalPlayerListener = new DefaultVitalPlayerListener(defaultVitalPlayerManager);
                final DefaultVitalPlayerTimeoutHandler defaultVitalPlayerTimeoutHandler = new DefaultVitalPlayerTimeoutHandler(getJavaPlugin(), defaultVitalPlayerManager);

                registerVitalComponent(defaultVitalPlayerManager);
                vitalListenerManager.registerVitalComponent(defaultVitalPlayerListener);
                registerVitalComponent(defaultVitalPlayerTimeoutHandler);
            }
        }

        // register command management.
        if(isUsingVitalCommands()) {
            final VitalCommandManager vitalCommandManager = new VitalCommandManager(getJavaPlugin());

            registerVitalComponent(vitalCommandManager);
        }

        // register item management.
        if(isUsingVitalItems()) {
            // Register VitalItemStackManagement and VitalItemStackCooldownHandler
            final VitalListenerManager vitalListenerManager = getVitalListenerManager().get();
            final VitalItemStackManager vitalItemStackManager = new VitalItemStackManager();
            final VitalItemStackCooldownHandler vitalItemStackCooldownHandler = new VitalItemStackCooldownHandler(getJavaPlugin(), vitalItemStackManager);
            final VitalItemStackListener vitalItemStackListener = new VitalItemStackListener(vitalItemStackManager);

            registerVitalComponent(vitalItemStackManager);
            registerVitalComponent(vitalItemStackCooldownHandler);
            vitalListenerManager.registerVitalComponent(vitalItemStackListener);
        }

        // register inventory management.
        if(isUsingVitalInventories()) {
            // Register VitalInventoryMenuListener
            final VitalListenerManager vitalListenerManager = getVitalListenerManager().get();
            final VitalInventoryListener vitalInventoryListener = new VitalInventoryListener();

            vitalListenerManager.registerVitalComponent(vitalInventoryListener);
        }

        // register database management.
        if(isUsingVitalDatabases()) {
            if (vitalDatabaseEnabled) {
                // Register VitalDatabaseManager
                final VitalDatabaseManager vitalDatabaseManager = new VitalDatabaseManager();

                registerVitalComponent(vitalDatabaseManager);
            }
        }

        // register mini game management.
        try {
            final Class<? extends VitalComponent> vitalMinigameManagerClass = (Class<? extends VitalComponent>) Class.forName("me.xra1ny.vital.minigames.VitalMinigameManager");

            // call the constructor, so it registers itself using singleton pattern.
            VitalDIUtils.getDependencyInjectedInstance(vitalMinigameManagerClass);
        } catch (ClassNotFoundException ignored) {}
    }

    /**
     * Unregisters Vital's default player management system.
     *
     * @apiNote This method is implemented for convenience for registering custom player management.
     */
    public void unregisterDefaultVitalPlayerManagement() {
        final Optional<DefaultVitalPlayerManager> optionalDefaultVitalPlayerManager = getVitalComponent(DefaultVitalPlayerManager.class);
        final Optional<DefaultVitalPlayerListener> optionalDefaultVitalPlayerListener = getVitalComponent(DefaultVitalPlayerListener.class);
        final Optional<DefaultVitalPlayerTimeoutHandler> optionalDefaultVitalPlayerTimeoutHandler = getVitalComponent(DefaultVitalPlayerTimeoutHandler.class);

        optionalDefaultVitalPlayerManager.ifPresent(this::unregisterVitalComponent);
        optionalDefaultVitalPlayerListener.ifPresent(this::unregisterVitalComponent);
        optionalDefaultVitalPlayerTimeoutHandler.ifPresent(this::unregisterVitalComponent);
    }

    /**
     * Registers custom player management, unregistering default player management if not already.
     *
     * @param customVitalPlayerClass               The custom {@link VitalPlayer} class.
     * @param customVitalPlayerManagerClass        The custom {@link VitalPlayerManager} class.
     * @param customVitalPlayerListenerClass       The custom {@link VitalPlayerListener} class.
     * @param customVitalPlayerTimeoutHandlerClass The custom {@link VitalPlayerTimeoutHandler} class.
     * @param customVitalPlayerTimeout             The custom player timeout.
     * @param <P>                                  {@link VitalPlayer}
     * @param <M>                                  {@link VitalPlayerManager}
     * @param <L>                                  {@link VitalPlayerListener}
     * @param <TH>                                 {@link VitalPlayerTimeoutHandler}
     * @apiNote The specified VitalPlayerManagement Classes MUST contain the DefaultVitalPlayerManagement Constructors.
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @SneakyThrows
    public <P extends VitalPlayer, M extends VitalPlayerManager<P>, L extends VitalPlayerListener<P>, TH extends VitalPlayerTimeoutHandler<P>> void registerSimpleCustomPlayerManagement(@NonNull Class<P> customVitalPlayerClass, @NonNull Class<M> customVitalPlayerManagerClass, @NonNull Class<L> customVitalPlayerListenerClass, @NonNull Class<TH> customVitalPlayerTimeoutHandlerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final VitalListenerManager vitalListenerManager = getVitalComponent(VitalListenerManager.class).get();
        final M customVitalPlayerManager = customVitalPlayerManagerClass.getDeclaredConstructor(int.class).newInstance(customVitalPlayerTimeout);
        final L customVitalPlayerListener = customVitalPlayerListenerClass.getDeclaredConstructor(customVitalPlayerManagerClass, customVitalPlayerClass.getClass()).newInstance(customVitalPlayerManager, customVitalPlayerClass);
        final TH customVitalPlayerTimeoutHandler = customVitalPlayerTimeoutHandlerClass.getDeclaredConstructor(JavaPlugin.class, customVitalPlayerManagerClass).newInstance(getJavaPlugin(), customVitalPlayerManager);

        registerVitalComponent(customVitalPlayerManager);
        vitalListenerManager.registerVitalComponent(customVitalPlayerListener);
        registerVitalComponent(customVitalPlayerTimeoutHandler);
    }

    /**
     * Registers custom player management using the specified {@link VitalPlayer} class, instantiating every needed dependency required for player management.
     *
     * @param customVitalPlayerClass   The custom {@link VitalPlayer} class.
     * @param customVitalPlayerTimeout The custom player timeout.
     * @param <P>                      {@link VitalPlayer}
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public <P extends VitalPlayer> void registerSimpleCustomPlayerManagement(@NonNull Class<P> customVitalPlayerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final VitalListenerManager vitalListenerManager = getVitalComponent(VitalListenerManager.class).get();
        final CustomVitalPlayerManager<P> customVitalPlayerManager = new CustomVitalPlayerManager<>(customVitalPlayerTimeout);
        final CustomVitalPlayerListener<P> customVitalPlayerListener = new CustomVitalPlayerListener<>(customVitalPlayerManager, customVitalPlayerClass);
        final CustomVitalPlayerTimeoutHandler<P> customVitalPlayerTimeoutHandler = new CustomVitalPlayerTimeoutHandler<>(getJavaPlugin(), customVitalPlayerManager);

        registerVitalComponent(customVitalPlayerManager);
        vitalListenerManager.registerVitalComponent(customVitalPlayerListener);
        registerVitalComponent(customVitalPlayerTimeoutHandler);
    }

    /**
     * Attempts to fetch the {@link VitalConfigManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalConfigManager> getVitalConfigManager() {
        return getVitalComponent(VitalConfigManager.class);
    }

    /**
     * Attempts to fetch the {@link VitalPlayerListener} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalListenerManager> getVitalListenerManager() {
        return getVitalComponent(VitalListenerManager.class);
    }

    /**
     * Attempts to fetch the {@link DefaultVitalPlayerManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<DefaultVitalPlayerManager> getDefaultVitalPlayerManager() {
        return getVitalComponent(DefaultVitalPlayerManager.class);
    }

    /**
     * Attempts to fetch the {@link DefaultVitalPlayerListener} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<DefaultVitalPlayerListener> getDefaultVitalPlayerListener() {
        return getVitalComponent(DefaultVitalPlayerListener.class);
    }

    /**
     * Attempts to fetch the {@link DefaultVitalPlayerTimeoutHandler} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<DefaultVitalPlayerTimeoutHandler> getDefaultVitalPlayerTimeoutHandler() {
        return getVitalComponent(DefaultVitalPlayerTimeoutHandler.class);
    }

    /**
     * Attempts to fetch the {@link CustomVitalPlayerManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerManager> getCustomVitalPlayerManager() {
        return getVitalComponent(CustomVitalPlayerManager.class);
    }

    /**
     * Attempts to fetch the {@link CustomVitalPlayerListener} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerListener> getCustomVitalPlayerListener() {
        return getVitalComponent(CustomVitalPlayerListener.class);
    }

    /**
     * Attempts to fetch the {@link CustomVitalPlayerTimeoutHandler} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerTimeoutHandler> getCustomVitalPlayerTimeoutHandler() {
        return getVitalComponent(CustomVitalPlayerTimeoutHandler.class);
    }

    /**
     * Attempts to fetch the {@link VitalCommandManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalCommandManager> getVitalCommandManager() {
        return getVitalComponent(VitalCommandManager.class);
    }

    /**
     * Attempts to fetch the {@link VitalHologramManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalHologramManager> getVitalHologramManager() {
        return getVitalComponent(VitalHologramManager.class);
    }

    /**
     * Attempts to fetch the {@link VitalItemStackManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalItemStackManager> getVitalItemStackManager() {
        return getVitalComponent(VitalItemStackManager.class);
    }

    /**
     * Attempts to fetch the {@link VitalDatabaseManager} from Vital.
     *
     * @return An {@link Optional} holding either the fetched value; or empty.
     */
    public Optional<VitalDatabaseManager> getVitalDatabaseManager() {
        return getVitalComponent(VitalDatabaseManager.class);
    }

    /**
     * Gets this generic {@link Vital} instance.
     *
     * @return This generic {@link Vital} instance.
     */
    public static Vital<?> getVitalInstance() {
        return (Vital<?>) getVitalCoreInstance();
    }

    /**
     * Gets this {@link Vital} instance with the {@link JavaPlugin} type specified.
     *
     * @param type The type of your {@link JavaPlugin} implementation.
     * @param <T>  The type of your {@link JavaPlugin}. implementation.
     * @return This generic accurate {@link Vital} instance.
     */
    public static <T extends JavaPlugin> Vital<T> getVitalInstance(@NonNull Class<T> type) {
        return (Vital<T>) getVitalCoreInstance(type);
    }
}