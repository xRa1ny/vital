package me.xra1ny.vital.samples.task;

import me.xra1ny.vital.tasks.VitalRepeatableTask;
import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@VitalRepeatableTaskInfo(interval = 1000) // measured in ms
public class VitalSampleTask extends VitalRepeatableTask {
    public VitalSampleTask(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    public VitalSampleTask(@NotNull JavaPlugin javaPlugin, int interval) {
        super(javaPlugin, interval);
    }

    @Override
    public Class<VitalRepeatableTaskInfo> requiredAnnotationType() {
        return VitalRepeatableTaskInfo.class;
    }

    @Override
    public void onVitalComponentRegistered() {

    }

    @Override
    public void onVitalComponentUnregistered() {

    }

    @Override
    public void onStart() {
        // Called when this task starts.
    }

    @Override
    public void onStop() {
        // Called when this task stops.
    }

    @Override
    public void onTick() {
        // Called every task tick, in this case it would be every 1000ms or 1s defined in `@VitalRepeatableTaskInfo(interval)` at the top of this Class.
    }
}
