package me.xra1ny.vital;

import lombok.SneakyThrows;
import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.databases.VitalDatabaseManager;
import me.xra1ny.vital.holograms.VitalHologramConfig;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.inventories.VitalInventoryListener;
import me.xra1ny.vital.items.VitalItemStackCooldownHandler;
import me.xra1ny.vital.items.VitalItemStackListener;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.players.*;
import me.xra1ny.vital.scoreboards.VitalGlobalScoreboardManager;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Main Instance of Vital
 *
 * @param <T> The JavaPlugin Instance
 * @author xRa1ny
 */
@SuppressWarnings("unused")
public final class Vital<T extends JavaPlugin> extends VitalCore<T> {
    private static final List<Vital<?>> vitalList = new ArrayList<>();

    public Vital(@NotNull T javaPlugin) {
        super(javaPlugin);

        vitalList.add(this);
    }

    @Override
    public void onEnable() {
        // Register VitalConfigManagement
        final VitalConfigManager vitalConfigManager = new VitalConfigManager();
        final DefaultVitalConfig defaultVitalConfig = new DefaultVitalConfig(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalConfigManager);
        getVitalComponentManager().registerVitalComponent(defaultVitalConfig);

        // Register VitalListenerManager
        final VitalListenerManager vitalListenerManager = new VitalListenerManager(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalListenerManager);

        // Register VitalPlayerManagement
        final DefaultVitalPlayerManager defaultVitalPlayerManager = new DefaultVitalPlayerManager(defaultVitalConfig.vitalPlayerTimeout);
        final DefaultVitalPlayerListener defaultVitalPlayerListener = new DefaultVitalPlayerListener(getJavaPlugin(), defaultVitalPlayerManager);
        final DefaultVitalPlayerTimeoutHandler defaultVitalPlayerTimeoutHandler = new DefaultVitalPlayerTimeoutHandler(getJavaPlugin(), defaultVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(defaultVitalPlayerManager);
        vitalListenerManager.registerVitalComponent(defaultVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(defaultVitalPlayerTimeoutHandler);

        // Register VitalCommandManagement
        final VitalCommandManager vitalCommandManager = new VitalCommandManager(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalCommandManager);

        // Register VitalHologramManagement
        final VitalHologramConfig vitalHologramConfig = new VitalHologramConfig("holograms.yml", getJavaPlugin());
        final VitalHologramManager vitalHologramManager = new VitalHologramManager(getJavaPlugin(), vitalHologramConfig);

        getVitalComponentManager().registerVitalComponent(vitalHologramManager);

        // Register VitalItemStackManagement and VitalItemStackCooldownHandler
        final VitalItemStackManager vitalItemStackManager = new VitalItemStackManager(getJavaPlugin());
        final VitalItemStackListener vitalItemStackListener = new VitalItemStackListener(vitalItemStackManager);
        final VitalItemStackCooldownHandler vitalItemStackCooldownHandler = new VitalItemStackCooldownHandler(getJavaPlugin(), vitalItemStackManager);

        getVitalComponentManager().registerVitalComponent(vitalItemStackManager);
        vitalListenerManager.registerVitalComponent(vitalItemStackListener);
        getVitalComponentManager().registerVitalComponent(vitalItemStackCooldownHandler);

        // Register VitalInventoryMenuListener
        final VitalInventoryListener vitalInventoryListener = new VitalInventoryListener();

        vitalListenerManager.registerVitalComponent(vitalInventoryListener);

        // Register VitalPerPlayerScoreboardManagement
        final VitalPerPlayerScoreboardManager vitalPerPlayerScoreboardManager = new VitalPerPlayerScoreboardManager();

        getVitalComponentManager().registerVitalComponent(vitalPerPlayerScoreboardManager);

        // Register VitalGlobalScoreboardManagement
        final VitalGlobalScoreboardManager vitalGlobalScoreboardManager = new VitalGlobalScoreboardManager();

        getVitalComponentManager().registerVitalComponent(vitalGlobalScoreboardManager);

        if(defaultVitalConfig.vitalDatabaseEnabled) {
            // Register VitalDatabaseManager
            final VitalDatabaseManager vitalDatabaseManager = new VitalDatabaseManager();

            getVitalComponentManager().registerVitalComponent(vitalDatabaseManager);
        }
    }

    /**
     * Unregisters Vital's default VitalPlayerManagement System, allowing for custom VitalPlayerManagement.
     */
    public void unregisterDefaultVitalPlayerManagement() {
        final Optional<DefaultVitalPlayerManager> optionalDefaultVitalPlayerManager = getVitalComponentManager().getVitalComponent(DefaultVitalPlayerManager.class);
        final Optional<DefaultVitalPlayerListener> optionalDefaultVitalPlayerListener = getVitalComponentManager().getVitalComponent(DefaultVitalPlayerListener.class);
        final Optional<DefaultVitalPlayerTimeoutHandler> optionalDefaultVitalPlayerTimeoutHandler = getVitalComponentManager().getVitalComponent(DefaultVitalPlayerTimeoutHandler.class);

        optionalDefaultVitalPlayerManager.ifPresent(getVitalComponentManager()::unregisterVitalComponent);
        optionalDefaultVitalPlayerListener.ifPresent(getVitalComponentManager()::unregisterVitalComponent);
        optionalDefaultVitalPlayerTimeoutHandler.ifPresent(getVitalComponentManager()::unregisterVitalComponent);
    }

    /**
     * Registers custom VitalPlayerManagement using the specified Classes, unregistering DefaultVitalPlayerManagement is not already.
     * NOTE: The specified VitalPlayerManagement Classes MUST contain the DefaultVitalPlayerManagement Constructors.
     *
     * @param customVitalPlayerClass The custom VitalPlayer Class.
     * @param customVitalPlayerManagerClass The custom VitalPlayerManager Class.
     * @param customVitalPlayerListenerClass The custom VitalPlayerListener Class.
     * @param customVitalPlayerTimeoutHandlerClass The custom VitalPlayerTimeoutHandler Class.
     * @param customVitalPlayerTimeout The custom VitalPlayerTimeout.
     * @param <P> VitalPlayer
     * @param <M> VitalPlayerManager
     * @param <L> VitalPlayerListener
     * @param <TH> VitalPlayerTimeoutHandler
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @SneakyThrows
    public <P extends VitalPlayer, M extends VitalPlayerManager<P>, L extends VitalPlayerListener<P>, TH extends VitalPlayerTimeoutHandler<P>> void registerSimpleCustomPlayerManagement(@NotNull Class<P> customVitalPlayerClass, @NotNull Class<M> customVitalPlayerManagerClass, @NotNull Class<L> customVitalPlayerListenerClass, @NotNull Class<TH> customVitalPlayerTimeoutHandlerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final VitalListenerManager vitalListenerManager = getVitalComponentManager().getVitalComponent(VitalListenerManager.class).get();
        final M customVitalPlayerManager = customVitalPlayerManagerClass.getDeclaredConstructor().newInstance();
        final L customVitalPlayerListener = customVitalPlayerListenerClass.getDeclaredConstructor(JavaPlugin.class, VitalPlayerManager.class, int.class).newInstance(getJavaPlugin(), customVitalPlayerManager, customVitalPlayerTimeout);
        final TH customVitalPlayerTimeoutHandler = customVitalPlayerTimeoutHandlerClass.getDeclaredConstructor(JavaPlugin.class, VitalPlayerManager.class).newInstance(getJavaPlugin(), customVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(customVitalPlayerManager);
        vitalListenerManager.registerVitalComponent(customVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerTimeoutHandler);
    }

    /**
     * Registers custom VitalPlayerManagement using the specified CustomVitalPlayerClass, instantiating every needed Dependency required for VitalPlayerManagement.
     *
     * @param customVitalPlayerClass The custom VitalPlayer Class.
     * @param customVitalPlayerTimeout The custom VitalPlayerTimeout.
     * @param <P> VitalPlayer
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    public <P extends VitalPlayer> void registerSimpleCustomPlayerManagement(@NotNull Class<P> customVitalPlayerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final VitalListenerManager vitalListenerManager = getVitalComponentManager().getVitalComponent(VitalListenerManager.class).get();
        final CustomVitalPlayerManager<P> customVitalPlayerManager = new CustomVitalPlayerManager<>(customVitalPlayerTimeout);
        final CustomVitalPlayerListener<P> customVitalPlayerListener = new CustomVitalPlayerListener<>(getJavaPlugin(), customVitalPlayerManager, customVitalPlayerClass);
        final CustomVitalPlayerTimeoutHandler<P> customVitalPlayerTimeoutHandler = new CustomVitalPlayerTimeoutHandler<>(getJavaPlugin(), customVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(customVitalPlayerManager);
        vitalListenerManager.registerVitalComponent(customVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerTimeoutHandler);
    }

    public Optional<VitalConfigManager> getVitalConfigManager() {
        return getVitalComponentManager().getVitalComponent(VitalConfigManager.class);
    }

    public Optional<VitalListenerManager> getVitalListenerManager() {
        return getVitalComponentManager().getVitalComponent(VitalListenerManager.class);
    }

    public Optional<DefaultVitalPlayerManager> getDefaultVitalPlayerManager() {
        return getVitalComponentManager().getVitalComponent(DefaultVitalPlayerManager.class);
    }

    public Optional<DefaultVitalPlayerListener> getDefaultVitalPlayerListener() {
        return getVitalComponentManager().getVitalComponent(DefaultVitalPlayerListener.class);
    }

    public Optional<DefaultVitalPlayerTimeoutHandler> getDefaultVitalPlayerTimeoutHandler() {
        return getVitalComponentManager().getVitalComponent(DefaultVitalPlayerTimeoutHandler.class);
    }

    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerManager> getCustomVitalPlayerManager() {
        return getVitalComponentManager().getVitalComponent(CustomVitalPlayerManager.class);
    }

    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerListener> getCustomVitalPlayerListener() {
        return getVitalComponentManager().getVitalComponent(CustomVitalPlayerListener.class);
    }

    @SuppressWarnings("rawtypes")
    public Optional<CustomVitalPlayerTimeoutHandler> getCustomVitalPlayerTimeoutHandler() {
        return getVitalComponentManager().getVitalComponent(CustomVitalPlayerTimeoutHandler.class);
    }

    public Optional<VitalCommandManager> getVitalCommandManager() {
        return getVitalComponentManager().getVitalComponent(VitalCommandManager.class);
    }

    public Optional<VitalHologramManager> getVitalHologramManager() {
        return getVitalComponentManager().getVitalComponent(VitalHologramManager.class);
    }

    public Optional<VitalItemStackManager> getVitalItemStackManager() {
        return getVitalComponentManager().getVitalComponent(VitalItemStackManager.class);
    }

    public Optional<VitalPerPlayerScoreboardManager> getVitalPerPlayerScoreboardManager() {
        return getVitalComponentManager().getVitalComponent(VitalPerPlayerScoreboardManager.class);
    }

    public Optional<VitalGlobalScoreboardManager> getVitalGlobalScoreboardManager() {
        return getVitalComponentManager().getVitalComponent(VitalGlobalScoreboardManager.class);
    }

    public Optional<VitalDatabaseManager> getVitalDatabaseManager() {
        return getVitalComponentManager().getVitalComponent(VitalDatabaseManager.class);
    }

    /**
     * Singleton access-point for all `Vital<T>` Instances.
     *
     * @param type Your Plugin's Main Class.
     * @return An Optional holding either the Vital Instance tied to the specified Main Class, or null.
     * @param <T> The Type of your Plugin's Main Class.
     */
    @SuppressWarnings("unchecked")
    public static <T extends JavaPlugin> Optional<Vital<T>> getVitalInstance(@NotNull Class<T> type) {
        return vitalList.stream()
                .filter(vital -> vital.getJavaPlugin().getClass().equals(type))
                .map(vital -> (Vital<T>) vital)
                .findAny();
    }
}