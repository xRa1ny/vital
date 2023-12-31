package me.xra1ny.vital.minigames.annotation;

import me.xra1ny.vital.minigames.VitalCountdownMinigameState;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for defining countdown minigame state information for implementations of {@link VitalCountdownMinigameState}.
 *
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalCountdownMinigameStateInfo {
    /**
     * Gets the initial countdown value for the minigame state.
     *
     * @return The initial countdown value.
     */
    int value();

    /**
     * Gets the time interval between countdown updates in ticks.
     *
     * @return The countdown update interval.
     */
    int interval() default 1_000;
}
