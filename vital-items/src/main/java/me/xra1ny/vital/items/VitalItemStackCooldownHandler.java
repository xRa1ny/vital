package me.xra1ny.vital.items;

import lombok.Getter;
import lombok.NonNull;
import me.xra1ny.vital.tasks.VitalRepeatableTask;
import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class responsible for managing cooldowns of VitalItemStack items.
 * This class extends VitalRepeatableTask to periodically reduce cooldowns.
 *
 * @author xRa1ny
 */
@VitalRepeatableTaskInfo(value = 50)
public final class VitalItemStackCooldownHandler extends VitalRepeatableTask {
    /**
     * The VitalItemStackManagement instance for managing VitalItemStacks.
     */
    @Getter
    @NonNull
    private final VitalItemStackManager vitalItemStackManager;

    /**
     * Creates a new VitalItemStackCooldownHandler.
     *
     * @param javaPlugin            The JavaPlugin instance.
     * @param vitalItemStackManager The VitalItemStackManagement instance.
     */
    public VitalItemStackCooldownHandler(@NonNull JavaPlugin javaPlugin, @NonNull VitalItemStackManager vitalItemStackManager) {
        super(javaPlugin);

        this.vitalItemStackManager = vitalItemStackManager;
    }

    /**
     * Called when the task starts.
     */
    @Override
    public void onStart() {
        // Initialization or actions when the task starts.
    }

    /**
     * Called when the task stops.
     */
    @Override
    public void onStop() {
        // Cleanup or actions when the task stops.
    }

    /**
     * Called on each tick of the repeatable task.
     * Reduces cooldowns for registered VitalItemStack items.
     */
    @Override
    public void onTick() {
        for (VitalItemStack vitalItemStack : vitalItemStackManager.getVitalComponentList()) {
            for (int i = 0; i < 50; i++) {
                // Reduce Cooldown
                if (vitalItemStack.getCurrentCooldown() <= 0) {
                    continue;
                }

                vitalItemStack.setCurrentCooldown(vitalItemStack.getCurrentCooldown() - 1);
            }
        }
    }

    /**
     * Called when this component is registered.
     * Starts the cooldown handler task.
     */
    @Override
    public void onRegistered() {
        start();
    }

    /**
     * Called when this component is unregistered.
     * Stops the cooldown handler task.
     */
    @Override
    public void onUnregistered() {
        stop();
    }

    /**
     * Specifies the required annotation type for this component.
     *
     * @return The annotation type required for this component.
     */
    @Override
    public Class<VitalRepeatableTaskInfo> requiredAnnotationType() {
        return VitalRepeatableTaskInfo.class;
    }
}

