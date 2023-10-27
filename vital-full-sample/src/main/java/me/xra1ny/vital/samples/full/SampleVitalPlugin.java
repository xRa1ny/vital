package me.xra1ny.vital.samples.full;

import me.xra1ny.vital.Vital;
import me.xra1ny.vital.commands.VitalCommandManager;
import me.xra1ny.vital.core.VitalListenerManager;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStackManager;
import me.xra1ny.vital.samples.full.command.SampleVitalCommand;
import me.xra1ny.vital.samples.full.listener.SampleVitalListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SampleVitalPlugin extends JavaPlugin {
    /**
     * Using this implementation, Vital is initialized with maximum functionality, containing all of Vital's content.
     * <p>
     * NOTE: When using this approach, you may also unregister certain modules of Vital that you do not need if you like.
     * NOTE: Let's say, you don't need to use the `vital-commands` Module, you can simply unregister it when your plugin enables if you like...
     */
    private final Vital<SampleVitalPlugin> vital = new Vital<>(this);

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    public void onEnable() {
        // Here we enable Vital
        vital.enable();

        // Here we register our Command.
        final VitalCommandManager vitalCommandManager = vital.getVitalComponentManager().getVitalComponent(VitalCommandManager.class).get();
        final SampleVitalCommand sampleVitalCommand = new SampleVitalCommand();

        vitalCommandManager.registerVitalComponent(sampleVitalCommand);

        // Here we register our Listener.
        final VitalListenerManager vitalListenerManager = vital.getVitalComponentManager().getVitalComponent(VitalListenerManager.class).get();
        final VitalItemStackManager vitalItemStackManager = vital.getVitalComponentManager().getVitalComponent(VitalItemStackManager.class).get();
        final VitalHologramManager vitalHologramManager = vital.getVitalComponentManager().getVitalComponent(VitalHologramManager.class).get();
        final SampleVitalListener sampleVitalListener = new SampleVitalListener(vitalItemStackManager, vitalHologramManager);

        vitalListenerManager.registerVitalComponent(sampleVitalListener);

        /*
         * Consider using the `dependency-injection` pattern, when working with Vital, to organize and keep complex code modular.
         */
    }
}
