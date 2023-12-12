package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.extern.java.Log;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

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
        for (Class<?> vitalComponentClass : new Reflections().getTypesAnnotatedWith(VitalAutoRegistered.class).stream().filter(VitalComponent.class::isAssignableFrom).toList()) {
            // assume every class is extended from `VitalComponent` since we filtered in our chain above.
            final Optional<? extends VitalComponent> optionalVitalComponent = (Optional<? extends VitalComponent>) DIUtils.getDependencyInjectedInstance(vitalComponentClass);

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

    /**
     * Broadcasts a message to all players currently connected to the server.
     * Identical to {@link Bukkit#broadcastMessage(String)}.
     * This method is supplied for convenience.
     *
     * @param message The message to broadcast.
     */
    public static void broadcast(@NotNull String message) {
        Bukkit.broadcastMessage(message);
    }

    /**
     * Broadcasts a message to all players currently connected to the server, matching the given {@link Predicate}.
     *
     * @param message         The message to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the message should be broadcast.
     */
    public static void broadcast(@NotNull String message, @NotNull Predicate<Player> playerPredicate) {
        for (Player player : Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .toList()) {
            player.sendMessage(message);
        }
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     * volume: 1f, pitch: 1f.
     *
     * @param sound The sound to broadcast.
     */
    public static void broadcast(@NotNull Sound sound) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, sound, 1f, 1f);
        }
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the sound should be broadcast.
     */
    public static void broadcast(@NotNull Sound sound, @NotNull Predicate<Player> playerPredicate) {
        for (Player player : Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .toList()) {
            player.playSound(player, sound, 1f, 1f);
        }
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     *
     * @param sound  The sound to broadcast.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void broadcast(@NotNull Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, sound, volume, pitch);
        }
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the sound is broadcast.
     * @param volume          The volume of the sound.
     * @param pitch           The pitch of the sound.
     */
    public static void broadcast(@NotNull Sound sound, @NotNull Predicate<Player> playerPredicate, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .toList()) {
            player.playSound(player, sound, volume, pitch);
        }
    }
}
