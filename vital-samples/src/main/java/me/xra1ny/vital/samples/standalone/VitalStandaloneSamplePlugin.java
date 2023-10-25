package me.xra1ny.vital.samples.standalone;

import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.holograms.VitalHologram;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.players.DefaultVitalPlayerListener;
import me.xra1ny.vital.players.DefaultVitalPlayerManager;
import me.xra1ny.vital.players.DefaultVitalPlayerTimeoutHandler;
import me.xra1ny.vital.samples.Scoreboard;
import me.xra1ny.vital.samples.item.VitalSampleItemStack;
import me.xra1ny.vital.samples.listener.VitalSampleListener;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerListener;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerManager;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerTimeoutHandler;
import me.xra1ny.vital.scoreboards.VitalGlobalScoreboardManager;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class VitalStandaloneSamplePlugin extends JavaPlugin {
    /**
     * Using the `VitalCore<T>` Class, Vital is initialized with almost to no functionality allowing for maximum customization.
     */
    private final VitalCore<VitalStandaloneSamplePlugin> vitalCore = new VitalCore<>(this) {
        @Override
        public void onEnable() {
            // Here we define any logic to execute when Vital is enabled.
        }
    };

    @Override
    public void onEnable() {
        // Here we enable Vital.
        vitalCore.onEnable();

        // Here we register Vital's default ConfigManager if you want to work with Vital's OOP-driven Config System
        final VitalConfigManager vitalConfigManager = new VitalConfigManager();

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalConfigManager);

        // Here we register Vital's default VitalPlayerManagement if you do not want to use custom VitalPlayer's.
        final DefaultVitalPlayerManager defaultVitalPlayerManager = new DefaultVitalPlayerManager(0);
        final DefaultVitalPlayerListener defaultVitalPlayerListener = new DefaultVitalPlayerListener(this, defaultVitalPlayerManager);
        final DefaultVitalPlayerTimeoutHandler defaultVitalPlayerTimeoutHandler = new DefaultVitalPlayerTimeoutHandler(this, defaultVitalPlayerManager);

        vitalCore.getVitalComponentManager().registerVitalComponent(defaultVitalPlayerManager);
        vitalCore.getVitalComponentManager().registerVitalComponent(defaultVitalPlayerListener);
        vitalCore.getVitalComponentManager().registerVitalComponent(defaultVitalPlayerTimeoutHandler);

        // Here we register a custom VitalUserManagement, so we can use OUR OWN VitalPlayer's.
        final VitalSamplePlayerManager vitalSamplePlayerManager = new VitalSamplePlayerManager(0);
        final VitalSamplePlayerListener vitalSamplePlayerListener = new VitalSamplePlayerListener(this, vitalSamplePlayerManager);
        final VitalSamplePlayerTimeoutHandler vitalSamplePlayerTimeoutHandler = new VitalSamplePlayerTimeoutHandler(this, vitalSamplePlayerManager);

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalSamplePlayerManager);
        vitalCore.getVitalComponentManager().registerVitalComponent(vitalSamplePlayerListener);
        vitalCore.getVitalComponentManager().registerVitalComponent(vitalSamplePlayerTimeoutHandler);

        // Here we register a Listener.
        final VitalSampleListener vitalSampleListener = new VitalSampleListener(this);

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalSampleListener);

        // Here we register the ItemStackManager, so we can register ItemStack's.
        final VitalItemStackManager vitalItemStackManager = new VitalItemStackManager(this);

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalItemStackManager);

        // Here we register an ItemStack.
        final VitalSampleItemStack vitalSampleItemStack = new VitalSampleItemStack();

        vitalItemStackManager.registerVitalComponent(vitalSampleItemStack);

        // Here we register Vital's default ScoreboardManager
        final VitalGlobalScoreboardManager vitalGlobalScoreboardManager = new VitalGlobalScoreboardManager();
        final VitalPerPlayerScoreboardManager vitalPerPlayerScoreboardManager = new VitalPerPlayerScoreboardManager();

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalGlobalScoreboardManager);
        vitalCore.getVitalComponentManager().registerVitalComponent(vitalPerPlayerScoreboardManager);

        // Here we register VitalScoreboard's
        vitalGlobalScoreboardManager.registerVitalComponent(Scoreboard.GLOBAL_SCOREBOARD);
        vitalPerPlayerScoreboardManager.registerVitalComponent(Scoreboard.PER_PLAYER_SCOREBOARD);

        // Here we register a Hologram.
        final VitalHologramManager vitalHologramManager = new VitalHologramManager(this);

        vitalCore.getVitalComponentManager().registerVitalComponent(vitalHologramManager);

        final Location randomLocation = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        final VitalHologram vitalHologram = new VitalHologram("testhologram", randomLocation, Material.STICK, "line1", "line2", "line3", "line4", "line5");

        vitalHologramManager.registerVitalComponent(vitalHologram);
        vitalHologram.update();
    }
}
