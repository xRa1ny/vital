package me.xra1ny.vital.players;

import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@VitalRepeatableTaskInfo(value = 50)
public final class DefaultVitalPlayerTimeoutHandler extends VitalPlayerTimeoutHandler<VitalPlayer> {
    /**
     * Constructs a VitalPlayerTimeoutHandler instance.
     *
     * @param javaPlugin            The JavaPlugin instance associated with this handler.
     * @param vitalPlayerManager The management component responsible for VitalPlayers.
     */
    public DefaultVitalPlayerTimeoutHandler(@NotNull JavaPlugin javaPlugin, @NotNull VitalPlayerManager<VitalPlayer> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }
}
