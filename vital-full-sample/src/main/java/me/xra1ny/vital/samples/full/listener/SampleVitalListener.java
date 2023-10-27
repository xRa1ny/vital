package me.xra1ny.vital.samples.full.listener;

import me.xra1ny.vital.core.VitalListener;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.samples.full.SampleVitalScoreboard;
import me.xra1ny.vital.samples.full.item.SampleVitalItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

public final class SampleVitalListener extends VitalListener {
    /**
     * Here we define any dependencies.
     */
    private final VitalItemStackManager vitalItemStackManager;
    private final VitalHologramManager vitalHologramManager;

    public SampleVitalListener(@NotNull VitalItemStackManager vitalItemStackManager, @NotNull VitalHologramManager vitalHologramManager) {
        this.vitalItemStackManager = vitalItemStackManager;
        this.vitalHologramManager = vitalHologramManager;
    }

    @EventHandler
    public void onPlayerJoinServer(@NotNull PlayerJoinEvent e) {
        SampleVitalScoreboard.PER_PLAYER_SCOREBOARD.add(e.getPlayer());
//        SampleVitalScoreboard.GLOBAL_SCOREBOARD.add(e.getPlayer());

        e.getPlayer().getInventory().clear();

        final SampleVitalItemStack sampleVitalItemStack = new SampleVitalItemStack(vitalHologramManager);

        vitalItemStackManager.registerVitalComponent(sampleVitalItemStack);

        e.getPlayer().getInventory().addItem(sampleVitalItemStack);
    }

    @EventHandler
    public void onPlayerLeaveServer(@NotNull PlayerQuitEvent e) {
        SampleVitalScoreboard.PER_PLAYER_SCOREBOARD.remove(e.getPlayer());
//        SampleVitalScoreboard.GLOBAL_SCOREBOARD.remove(e.getPlayer());
    }
}
