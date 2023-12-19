package me.xra1ny.vital.players;

import me.xra1ny.vital.core.annotation.VitalDI;

/**
 * The default manager implementation that manages {@link VitalPlayer} instances of the Vital-Framework.
 *
 * @author xRa1ny
 */
@VitalDI
public final class DefaultVitalPlayerManager extends VitalPlayerManager<VitalPlayer> {
    /**
     * Constructs a new default player manager managing {@link VitalPlayer} instances.
     *
     * @param vitalPlayerTimeout The timeout for each player instance.
     */
    public DefaultVitalPlayerManager(int vitalPlayerTimeout) {
        super(vitalPlayerTimeout);
    }
}
