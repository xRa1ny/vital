package me.xra1ny.vital.minigames;

import lombok.Getter;
import me.xra1ny.vital.core.VitalComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Manages the current state of a minigame using the Vital framework.
 */
public final class VitalMinigameManager implements VitalComponent {
    /**
     * The currently active minigame state.
     */
    @Getter(onMethod = @__(@Nullable))
    private VitalMinigameState vitalMinigameState;

    /**
     * Checks if the current minigame state matches a specified class.
     *
     * @param vitalMinigameStateClass The class of the minigame state to compare with.
     * @param <T> The type of minigame state.
     * @return True if the current state is of the specified class, otherwise false.
     */
    public <T extends VitalMinigameState> boolean isVitalMinigameState(@NotNull Class<T> vitalMinigameStateClass) {
        return vitalMinigameStateClass.equals(vitalMinigameState.getClass());
    }

    /**
     * Sets the current minigame state.
     * If a previous state exists, it is unregistered before registering the new state.
     *
     * @param vitalMinigameState The new minigame state to set.
     */
    public void setVitalMinigameState(@NotNull VitalMinigameState vitalMinigameState) {
        if (this.vitalMinigameState != null) {
            if(this.vitalMinigameState instanceof VitalCountdownMinigameState vitalCountdownMinigameState) {
                vitalCountdownMinigameState.stopCountdown();
            }

            this.vitalMinigameState.onVitalComponentUnregistered();
        }

        vitalMinigameState.onVitalComponentRegistered();
        this.vitalMinigameState = vitalMinigameState;
    }

    @Override
    public void onVitalComponentRegistered() {

    }

    @Override
    public void onVitalComponentUnregistered() {

    }
}