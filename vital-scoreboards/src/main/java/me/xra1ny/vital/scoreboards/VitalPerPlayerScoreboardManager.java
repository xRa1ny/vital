package me.xra1ny.vital.scoreboards;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.jetbrains.annotations.NotNull;

/**
 * Manages a list of individual per-player scoreboards represented by {@link VitalPerPlayerScoreboard}.
 * This class is responsible for registering and unregistering per-player scoreboards.
 *
 * @author xRa1ny
 */
@Log
public final class VitalPerPlayerScoreboardManager extends VitalComponentListManager<VitalPerPlayerScoreboard> {

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalPerPlayerScoreboardManagement!");
    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalPerPlayerScoreboard vitalPerPlayerScoreboard) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalPerPlayerScoreboard vitalPerPlayerScoreboard) {

    }
}

