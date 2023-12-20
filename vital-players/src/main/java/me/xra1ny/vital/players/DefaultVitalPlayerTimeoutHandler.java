package me.xra1ny.vital.players;

import lombok.NonNull;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.tasks.annotation.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * The default player timeout handler implementation that handles {@link VitalPlayer} session timeouts within the Vital-Framework.
 *
 * @author xRa1ny
 */
@VitalDI
@VitalAutoRegistered
@VitalRepeatableTaskInfo(value = 50)
public final class DefaultVitalPlayerTimeoutHandler extends VitalPlayerTimeoutHandler<VitalPlayer> {
    /**
     * Constructs a VitalPlayerTimeoutHandler instance.
     *
     * @param javaPlugin         The JavaPlugin instance associated with this handler.
     * @param vitalPlayerManager The management component responsible for VitalPlayers.
     */
    public DefaultVitalPlayerTimeoutHandler(@NonNull JavaPlugin javaPlugin, @NonNull VitalPlayerManager<VitalPlayer> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }

    @Override
    public void onRegistered() {
        start();
    }
}
