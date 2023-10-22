package me.xra1ny.vital.minigames;

import me.xra1ny.vital.core.VitalListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Abstract base class for minigame states within the Vital framework.
 * Minigame states define specific phases or conditions in a minigame.
 * Extend this class to create custom minigame states.
 */
public abstract class VitalMinigameState extends VitalListener {

    /**
     * Constructor for BaseVitalMinigameState.
     *
     * @param javaPlugin The JavaPlugin instance associated with the minigame state.
     */
    public VitalMinigameState(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public final void onVitalComponentRegistered() {
        super.onVitalComponentRegistered();
        onVitalMinigameStateRegistered();
    }

    @Override
    public final void onVitalComponentUnregistered() {
        super.onVitalComponentUnregistered();
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