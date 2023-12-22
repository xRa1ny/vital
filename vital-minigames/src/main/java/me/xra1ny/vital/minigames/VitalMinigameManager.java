package me.xra1ny.vital.minigames;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalDIUtils;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.core.annotation.VitalDI;

import java.util.Optional;

/**
 * Manages the current state of a minigame using the Vital framework.
 *
 * @apiNote This class may be extended from, to add more specific mini-game manager logic or function, depending on the mini-game you are trying to implement.
 * @author xRa1ny
 */
@Log
@VitalDI
public class VitalMinigameManager {
    private static VitalMinigameManager instance;
    private final VitalListenerManager vitalListenerManager;

    /**
     * The currently active minigame state.
     */
    @Getter
    private VitalMinigameState vitalMinigameState;

    /**
     * Constructs a new minigame manager instance with the specified {@link VitalListenerManager}.
     *
     * @param vitalListenerManager The {@link VitalListenerManager}.
     */
    public VitalMinigameManager(@NonNull VitalListenerManager vitalListenerManager) {
        this.vitalListenerManager = vitalListenerManager;
        instance = this;
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
        final Optional<? extends VitalMinigameState> optionalDiVitalMinigameState = VitalDIUtils.getDependencyInjectedInstance(vitalMinigameStateClass);

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

            instance.vitalListenerManager.unregisterVitalComponent(instance.vitalMinigameState);
        }

        instance.vitalListenerManager.registerVitalComponent(vitalMinigameState);
        instance.vitalMinigameState = vitalMinigameState;
    }
}