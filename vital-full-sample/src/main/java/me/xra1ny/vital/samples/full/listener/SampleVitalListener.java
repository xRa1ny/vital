package me.xra1ny.vital.samples.full.listener;

import lombok.NonNull;
import me.xra1ny.vital.core.VitalListener;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.samples.full.SampleVitalScoreboard;
import me.xra1ny.vital.samples.full.config.SampleVitalConfig;
import me.xra1ny.vital.samples.full.item.SampleVitalItemStack;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public final class SampleVitalListener extends VitalListener {
    /**
     * Here we define any dependencies.
     */
    private final VitalItemStackManager vitalItemStackManager;
    private final VitalHologramManager vitalHologramManager;
    private final SampleVitalConfig sampleVitalConfig;

    public SampleVitalListener(@NonNull VitalItemStackManager vitalItemStackManager, @NonNull VitalHologramManager vitalHologramManager, @NonNull SampleVitalConfig sampleVitalConfig) {
        this.vitalItemStackManager = vitalItemStackManager;
        this.vitalHologramManager = vitalHologramManager;
        this.sampleVitalConfig = sampleVitalConfig;
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onPlayerJoinServer(@NonNull PlayerJoinEvent e) {
        SampleVitalScoreboard.PER_PLAYER_SCOREBOARD.addPlayer(e.getPlayer());
//        SampleVitalScoreboard.GLOBAL_SCOREBOARD.add(e.getPlayer());

        e.getPlayer().getInventory().clear();

        final SampleVitalItemStack sampleVitalItemStack = new SampleVitalItemStack(vitalHologramManager, sampleVitalConfig);

        vitalItemStackManager.registerVitalComponent(sampleVitalItemStack);

        e.getPlayer().getInventory().addItem(sampleVitalItemStack);

        e.getPlayer().sendMessage(ChatColor.YELLOW + "currently registered holograms: " + ChatColor.RESET + vitalHologramManager.getVitalComponentList());
        e.getPlayer().sendMessage(ChatColor.YELLOW + "currently in CONFIG saved hologram: " + ChatColor.RESET + sampleVitalConfig.sampleVitalHologramConfigField);
        e.getPlayer().sendMessage(ChatColor.YELLOW + "currently in CONFIG saved hologram list: " + ChatColor.RESET + sampleVitalConfig.sampleVitalHologramListConfigField);
    }

    @EventHandler
    public void onPlayerLeaveServer(@NonNull PlayerQuitEvent e) {
        SampleVitalScoreboard.PER_PLAYER_SCOREBOARD.removePlayer(e.getPlayer());
//        SampleVitalScoreboard.GLOBAL_SCOREBOARD.remove(e.getPlayer());
    }
}
