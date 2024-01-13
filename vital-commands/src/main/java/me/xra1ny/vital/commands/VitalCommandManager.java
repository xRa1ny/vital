package me.xra1ny.vital.commands;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for managing {@link VitalCommand}.
 *
 * @author xRa1ny
 */
@Log
@VitalDI
public final class VitalCommandManager extends VitalComponentListManager<VitalCommand> {
    private final JavaPlugin javaPlugin;

    /**
     * Construcs a new command manager instance that manages all {@link VitalCommand} instances.
     *
     * @param javaPlugin The {@link JavaPlugin} this manager belongs to.
     */
    public VitalCommandManager(@NonNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void onRegistered() {
        log.info("VitalCommandManager online!");
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onVitalComponentRegistered(@NonNull VitalCommand vitalCommand) {
        javaPlugin.getCommand(vitalCommand.getName()).setExecutor(vitalCommand);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onVitalComponentUnregistered(@NonNull VitalCommand vitalComponent) {
        javaPlugin.getCommand(vitalComponent.getName()).setExecutor(null);
    }

    @Override
    public @NotNull Class<VitalCommand> managedType() {
        return VitalCommand.class;
    }
}
