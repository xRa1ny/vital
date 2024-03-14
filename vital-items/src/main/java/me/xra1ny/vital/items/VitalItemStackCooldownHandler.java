package me.xra1ny.vital.items;

import lombok.NonNull;
import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.tasks.VitalRepeatableTask;
import me.xra1ny.vital.tasks.annotation.VitalRepeatableTaskInfo;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

/**
 * A class responsible for managing cooldowns of VitalItemStack items.
 * This class extends VitalRepeatableTask to periodically reduce cooldowns.
 *
 * @author xRa1ny
 */
@Component
@VitalRepeatableTaskInfo(value = 50)
public final class VitalItemStackCooldownHandler extends VitalRepeatableTask {
    private final VitalCore<?> vitalCore;

    /**
     * Creates a new VitalItemStackCooldownHandler.
     *
     * @param javaPlugin            The JavaPlugin instance.
     */
    public VitalItemStackCooldownHandler(@NonNull JavaPlugin javaPlugin, VitalCore<?> vitalCore) {
        super(javaPlugin);

        this.vitalCore = vitalCore;
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
        for (VitalItemStack vitalItemStack : vitalCore.getComponentsByType(VitalItemStack.class)) {
            // Reduce Cooldown
            for (Map.Entry<Player, Integer> entry : vitalItemStack.getPlayerCooldownMap().entrySet()) {
                if (entry.getValue() <= 0) {
                    continue;
                }

                vitalItemStack.getPlayerCooldownMap().put(entry.getKey(), entry.getValue() - 50);

                if(vitalItemStack.equals(entry.getKey().getInventory().getItemInMainHand())) {
                    vitalItemStack.onCooldownTick(entry.getKey());
                }

                if (vitalItemStack.getCooldown(entry.getKey()) <= 0) {
                    // cooldown has expired, call on expired.
                    vitalItemStack.onCooldownExpire(entry.getKey());
                }
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

