package me.xra1ny.vital.samples.full;

import me.xra1ny.vital.Vital;
import me.xra1ny.vital.holograms.VitalHologram;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.samples.Scoreboard;
import me.xra1ny.vital.samples.inventory.VitalSampleInventoryMenu;
import me.xra1ny.vital.samples.item.VitalSampleItemStack;
import me.xra1ny.vital.samples.listener.VitalSampleListener;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayer;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerListener;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerManager;
import me.xra1ny.vital.samples.standalone.player.VitalSamplePlayerTimeoutHandler;
import me.xra1ny.vital.scoreboards.VitalGlobalScoreboardManager;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;

public class VitalFullSamplePlugin extends JavaPlugin {
    /**
     * Using the `Vital<T>` Class, Vital is initialized with default functionality many Plugins might need.
     * <p>
     * If single modules are not needed and not wanted, you can unregister them manually.
     */
    private final Vital<VitalFullSamplePlugin> vital = new Vital<>(this);

    @Override
    public void onEnable() {
        // Here we enable Vital.
        vital.enable();

        // Here we register custom VitalPlayerManagement if we want to work with custom VitalPlayer's
        vital.registerSimpleCustomPlayerManagement(VitalSamplePlayer.class, 0);

        // Or, if we want to use custom PlayerManagement as well without having the need to manually unregister default Systems and all that BS.
        vital.registerSimpleCustomPlayerManagement(VitalSamplePlayer.class, VitalSamplePlayerManager.class, VitalSamplePlayerListener.class, VitalSamplePlayerTimeoutHandler.class, 0);

        // Here we register a Listener.
        final VitalSampleListener vitalSampleListener = new VitalSampleListener(this);

        vital.getVitalComponentManager().registerVitalComponent(vitalSampleListener);

        // Here we register an InventoryMenu.
        final VitalSampleInventoryMenu vitalSampleInventoryMenu = new VitalSampleInventoryMenu(null);

        vital.getVitalComponentManager().registerVitalComponent(vitalSampleInventoryMenu);

        // Here we register an ItemStack.
        final VitalItemStackManager vitalItemStackManager = vital.getVitalComponentManager().getVitalComponent(VitalItemStackManager.class).get();
        final VitalSampleItemStack vitalSampleItemStack = new VitalSampleItemStack();

        vitalItemStackManager.registerVitalComponent(vitalSampleItemStack);

        // Here we register VitalScoreboard's
        final VitalGlobalScoreboardManager vitalGlobalScoreboardManager = vital.getVitalComponentManager().getVitalComponent(VitalGlobalScoreboardManager.class).get();
        final VitalPerPlayerScoreboardManager vitalPerPlayerScoreboardManager = vital.getVitalComponentManager().getVitalComponent(VitalPerPlayerScoreboardManager.class).get();

        vitalGlobalScoreboardManager.registerVitalComponent(Scoreboard.GLOBAL_SCOREBOARD);
        vitalPerPlayerScoreboardManager.registerVitalComponent(Scoreboard.PER_PLAYER_SCOREBOARD);

        // Here we register a Hologram.
        final VitalHologramManager vitalHologramManager = vital.getVitalComponentManager().getVitalComponent(VitalHologramManager.class).get();
        final Location randomLocation = new Location(Bukkit.getWorld("world"), 0, 0, 0);
        final VitalHologram vitalHologram = new VitalHologram("testhologram", randomLocation, Material.STICK, "line1", "line2", "line3", "line4", "line5");

        vitalHologramManager.registerVitalComponent(vitalHologram);
        vitalHologram.update();
    }
}
