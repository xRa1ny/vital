package me.xra1ny.vital;

import lombok.NonNull;
import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerListener;
import me.xra1ny.vital.players.VitalPlayerManager;

/**
 * Defines a custom player listener for the specified {@link VitalPlayer} type.
 *
 * @param <T> Your {@link VitalPlayer} type.
 * @author xRa1ny
 */
public class CustomVitalPlayerListener<T extends VitalPlayer> extends VitalPlayerListener<T> {
    private final Class<T> customVitalPlayerClass;

    /**
     * Creates a new instance of VitalPlayerListener.
     *
     * @param vitalPlayerManager     The {@link VitalPlayerManager} instance to manage {@link VitalPlayer} components.
     * @param customVitalPlayerClass The {@link VitalPlayer} to manage.
     */
    public CustomVitalPlayerListener(@NonNull VitalPlayerManager<T> vitalPlayerManager, @NonNull Class<T> customVitalPlayerClass) {
        super(vitalPlayerManager);

        this.customVitalPlayerClass = customVitalPlayerClass;
    }

    @Override
    protected final Class<T> vitalPlayerType() {
        return customVitalPlayerClass;
    }
}
