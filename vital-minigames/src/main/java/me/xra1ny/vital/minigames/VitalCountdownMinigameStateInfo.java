package me.xra1ny.vital.minigames;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining countdown minigame state information.
 * This annotation is used to specify the time interval between countdown updates
 * and the initial countdown value for a countdown-based minigame state.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalCountdownMinigameStateInfo {
    /**
     * Gets the time interval between countdown updates in ticks.
     *
     * @return The countdown update interval.
     */
    int interval();

    /**
     * Gets the initial countdown value for the minigame state.
     *
     * @return The initial countdown value.
     */
    int countdown();
}
