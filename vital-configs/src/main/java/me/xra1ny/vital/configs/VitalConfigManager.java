package me.xra1ny.vital.configs;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;

/**
 * Class responsible for managing Vital configuration files.
 * Extends VitalComponentListManagement to handle configuration registration and unregistration.
 *
 * @author xRa1ny
 */
@Log
public final class VitalConfigManager extends VitalComponentListManager<VitalConfig> {

    @Override
    public void onVitalComponentRegistered() {
        log.info("Successfully registered VitalConfigManagement!");
    }


    @Override
    public void onVitalComponentUnregistered() {

    }
}
