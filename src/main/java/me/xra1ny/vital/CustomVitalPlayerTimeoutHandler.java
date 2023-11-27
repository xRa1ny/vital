package me.xra1ny.vital;

import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerManager;
import me.xra1ny.vital.players.VitalPlayerTimeoutHandler;
import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@VitalRepeatableTaskInfo(value = 50)
public class CustomVitalPlayerTimeoutHandler<T extends VitalPlayer> extends VitalPlayerTimeoutHandler<T> {
    /**
     * Constructs a VitalPlayerTimeoutHandler instance.
     *
     * @param javaPlugin         The JavaPlugin instance associated with this handler.
     * @param vitalPlayerManager The management component responsible for VitalPlayers.
     */
    public CustomVitalPlayerTimeoutHandler(@NotNull JavaPlugin javaPlugin, @NotNull VitalPlayerManager<T> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }
}
