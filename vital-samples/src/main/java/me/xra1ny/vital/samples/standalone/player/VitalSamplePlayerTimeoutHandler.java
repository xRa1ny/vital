package me.xra1ny.vital.samples.standalone.player;

import me.xra1ny.vital.players.VitalPlayerManager;
import me.xra1ny.vital.players.VitalPlayerTimeoutHandler;
import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@VitalRepeatableTaskInfo(interval = 50)
public class VitalSamplePlayerTimeoutHandler extends VitalPlayerTimeoutHandler<VitalSamplePlayer> {
    public VitalSamplePlayerTimeoutHandler(@NotNull JavaPlugin javaPlugin, @NotNull VitalPlayerManager<VitalSamplePlayer> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }
}
