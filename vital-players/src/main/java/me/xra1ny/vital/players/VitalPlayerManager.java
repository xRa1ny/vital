package me.xra1ny.vital.players;

import lombok.Getter;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

/**
 * A management class responsible for handling and managing VitalPlayer components associated with players.
 *
 * @author xRa1ny
 */
@Getter
@Log
@VitalDI
public abstract class VitalPlayerManager<T extends VitalPlayer> extends VitalComponentListManager<T> {
    private final int vitalPlayerTimeout;

    /**
     * Constructs a new player manager instance with the specified timeout.
     *
     * @param vitalPlayerTimeout The timeout for each {@link VitalPlayer} instance.
     */
    public VitalPlayerManager(int vitalPlayerTimeout) {
        this.vitalPlayerTimeout = vitalPlayerTimeout;
    }

    @Override
    public final void onRegistered() {
        log.info("VitalPlayerManager online!");
    }

    @Override
    public final @NotNull Class<T> managedType() {
        return (Class<T>) VitalPlayer.class;
    }
}

