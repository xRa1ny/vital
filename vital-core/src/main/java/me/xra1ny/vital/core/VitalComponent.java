package me.xra1ny.vital.core;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Interface representing a component within the Vital framework.
 * Defines common methods and lifecycle events for Vital components.
 *
 * @author xRa1ny
 */
public interface VitalComponent {
    /**
     * Gets the unique identifier for this component.
     *
     * @return The UUID representing this component.
     */
    @NotNull
    default UUID getUniqueId() {
        return UUID.randomUUID();
    }

    /**
     * Gets the name of this component.
     *
     * @return The name of the component (usually the class name).
     */
    @NotNull
    default String getName() {
        return getClass().getSimpleName();
    }

    /**
     * Called when this VitalComponent is registered.
     * Implement this method to perform any required initialization when the component is registered.
     */
    void onVitalComponentRegistered();

    /**
     * Called when this VitalComponent is unregistered.
     * Implement this method to perform any cleanup or finalization when the component is unregistered.
     */
    void onVitalComponentUnregistered();
}
