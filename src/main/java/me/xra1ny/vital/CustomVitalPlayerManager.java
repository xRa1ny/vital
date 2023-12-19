package me.xra1ny.vital;

import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerManager;

/**
 * Implementation for {@link Vital} "simple" registered custom {@link VitalPlayer} instances.
 *
 * @param <T> The type of {@link VitalPlayer} to manage.
 * @author xRa1ny
 */
@VitalDI
public class CustomVitalPlayerManager<T extends VitalPlayer> extends VitalPlayerManager<T> {
    /**
     * Constructs a new player manager with the custom supplied {@link VitalPlayer} type of this class.
     *
     * @param vitalPlayerTimeout The session timeout of each {@link VitalPlayer} instance.
     */
    public CustomVitalPlayerManager(int vitalPlayerTimeout) {
        super(vitalPlayerTimeout);
    }
}
