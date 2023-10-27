package me.xra1ny.vital.minigames;

import me.xra1ny.vital.core.VitalListener;

/**
 * Abstract base class for minigame states within the Vital framework.
 * Minigame states define specific phases or conditions in a minigame.
 * Extend this class to create custom minigame states.
 */
public abstract class VitalMinigameState extends VitalListener {
    @Override
    public final void onRegistered() {
        onVitalMinigameStateRegistered();
    }

    @Override
    public final void onUnregistered() {
        super.onUnregistered();
        onVitalMinigameStateUnregistered();
    }

    /**
     * Abstract method to be overridden in the subclass.
     * Implement this method to define the specific setup or initialization logic for this minigame state when it's registered.
     */
    public abstract void onVitalMinigameStateRegistered();

    /**
     * Abstract method to be overridden in the subclass.
     * Implement this method to define the specific cleanup or resource release logic for this minigame state when it's unregistered.
     */
    public abstract void onVitalMinigameStateUnregistered();
}