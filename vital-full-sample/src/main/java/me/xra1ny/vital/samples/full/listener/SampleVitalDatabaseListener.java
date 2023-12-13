package me.xra1ny.vital.samples.full.listener;

import lombok.NonNull;
import me.xra1ny.vital.core.VitalListener;
import me.xra1ny.vital.samples.full.persistence.service.SampleVitalPlayerService;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class SampleVitalDatabaseListener extends VitalListener {
    private final SampleVitalPlayerService sampleVitalPlayerService;

    public SampleVitalDatabaseListener(@NonNull SampleVitalPlayerService sampleVitalPlayerService) {
        this.sampleVitalPlayerService = sampleVitalPlayerService;
    }

    @EventHandler
    public void onPlayerJoinServer(@NonNull PlayerJoinEvent e) {
        if (sampleVitalPlayerService.vitalPlayerExistsById(e.getPlayer().getUniqueId())) {
            e.getPlayer().sendMessage("player already exists in db!");

            return;
        }

        e.getPlayer().sendMessage("player does not exist in db yet!");
        e.getPlayer().sendMessage("creating player...");

        sampleVitalPlayerService.createVitalPlayerEntity(e.getPlayer().getUniqueId(), e.getPlayer().getName(), 0, 0);

        e.getPlayer().sendMessage("player successfully created in db!");
    }

    @EventHandler
    public void onPlayerLeaveServer(@NonNull PlayerQuitEvent e) {
        sampleVitalPlayerService.removeVitalPlayerEntityById(e.getPlayer().getUniqueId());
    }
}
