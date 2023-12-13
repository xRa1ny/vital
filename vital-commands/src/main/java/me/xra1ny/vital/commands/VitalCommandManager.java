package me.xra1ny.vital.commands;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Class responsible for managing {@link VitalCommand}.
 *
 * @author xRa1ny
 */
@Log
public final class VitalCommandManager extends VitalComponentListManager<VitalCommand> {
    private final JavaPlugin javaPlugin;

    public VitalCommandManager(@NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalCommandManagement!");
    }

    @Override
    public void onUnregistered() {

    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onVitalComponentRegistered(@NotNull VitalCommand vitalCommand) {
        javaPlugin.getCommand(vitalCommand.getName()).setExecutor(vitalCommand);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void onVitalComponentUnregistered(@NotNull VitalCommand vitalComponent) {
        javaPlugin.getCommand(vitalComponent.getName()).setExecutor(null);
    }

    @Override
    public Class<VitalCommand> managedType() {
        return VitalCommand.class;
    }
}
