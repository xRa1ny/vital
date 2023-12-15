package me.xra1ny.vital.minigames;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalAutoRegistered;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalDI;
import me.xra1ny.vital.core.VitalListenerManager;

import java.util.Optional;

/**
 * Manages the current state of a minigame using the Vital framework.
 */
@Log
@VitalAutoRegistered
@VitalDI
public final class VitalMinigameManager implements VitalComponent {
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
    }

    /**
     * Checks if the current minigame state matches a specified class.
     *
     * @param vitalMinigameStateClass The class of the minigame state to compare with.
     * @param <T>                     The type of minigame state.
     * @return True if the current state is of the specified class, otherwise false.
     */
    @SuppressWarnings("unused")
    public <T extends VitalMinigameState> boolean isVitalMinigameState(@NonNull Class<T> vitalMinigameStateClass) {
        return vitalMinigameStateClass.equals(vitalMinigameState.getClass());
    }

    /**
     * Sets the current minigame state by Class.
     * NOTE: this method attempts to construct a dependency injected instance using Vital's DI utils (@link VitalDIUtils)
     *
     * @param vitalMinigameStateClass The Class of the minigame state to set to (must be registered).
     */
    public void setVitalMinigameState(@NotNull Class<? extends VitalMinigameState> vitalMinigameStateClass) {
        final Optional<? extends VitalMinigameState> optionalDiVitalMinigameState = VitalDIUtils.getDependencyInjectedInstance(vitalMinigameStateClass);

        optionalDiVitalMinigameState.ifPresent(this::setVitalMinigameState);
    }

    /**
     * Sets the current minigame state.
     * If a previous state exists, it is unregistered before registering the new state.
     *
     * @param vitalMinigameState The new minigame state to set.
     */
    @SuppressWarnings("unused")
    public void setVitalMinigameState(@NonNull VitalMinigameState vitalMinigameState) {
        if (this.vitalMinigameState != null) {
            if (this.vitalMinigameState instanceof VitalCountdownMinigameState vitalCountdownMinigameState) {
                vitalCountdownMinigameState.stopCountdown();
            }

            vitalListenerManager.unregisterVitalComponent(this.vitalMinigameState);
            this.vitalMinigameState.onUnregistered();
        }

        vitalListenerManager.registerVitalComponent(vitalMinigameState);
        vitalMinigameState.onRegistered();
        this.vitalMinigameState = vitalMinigameState;
    }

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalMinigameManagement!");
    }

    @Override
    public void onUnregistered() {

    }
}