package me.xra1ny.vital.tasks;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.tasks.annotation.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * Abstract base class for creating repeatable tasks in the Vital plugin framework.
 * Repeatable tasks can be used to execute specific logic at defined intervals.
 *
 * @author xRa1ny
 */
@VitalDI
public abstract class VitalRepeatableTask implements AnnotatedVitalComponent<VitalRepeatableTaskInfo> {
    @Getter
    @NonNull
    private final JavaPlugin javaPlugin;

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    /**
     * The interval at which this repeatable task should execute, in milliseconds.
     */
    @Getter
    private final int interval;

    /**
     * The BukkitRunnable associated with this repeatable task, defining its logic.
     */
    @Getter
    @NonNull
    private BukkitRunnable runnable;

    /**
     * The BukkitTask representing this repeatable task.
     */
    @Getter
    private BukkitTask task;

    /**
     * If true this repeatable tasks tick Method is called.
     * If false, skips tick Method call.
     */
    @Getter
    @Setter
    private boolean allowTick = true;

    /**
     * Creates a new instance of VitalRepeatableTask with the specified JavaPlugin.
     * Using the Information provided by the VitalRepeatableTaskInfo Annotation
     *
     * @param javaPlugin The JavaPlugin instance associated with this task.
     */
    public VitalRepeatableTask(@NonNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;

        final VitalRepeatableTaskInfo vitalRepeatableTaskInfo = getRequiredAnnotation();

        this.interval = vitalRepeatableTaskInfo.value();
    }

    /**
     * Creates a new instance of VitalRepeatableTask with the specified JavaPlugin and interval.
     *
     * @param javaPlugin The JavaPlugin instance associated with this task.
     * @param interval   The interval at which this task should execute, in milliseconds.
     */
    public VitalRepeatableTask(@NonNull JavaPlugin javaPlugin, int interval) {
        this.javaPlugin = javaPlugin;
        this.interval = interval;
    }

    @Override
    public Class<VitalRepeatableTaskInfo> requiredAnnotationType() {
        return VitalRepeatableTaskInfo.class;
    }

    /**
     * Starts this repeatable task. If it's already running, this method has no effect.
     */
    public final void start() {
        if (isRunning()) {
            return;
        }

        onStart();
        this.runnable = new BukkitRunnable() {
            @Override
            public void run() {
                if (!allowTick) {
                    return;
                }

                onTick();
            }
        };
        this.task = this.runnable.runTaskTimer(javaPlugin, 0L, (long) ((this.interval / 1000D) * 20L));
    }

    /**
     * Called when this repeatable task starts.
     */
    public void onStart() {

    }

    /**
     * Stops this repeatable task. If it's not running, this method has no effect.
     */
    public final void stop() {
        if (!isRunning()) {
            return;
        }

        onStop();
        this.task.cancel();
        this.runnable.cancel();
        this.task = null;
        this.runnable = null;
    }

    /**
     * Called when this repeatable task stops.
     */
    public void onStop() {

    }

    /**
     * Checks if this repeatable task is currently running.
     *
     * @return True if the task is running, false otherwise.
     */
    public final boolean isRunning() {
        return this.runnable != null && !this.runnable.isCancelled() && this.task != null && !this.task.isCancelled();
    }

    /**
     * Called whenever the interval of this repeatable task expires.
     */
    public void onTick() {

    }
}

