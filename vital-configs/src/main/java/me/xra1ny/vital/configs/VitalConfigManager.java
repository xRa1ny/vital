package me.xra1ny.vital.configs;

import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.DIUtils;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

import java.util.Optional;
import java.util.Set;

/**
 * Class responsible for managing Vital configuration files.
 * Extends VitalComponentListManagement to handle configuration registration and unregistration.
 *
 * @author xRa1ny
 */
@Log
public final class VitalConfigManager extends VitalComponentListManager<VitalConfig> {

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalConfigManagement!");
    }


    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalConfig vitalConfig) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalConfig vitalConfig) {

    }

    @Override
    public Class<VitalConfig> managedType() {
        return VitalConfig.class;
    }

    /**
     * Attempts to automatically register all `VitalConfigs` in the specified package.
     *
     * @param packageName The package.
     */
    @SneakyThrows
    public void registerVitalConfigs(@NotNull String packageName, @NotNull JavaPlugin javaPlugin) {
        for (Class<? extends VitalConfig> vitalConfigClass : new Reflections(packageName).getSubTypesOf(VitalConfig.class)) {
            final VitalConfig vitalConfig = vitalConfigClass.getDeclaredConstructor(JavaPlugin.class).newInstance(javaPlugin);

            registerVitalComponent(vitalConfig);
        }
    }

    @Override
    protected void onEnable() {
        final Set<Class<? extends VitalConfig>> vitalConfigClassSet = new Reflections().getSubTypesOf(VitalConfig.class);

        for (Class<? extends VitalConfig> vitalConfigClass : vitalConfigClassSet) {
            final Optional<? extends VitalConfig> optionalVitalConfig = DIUtils.getDependencyInjectedInstance(vitalConfigClass);

            optionalVitalConfig.ifPresent(this::registerVitalComponent);
        }
    }
}
