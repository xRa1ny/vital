package me.xra1ny.vital.core;

import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.event.Listener;

/**
 * A listener component for handling events in the Vital-Framework.
 *
 * @author xRa1ny
 */
@VitalDI
public class VitalListener implements VitalComponent, Listener {
    /**
     * Called when this {@link VitalListener} is registered.
     */
    @Override
    public void onRegistered() {

    }

    /**
     * Called when this {@link VitalListener} is unregistered.
     */
    @Override
    public void onUnregistered() {

    }
}
