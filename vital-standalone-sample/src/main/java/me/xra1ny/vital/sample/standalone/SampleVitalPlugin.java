package me.xra1ny.vital.sample.standalone;

import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.databases.VitalDatabaseManager;
import me.xra1ny.vital.holograms.VitalHologramConfig;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import org.bukkit.plugin.java.JavaPlugin;

public class SampleVitalPlugin extends JavaPlugin {
    /**
     * Using this Implementation, Vital is initialized with minimal functionality, every module MUST be manually implemented if needed.
     */
    private final VitalCore<SampleVitalPlugin> vitalCore = new VitalCore<>(this) {
        @Override
        public void onEnable() {
            // Here we register Vital's CommandManager
            final VitalCommandManager vitalCommandManager = new VitalCommandManager(getJavaPlugin());

            getVitalComponentManager().registerVitalComponent(vitalCommandManager);

            // Here we register Vital's ListenerManager
            final VitalListenerManager vitalListenerManager = new VitalListenerManager(getJavaPlugin());

            getVitalComponentManager().registerVitalComponent(vitalListenerManager);

            // Here we register Vital's ItemStackManager
            final VitalItemStackManager vitalItemStackManager = new VitalItemStackManager(getJavaPlugin());

            getVitalComponentManager().registerVitalComponent(vitalItemStackManager);

            // Here we register Vital's HologramManager
            final VitalConfigManager vitalConfigManager = new VitalConfigManager();
            final VitalHologramConfig vitalHologramConfig = new VitalHologramConfig(getJavaPlugin());
            final VitalHologramManager vitalHologramManager = new VitalHologramManager(getJavaPlugin(), vitalHologramConfig);

            getVitalComponentManager().registerVitalComponent(vitalHologramManager);

            // Here we register Vital's DatabaseManager
            final VitalDatabaseManager vitalDatabaseManager = new VitalDatabaseManager();

            getVitalComponentManager().registerVitalComponent(vitalDatabaseManager);

            // You get the gist, when using this approach you manually have to implement all of Vital's functionality which allows you to fully control which aspect of Vital is present in your project.
            // If you need more help regarding actually implementing functionality with Vital, follow the examples in the `vital-full-sample` module
            // The Implementation of functionality does not differ when using either the full package of vital or the manual implementation explained in this class.
            // All implementations will always be the same in principle.
        }
    };

    @Override
    public void onEnable() {
        // Here we enable Vital
        vitalCore.enable();
    }
}
