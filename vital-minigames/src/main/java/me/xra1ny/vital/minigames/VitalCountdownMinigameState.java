package me.xra1ny.vital.minigames;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.tasks.VitalRepeatableTask;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * An abstract class for countdown-based minigame states in the Vital framework.
 * Extends the VitalMinigameState class and implements the AnnotatedVitalComponent interface
 * with the VitalCountdownMinigameStateInfo annotation.
 */
public abstract class VitalCountdownMinigameState extends VitalMinigameState implements AnnotatedVitalComponent<VitalCountdownMinigameStateInfo> {
    private VitalRepeatableTask vitalRepeatableTask;

    @Getter
    private final int initialCountdown;

    @Getter
    @Setter
    private int countdown;

    /**
     * Constructor for VitalCountdownMinigameState with the default interval from the annotation.
     *
     * @param javaPlugin The JavaPlugin instance.
     */
    @SuppressWarnings("unused")
    public VitalCountdownMinigameState(@NotNull JavaPlugin javaPlugin) {
        final VitalCountdownMinigameStateInfo vitalCountdownMinigameStateInfo = getRequiredAnnotation();

        this.initialCountdown = getRequiredAnnotation().countdown();
        this.countdown = this.initialCountdown;

        run(javaPlugin, vitalCountdownMinigameStateInfo.interval());
    }

    /**
     * Constructor for VitalCountdownMinigameState with custom interval and countdown values.
     *
     * @param javaPlugin The JavaPlugin instance.
     * @param interval   The countdown update interval.
     * @param countdown  The initial countdown value.
     */
    @SuppressWarnings("unused")
    public VitalCountdownMinigameState(@NotNull JavaPlugin javaPlugin, int interval, int countdown) {
        this.initialCountdown = countdown;
        this.countdown = this.initialCountdown;

        run(javaPlugin, interval);
    }

    /**
     * Sets up the countdown task using a VitalRepeatableTask.
     *
     * @param javaPlugin The JavaPlugin instance.
     * @param interval   The countdown update interval.
     */
    private void run(@NotNull JavaPlugin javaPlugin, int interval) {
        vitalRepeatableTask = new VitalRepeatableTask(javaPlugin, interval) {
            @Override
            public void onStart() {
                onCountdownStart();
            }

            @Override
            public void onStop() {
                onCountdownStop();
            }

            @Override
            public void onTick() {
                if(countdown <= 0) {
                    stop();
                    onCountdownExpire();

                    return;
                }

                onCountdownTick();

                countdown--;
            }
        };
    }

    @Override
    public final Class<VitalCountdownMinigameStateInfo> requiredAnnotationType() {
        return VitalCountdownMinigameStateInfo.class;
    }

    /**
     * Start the countdown.
     */
    @SuppressWarnings("unused")
    public final void startCountdown() {
        vitalRepeatableTask.start();
    }

    /**
     * Stop the countdown.
     */
    public final void stopCountdown() {
        vitalRepeatableTask.stop();
    }

    /**
     * Called when the countdown starts.
     */
    public void onCountdownStart() {

    }

    /**
     * Called on each countdown tick.
     */
    public void onCountdownTick() {

    }

    /**
     * Called when the countdown stops.
     */
    public void onCountdownStop() {

    }

    /**
     * Called when the countdown expires.
     */
    public void onCountdownExpire() {

    }
}
