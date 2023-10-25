package me.xra1ny.vital.samples.listener;

import me.xra1ny.vital.core.VitalListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class VitalSampleListener extends VitalListener {
    public VitalSampleListener(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @EventHandler
    public void onPlayerJoinServer(@NotNull PlayerJoinEvent e) {
        //
    }

    @EventHandler
    public void onPlayerLeaveServer(@NotNull PlayerQuitEvent e) {
        //
    }
}
