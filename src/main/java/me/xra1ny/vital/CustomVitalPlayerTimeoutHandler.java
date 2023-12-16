package me.xra1ny.vital;

import lombok.NonNull;
import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerManager;
import me.xra1ny.vital.players.VitalPlayerTimeoutHandler;
import me.xra1ny.vital.tasks.annotation.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Implementation for any custom {@link VitalPlayer} instance "simply" registered by {@link Vital}.
 *
 * @param <T> The type of {@link VitalPlayer} to manage.
 * @author xRa1ny
 */
@VitalRepeatableTaskInfo(value = 50)
public class CustomVitalPlayerTimeoutHandler<T extends VitalPlayer> extends VitalPlayerTimeoutHandler<T> {
    /**
     * Constructs a VitalPlayerTimeoutHandler instance.
     *
     * @param javaPlugin         The JavaPlugin instance associated with this handler.
     * @param vitalPlayerManager The management component responsible for VitalPlayers.
     */
    public CustomVitalPlayerTimeoutHandler(@NonNull JavaPlugin javaPlugin, @NonNull VitalPlayerManager<T> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }
}
