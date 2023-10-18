package me.xra1ny.vital;

import lombok.SneakyThrows;
import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.inventories.VitalInventoryMenuListener;
import me.xra1ny.vital.items.VitalItemStackCooldownHandler;
import me.xra1ny.vital.items.VitalItemStackListener;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.players.*;
import me.xra1ny.vital.scoreboards.VitalGlobalScoreboardManager;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboardManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * The Main Instance of Vital
 *
 * @param <T> The JavaPlugin Instance
 * @author xRa1ny
 */
public final class Vital<T extends JavaPlugin> extends VitalCore<T> {
    public Vital(@NotNull T javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public void onEnable() {
        // Register VitalConfigManagement
        final VitalConfigManager vitalConfigManager = new VitalConfigManager();
        final DefaultVitalConfig defaultVitalConfig = new DefaultVitalConfig(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalConfigManager);
        getVitalComponentManager().registerVitalComponent(defaultVitalConfig);

        // Register VitalPlayerManagement
        final DefaultVitalPlayerManager defaultVitalPlayerManager = new DefaultVitalPlayerManager(defaultVitalConfig.getVitalPlayerTimeout());
        final DefaultVitalPlayerListener defaultVitalPlayerListener = new DefaultVitalPlayerListener(getJavaPlugin(), defaultVitalPlayerManager);
        final DefaultVitalPlayerTimeoutHandler defaultVitalPlayerTimeoutHandler = new DefaultVitalPlayerTimeoutHandler(getJavaPlugin(), defaultVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(defaultVitalPlayerManager);
        getVitalComponentManager().registerVitalComponent(defaultVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(defaultVitalPlayerTimeoutHandler);

        // Register VitalCommandManagement
        final VitalCommandManager vitalCommandManager = new VitalCommandManager();

        getVitalComponentManager().registerVitalComponent(vitalCommandManager);

        // Register VitalHologramManagement
        final VitalHologramManager vitalHologramManager = new VitalHologramManager(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalHologramManager);

        // Register VitalItemStackManagement and VitalItemStackCooldownHandler
        final VitalItemStackManager vitalItemStackManager = new VitalItemStackManager(getJavaPlugin());
        final VitalItemStackListener vitalItemStackListener = new VitalItemStackListener(getJavaPlugin(), vitalItemStackManager);
        final VitalItemStackCooldownHandler vitalItemStackCooldownHandler = new VitalItemStackCooldownHandler(getJavaPlugin(), vitalItemStackManager);

        getVitalComponentManager().registerVitalComponent(vitalItemStackManager);
        getVitalComponentManager().registerVitalComponent(vitalItemStackListener);
        getVitalComponentManager().registerVitalComponent(vitalItemStackCooldownHandler);

        // Register VitalInventoryMenuListener
        final VitalInventoryMenuListener vitalInventoryMenuListener = new VitalInventoryMenuListener(getJavaPlugin());

        getVitalComponentManager().registerVitalComponent(vitalInventoryMenuListener);

        // Register VitalPerPlayerScoreboardManagement
        final VitalPerPlayerScoreboardManager vitalPerPlayerScoreboardManager = new VitalPerPlayerScoreboardManager();

        getVitalComponentManager().registerVitalComponent(vitalPerPlayerScoreboardManager);

        // Register VitalGlobalScoreboardManagement
        final VitalGlobalScoreboardManager vitalGlobalScoreboardManager = new VitalGlobalScoreboardManager();

        getVitalComponentManager().registerVitalComponent(vitalGlobalScoreboardManager);
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
    @SneakyThrows
    public <P extends VitalPlayer, M extends VitalPlayerManager<P>, L extends VitalPlayerListener<P>, TH extends VitalPlayerTimeoutHandler<P>> void registerSimpleCustomPlayerManagement(@NotNull Class<P> customVitalPlayerClass, @NotNull Class<M> customVitalPlayerManagerClass, @NotNull Class<L> customVitalPlayerListenerClass, @NotNull Class<TH> customVitalPlayerTimeoutHandlerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final M customVitalPlayerManager = customVitalPlayerManagerClass.getDeclaredConstructor().newInstance();
        final L customVitalPlayerListener = customVitalPlayerListenerClass.getDeclaredConstructor(JavaPlugin.class, VitalPlayerManager.class, int.class).newInstance(getJavaPlugin(), customVitalPlayerManager, customVitalPlayerTimeout);
        final TH customVitalPlayerTimeoutHandler = customVitalPlayerTimeoutHandlerClass.getDeclaredConstructor(JavaPlugin.class, VitalPlayerManager.class).newInstance(getJavaPlugin(), customVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(customVitalPlayerManager);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerTimeoutHandler);
    }

    /**
     * Registers custom VitalPlayerManagement using the specified CustomVitalPlayerClass, instantiating every needed Dependency required for VitalPlayerManagement.
     *
     * @param customVitalPlayerClass The custom VitalPlayer Class.
     * @param customVitalPlayerTimeout The custom VitalPlayerTimeout.
     * @param <P> VitalPlayer
     */
    public <P extends VitalPlayer> void registerSimpleCustomPlayerManagement(@NotNull Class<P> customVitalPlayerClass, int customVitalPlayerTimeout) {
        unregisterDefaultVitalPlayerManagement();

        final CustomVitalPlayerManager<P> customVitalPlayerManager = new CustomVitalPlayerManager<>(customVitalPlayerTimeout);
        final CustomVitalPlayerListener<P> customVitalPlayerListener = new CustomVitalPlayerListener<>(getJavaPlugin(), customVitalPlayerManager, customVitalPlayerClass);
        final CustomVitalPlayerTimeoutHandler<P> customVitalPlayerTimeoutHandler = new CustomVitalPlayerTimeoutHandler<>(getJavaPlugin(), customVitalPlayerManager);

        getVitalComponentManager().registerVitalComponent(customVitalPlayerManager);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerListener);
        getVitalComponentManager().registerVitalComponent(customVitalPlayerTimeoutHandler);
    }
}