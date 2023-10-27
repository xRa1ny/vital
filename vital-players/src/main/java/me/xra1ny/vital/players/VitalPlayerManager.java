package me.xra1ny.vital.players;

import lombok.Getter;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.jetbrains.annotations.NotNull;

/**
 * A management class responsible for handling and managing VitalPlayer components associated with players.
 *
 * @author xRa1ny
 */
@Getter
@Log
public class VitalPlayerManager<T extends VitalPlayer> extends VitalComponentListManager<T> {
    private final int vitalPlayerTimeout;

    public VitalPlayerManager(int vitalPlayerTimeout) {
        this.vitalPlayerTimeout = vitalPlayerTimeout;
    }

    @Override
    public final void onRegistered() {
        log.info("Successfully registered VitalUserManagement!");
    }

    @Override
    public final void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull T vitalPlayer) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull T vitalPlayer) {

    }
}

