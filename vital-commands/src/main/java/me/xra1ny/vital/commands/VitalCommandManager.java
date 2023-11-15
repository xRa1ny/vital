package me.xra1ny.vital.commands;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

/**
 * Class responsible for managing Vital commands.
 * Extends VitalComponentListManagement to handle command registration and unregistration.
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

    /**
     * Attempts to automatically register all `VitalCommands` in the specified package.
     *
     * @param packageName The package.
     */
    @SneakyThrows
    public void registerVitalCommands(@NotNull String packageName) {
        for(Class<? extends VitalCommand> vitalCommandClass : new Reflections(packageName).getSubTypesOf(VitalCommand.class)) {
            final VitalCommand vitalCommand = vitalCommandClass.getDeclaredConstructor().newInstance();

            registerVitalComponent(vitalCommand);
        }
    }
}
