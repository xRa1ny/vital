package me.xra1ny.vital.configs;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;

/**
 * Class responsible for managing {@link VitalConfig}.
 *
 * @author xRa1ny
 */
@Log
public final class VitalConfigManager extends VitalComponentListManager<VitalConfig> {

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalConfigManagement!");
    }


    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NonNull VitalConfig vitalConfig) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalConfig vitalConfig) {

    }

    @Override
    public Class<VitalConfig> managedType() {
        return VitalConfig.class;
    }
}
