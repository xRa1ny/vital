package me.xra1ny.vital.players;

import lombok.NonNull;
import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.core.annotation.VitalManagerAutoRegistered;

/**
 * The default {@link VitalPlayer} listener of the Vital-Framework.
 *
 * @author xRa1ny
 */
@VitalDI
@VitalManagerAutoRegistered
public final class DefaultVitalPlayerListener extends VitalPlayerListener<VitalPlayer> {
    /**
     * Creates a new instance of VitalPlayerListener.
     *
     * @param vitalPlayerManager The VitalUserManagement instance to manage VitalPlayer components.
     */
    public DefaultVitalPlayerListener(@NonNull VitalPlayerManager<VitalPlayer> vitalPlayerManager) {
        super(vitalPlayerManager);
    }

    @Override
    protected Class<VitalPlayer> vitalPlayerType() {
        return VitalPlayer.class;
    }
}
