package me.xra1ny.vital.samples.standalone.player;

import me.xra1ny.vital.players.VitalPlayerListener;
import me.xra1ny.vital.players.VitalPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VitalSamplePlayerListener extends VitalPlayerListener<VitalSamplePlayer> {
    public VitalSamplePlayerListener(@NotNull JavaPlugin javaPlugin, @NotNull VitalPlayerManager<VitalSamplePlayer> vitalPlayerManager) {
        super(javaPlugin, vitalPlayerManager);
    }

    @Override
    protected Class<VitalSamplePlayer> vitalPlayerType() {
        return VitalSamplePlayer.class;
    }
}
