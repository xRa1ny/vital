package me.xra1ny.vital.core;

/**
 * Final class for managing a list of Vital components.
 * Extends the abstract VitalComponentListManagement class.
 *
 * @author xRa1ny
 */
public final class VitalComponentManager extends VitalComponentListManager<VitalComponent> {
    /**
     * Called when a Vital component is registered with this management.
     * Override this method to perform custom actions when a component is registered.
     */
    @Override
    public void onVitalComponentRegistered() {

    }

    /**
     * Called when a Vital component is unregistered from this management.
     * Override this method to perform custom actions when a component is unregistered.
     */
    @Override
    public void onVitalComponentUnregistered() {

    }
}
