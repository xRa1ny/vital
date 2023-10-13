package me.xra1ny.vital.players;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;

/**
 * A management class responsible for handling and managing VitalPlayer components associated with players.
 *
 * @author xRa1ny
 */
@Log
public class VitalPlayerManager<T extends VitalPlayer> extends VitalComponentListManager<T> {

    @Override
    public final void onVitalComponentRegistered() {
        log.info("Successfully registered VitalUserManagement!");
    }

    @Override
    public final void onVitalComponentUnregistered() {

    }
}

