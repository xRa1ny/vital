package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.reflections.Reflections;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

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
        onEnable();

        // hold all scanned and ready for dependency injected classes.
        final Map<Class<? extends VitalComponent>, Integer> prioritisedDependencyInjectableClasses = new HashMap<>();

        // scan for all classes annotated with `@VitalAutoRegistered` and attempt to register them on the base manager.
        // NOTE: this configuration is entirely user dependent, if a user wrongly implements their auto registered components,
        // with components that cannot be dependency injected, this registration will not work!!!
        for (Class<?> vitalComponentClass : new Reflections().getTypesAnnotatedWith(VitalAutoRegistered.class).stream().filter(VitalComponent.class::isAssignableFrom).toList()) {
            // assume every class is extended from `VitalComponent` since we filtered in our chain above.
            final Optional<? extends VitalComponent> optionalVitalComponent = (Optional<? extends VitalComponent>) VitalDIUtils.getDependencyInjectedInstance(vitalComponentClass);

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

    /**
     * Broadcasts a message to all players currently connected to the server.
     * Identical to {@link Bukkit#broadcastMessage(String)}.
     * This method is supplied for convenience.
     *
     * @param message The message to broadcast.
     */
    public static void broadcastMessage(@NonNull String message) {
        Bukkit.broadcastMessage(message);
    }

    /**
     * Broadcasts a message to all players currently connected to the server, matching the given {@link Predicate}.
     *
     * @param message         The message to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the message should be broadcast.
     */
    public static void broadcastMessage(@NonNull String message, @NonNull Predicate<Player> playerPredicate) {
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
    public static void broadcastSound(@NonNull Sound sound) {
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
    public static void broadcastSound(@NonNull Sound sound, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .toList().forEach(player -> player.playSound(player, sound, 1f, 1f));
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     *
     * @param sound  The sound to broadcast.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void broadcastSound(@NonNull Sound sound, float volume, float pitch) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.playSound(player, sound, volume, pitch);
        }
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param volume          The volume of the sound.
     * @param pitch           The pitch of the sound.
     * @param playerPredicate The Predicate specifying the condition in which the sound is broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound, float volume, float pitch, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .toList().forEach(player -> player.playSound(player, sound, volume, pitch));
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle);
        }
    }

    /**
     * Broadcasts a title to all players currently connected to this server, matching the given {@link Predicate}
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .forEach(player -> player.sendTitle(title, subtitle));
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     * @param fadeIn   The fade-in amount (in ticks).
     * @param stay     The stay amount (in ticks).
     * @param fadeOut  The fade-out amount (in ticks).
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
        }
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param fadeIn          The fade-in amount (in ticks).
     * @param stay            The stay amount (in ticks).
     * @param fadeOut         The fade-out amount (in ticks).
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .forEach(player -> player.sendTitle(title, subtitle, fadeIn, stay, fadeOut));
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     * @param fade     The fade amount used for both fade-in and fade-out (in ticks).
     * @param stay     The stay amount (in ticks).
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fade, int stay) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(title, subtitle, fade, stay, fade);
        }
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param fade            The fade amount used for both fade-in and fade-out (in ticks).
     * @param stay            The stay amount (in ticks).
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fade, int stay, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .forEach(player -> player.sendTitle(title, subtitle, fade, stay, fade));
    }
}
