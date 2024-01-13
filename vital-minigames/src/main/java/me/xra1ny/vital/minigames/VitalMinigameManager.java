package me.xra1ny.vital.minigames;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalDIUtils;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

/**
 * Manages the current state of a minigame using the Vital framework.
 *
 * @apiNote This class may be extended from, to add more specific mini-game manager logic or function, depending on the mini-game you are trying to implement.
 * @author xRa1ny
 */
@Log
@VitalDI
public final class VitalMinigameManager implements VitalComponent {
    private static VitalMinigameManager instance;

    /**
     * The currently active minigame state.
     */
    @Getter
    private VitalMinigameState vitalMinigameState;

    private final JavaPlugin javaPlugin;

    /**
     * Constructs a new minigame manager instance with the specified {@link JavaPlugin}.
     *
     * @param javaPlugin The {@link JavaPlugin}.
     */
    public VitalMinigameManager(@NonNull JavaPlugin javaPlugin) {
        instance = this;
        this.javaPlugin = javaPlugin;
    }

    /**
     * Checks if the current minigame state matches a specified class.
     *
     * @param vitalMinigameStateClass The class of the minigame state to compare with.
     * @param <T>                     The type of minigame state.
     * @return True if the current state is of the specified class, otherwise false.
     */
    @SuppressWarnings("unused")
    public static <T extends VitalMinigameState> boolean isVitalMinigameState(@NonNull Class<T> vitalMinigameStateClass) {
        return vitalMinigameStateClass.equals(instance.vitalMinigameState.getClass());
    }

    /**
     * Sets the current minigame state by Class.
     *
     * @apiNote this method attempts to construct a dependency injected instance using Vital's DI utils {@link VitalDIUtils}
     * @param vitalMinigameStateClass The Class of the minigame state to set to (must be registered).
     */
    public static void setVitalMinigameState(@NonNull Class<? extends VitalMinigameState> vitalMinigameStateClass) {
        final Optional<? extends VitalMinigameState> optionalDiVitalMinigameState = VitalDIUtils.getDependencyInjectedInstance(vitalMinigameStateClass, false);

        optionalDiVitalMinigameState.ifPresent(VitalMinigameManager::setVitalMinigameState);
    }

    /**
     * Sets the current minigame state.
     * If a previous state exists, it is unregistered before registering the new state.
     *
     * @param vitalMinigameState The new minigame state to set.
     */
    @SuppressWarnings("unused")
    public static void setVitalMinigameState(@NonNull VitalMinigameState vitalMinigameState) {
        if (instance.vitalMinigameState != null) {
            if (instance.vitalMinigameState instanceof VitalCountdownMinigameState vitalCountdownMinigameState) {
                vitalCountdownMinigameState.stopCountdown();
            }

            // unregister listener from bukkit.
            HandlerList.unregisterAll(instance.vitalMinigameState);
            instance.vitalMinigameState.onUnregistered();
        }

        instance.vitalMinigameState = vitalMinigameState;
        Bukkit.getPluginManager().registerEvents(vitalMinigameState, instance.javaPlugin);
        vitalMinigameState.onRegistered();
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}