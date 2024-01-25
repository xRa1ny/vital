package me.xra1ny.vital.tasks;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.tasks.annotation.VitalCountdownTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;

public class VitalCountdownTask implements AnnotatedVitalComponent<VitalCountdownTaskInfo> {
    private VitalRepeatableTask vitalRepeatableTask;

    @Getter
    private final int initialCountdown;

    @Getter
    @Setter
    private int countdown;

    public VitalCountdownTask(@NonNull JavaPlugin javaPlugin) {
        final VitalCountdownTaskInfo vitalCountdownTaskInfo = getRequiredAnnotation();

        this.initialCountdown = vitalCountdownTaskInfo.value();
        this.countdown = this.initialCountdown;

        run(javaPlugin, vitalCountdownTaskInfo.interval());
    }


    public VitalCountdownTask(@NonNull JavaPlugin javaPlugin, int interval, int countdown) {
        this.initialCountdown = countdown;
        this.countdown = this.initialCountdown;

        run(javaPlugin, interval);
    }

    @Override
    public Class<VitalCountdownTaskInfo> requiredAnnotationType() {
        return VitalCountdownTaskInfo.class;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @NonNull
    public JavaPlugin getJavaPlugin() {
        return vitalRepeatableTask.getJavaPlugin();
    }

    /**
     * Sets up the countdown task using a VitalRepeatableTask.
     *
     * @param javaPlugin The JavaPlugin instance.
     * @param interval   The countdown update interval.
     */
    private void run(@NonNull JavaPlugin javaPlugin, int interval) {
        vitalRepeatableTask = new VitalRepeatableTask(javaPlugin, interval) {
            @Override
            public void onStart() {
                VitalCountdownTask.this.onStart();
            }

            @Override
            public void onStop() {
                VitalCountdownTask.this.onStop();
            }

            @Override
            public void onTick() {
                if (countdown <= 0) {
                    stop();
                    onExpire();

                    return;
                }

                VitalCountdownTask.this.onTick();

                countdown--;
            }
        };
    }

    /**
     * Starts the countdown.
     */
    @SuppressWarnings("unused")
    public final void start() {
        vitalRepeatableTask.start();
    }

    /**
     * Stops the countdown.
     */
    public final void stop() {
        vitalRepeatableTask.stop();
    }

    /**
     * Resets the countdown to its initial value, restarting its responsible {@link VitalRepeatableTask}.
     */
    public final void restart() {
        vitalRepeatableTask.stop();
        countdown = initialCountdown;
        vitalRepeatableTask.start();
        onRestart();
    }

    /**
     * Called when the countdown starts.
     */
    public void onStart() {

    }

    /**
     * Called on each countdown tick.
     */
    public void onTick() {

    }

    /**
     * Called when the countdown stops.
     */
    public void onStop() {

    }

    /**
     * Called when the countdown expires.
     */
    public void onExpire() {

    }

    /**
     * Called when the countdown is told to reset
     *
     * @see VitalCountdownTask#restart()
     */
    public void onRestart() {

    }
}