package me.xra1ny.vital.players;

import lombok.NonNull;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The default {@link VitalPlayer} listener of the Vital-Framework.
 *
 * @author xRa1ny
 */
public final class DefaultVitalPlayerListener extends VitalPlayerListener<VitalPlayer> {
    /**
     * Creates a new instance of VitalPlayerListener.
     *
     * @param javaPlugin         The JavaPlugin instance associated with the listener.
     * @param vitalPlayerManager The VitalUserManagement instance to manage VitalPlayer components.
     */
    public DefaultVitalPlayerListener(@NonNull JavaPlugin javaPlugin, @NonNull VitalPlayerManager<VitalPlayer> vitalPlayerManager) {
        super(vitalPlayerManager);
    }

    @Override
    protected Class<VitalPlayer> vitalPlayerType() {
        return VitalPlayer.class;
    }
}
