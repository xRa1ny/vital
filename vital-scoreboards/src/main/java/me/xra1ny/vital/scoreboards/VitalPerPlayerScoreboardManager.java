package me.xra1ny.vital.scoreboards;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;

/**
 * Manages a list of individual per-player scoreboards represented by {@link VitalPerPlayerScoreboard}.
 * This class is responsible for registering and unregistering per-player scoreboards.
 *
 * @author xRa1ny
 */
@Log
public final class VitalPerPlayerScoreboardManager extends VitalComponentListManager<VitalPerPlayerScoreboard> {

    @Override
    public void onVitalComponentRegistered() {
        log.info("Successfully registered VitalPerPlayerScoreboardManagement!");
    }

    @Override
    public void onVitalComponentUnregistered() {

    }
}

