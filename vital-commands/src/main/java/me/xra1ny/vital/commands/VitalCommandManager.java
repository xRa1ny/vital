package me.xra1ny.vital.commands;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;

/**
 * Class responsible for managing Vital commands.
 * Extends VitalComponentListManagement to handle command registration and unregistration.
 *
 * @author xRa1ny
 */
@Log
public final class VitalCommandManager extends VitalComponentListManager<VitalCommand> {

    @Override
    public void onVitalComponentRegistered() {
        log.info("Successfully registered VitalCommandManagement!");
    }

    @Override
    public void onVitalComponentUnregistered() {

    }
}
