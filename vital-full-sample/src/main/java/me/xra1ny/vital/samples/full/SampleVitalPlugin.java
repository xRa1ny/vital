package me.xra1ny.vital.samples.full;

import me.xra1ny.vital.DefaultVitalConfig;
import me.xra1ny.vital.Vital;
import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.configs.VitalConfigManager;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.databases.VitalDatabaseManager;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.samples.full.command.SampleVitalCommand;
import me.xra1ny.vital.samples.full.config.SampleVitalConfig;
import me.xra1ny.vital.samples.full.listener.SampleVitalDatabaseListener;
import me.xra1ny.vital.samples.full.listener.SampleVitalListener;
import me.xra1ny.vital.samples.full.persistence.database.SampleVitalDatabase;
import me.xra1ny.vital.samples.full.persistence.repository.SampleVitalPlayerRepository;
import me.xra1ny.vital.samples.full.persistence.service.SampleVitalPlayerService;
import org.bukkit.plugin.java.JavaPlugin;

public final class SampleVitalPlugin extends JavaPlugin {
    /**
     * Using this implementation, Vital is initialized with maximum functionality, containing all of Vital's content.
     *
     * @apiNote When using this approach, you may also unregister certain modules of {@link Vital} that you do not need if you like. Let's say, you don't need to use the `vital-commands` Module, you can simply unregister it when your plugin enables if you like...
     */
    private final Vital<SampleVitalPlugin> vital = new Vital<>(this);

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void onEnable() {
        /*
         * TODO: NOTE: Consider using the `dependency-injection` pattern, when working with Vital, to organize and keep complex code modular.
         */

        // Here we enable Vital
        vital.enable();

        final VitalConfigManager vitalConfigManager = vital.getVitalComponent(VitalConfigManager.class).get();
        final SampleVitalConfig sampleVitalConfig = new SampleVitalConfig(this);

        vitalConfigManager.registerVitalComponent(sampleVitalConfig);

        final DefaultVitalConfig defaultVitalConfig = vital.getVitalComponent(DefaultVitalConfig.class).get();

        if (defaultVitalConfig.vitalDatabaseEnabled) {
            final VitalDatabaseManager vitalDatabaseManager = vital.getVitalComponent(VitalDatabaseManager.class).get();
            final SampleVitalDatabase sampleVitalDatabase = new SampleVitalDatabase();
            final SampleVitalPlayerRepository sampleVitalPlayerRepository = new SampleVitalPlayerRepository(sampleVitalDatabase);

            // Here we register a new repository for our Database.
            sampleVitalDatabase.registerVitalComponent(sampleVitalPlayerRepository);

            // Here we register our new Database.
            vitalDatabaseManager.registerVitalComponent(sampleVitalDatabase);

            // Here we register a new service for our SampleVitalPlayerRepository.
            final SampleVitalPlayerService sampleVitalPlayerService = new SampleVitalPlayerService(sampleVitalPlayerRepository);

            // Now we can inject the SampleVitalPlayerService into any class instance that depends on its functionality.
            // Let's say, we have a Command that needs to interact with our database, we can now create a constructor in that Command, to accept the SampleVitalPlayerService
            // The Command will then utilize the Service to perform actions on DB level.
            // Using this approach, we can make a cut between direct DB interactions and can include unit tests easily. (Spring approach)

            // Here we register a sample Listener to showcase Database interactions.
            final SampleVitalDatabaseListener sampleVitalDatabaseListener = new SampleVitalDatabaseListener(sampleVitalPlayerService);

            final VitalListenerManager vitalListenerManager = vital.getVitalComponent(VitalListenerManager.class).get();

            vitalListenerManager.registerVitalComponent(sampleVitalDatabaseListener);
        }

        // Here we register our Command.
        final VitalCommandManager vitalCommandManager = vital.getVitalComponent(VitalCommandManager.class).get();
        final SampleVitalCommand sampleVitalCommand = new SampleVitalCommand();

        vitalCommandManager.registerVitalComponent(sampleVitalCommand);

        // Here we register our Listener.
        final VitalListenerManager vitalListenerManager = vital.getVitalComponent(VitalListenerManager.class).get();
        final VitalItemStackManager vitalItemStackManager = vital.getVitalComponent(VitalItemStackManager.class).get();
        final VitalHologramManager vitalHologramManager = vital.getVitalComponent(VitalHologramManager.class).get();
        final SampleVitalListener sampleVitalListener = new SampleVitalListener(vitalItemStackManager, vitalHologramManager, sampleVitalConfig);

        vitalListenerManager.registerVitalComponent(sampleVitalListener);
    }
}
