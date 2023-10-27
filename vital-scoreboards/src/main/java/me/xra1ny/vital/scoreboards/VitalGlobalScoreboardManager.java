package me.xra1ny.vital.scoreboards;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.jetbrains.annotations.NotNull;

/**
 * Manages a collection of global scoreboards for use in your plugin.
 * This class is responsible for managing a collection of global scoreboards,
 * allowing easy registration and unregistration of scoreboards in your plugin.
 *
 * @author xRa1ny
 */
@Log
public final class VitalGlobalScoreboardManager extends VitalComponentListManager<VitalGlobalScoreboard> {

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalGlobalScoreboardManagement!");
    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalGlobalScoreboard vitalGlobalScoreboard) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalGlobalScoreboard vitalGlobalScoreboard) {

    }
}

