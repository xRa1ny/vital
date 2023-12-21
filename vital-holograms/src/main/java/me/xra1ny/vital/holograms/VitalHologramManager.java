package me.xra1ny.vital.holograms;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Manages the creation, configuration, and persistence of {@link VitalHologram} instances.
 *
 * @author xRa1ny
 */
@SuppressWarnings("unused")
@Log
@VitalDI
public final class VitalHologramManager extends VitalComponentListManager<VitalHologram> {
    private final JavaPlugin javaPlugin;
    private final VitalHologramConfig vitalHologramConfig;

    /**
     * Constructs a new VitalHologramManagement instance.
     *
     * @param javaPlugin          The JavaPlugin instance associated with this manager.
     * @param vitalHologramConfig The {@link VitalHologramConfig} that persisted all {@link VitalHologram} instances.
     */
    public VitalHologramManager(@NonNull JavaPlugin javaPlugin, @NonNull VitalHologramConfig vitalHologramConfig) {
        this.javaPlugin = javaPlugin;
        this.vitalHologramConfig = vitalHologramConfig;
    }

    /**
     * Saves the specified VitalHologram to the configuration file.
     *
     * @param vitalHologram The hologram to save.
     */
    @SneakyThrows
    public void saveVitalHologramToConfig(@NonNull VitalHologram vitalHologram) {
        List<VitalHologram> vitalHologramList = vitalHologramConfig.getVitalHologramList();

        if (vitalHologramList == null) {
            vitalHologramList = new ArrayList<>();
        }

        vitalHologramList.add(vitalHologram);
        vitalHologramConfig.setVitalHologramList(vitalHologramList);
    }

    /**
     * Removes the specified VitalHologram from the configuration file.
     *
     * @param vitalHologram The hologram to remove.
     */
    @SneakyThrows
    public void removeVitalHologramFromConfig(@NonNull VitalHologram vitalHologram) {
        final Optional<List<VitalHologram>> optionalVitalHologramList = Optional.ofNullable(vitalHologramConfig.getVitalHologramList());

        if (optionalVitalHologramList.isEmpty()) {
            return;
        }

        final List<VitalHologram> vitalHologramList = optionalVitalHologramList.get();

        vitalHologramList.remove(vitalHologram);
        vitalHologramConfig.setVitalHologramList(vitalHologramList);
    }

    /**
     * Called when this VitalHologramManagement instance is registered.
     * Loads hologram data from the configuration file and creates VitalHologram instances.
     */
    @Override
    public void onRegistered() {
        if (vitalHologramConfig.getVitalHologramList() != null) {
            for (VitalHologram vitalHologram : vitalHologramConfig.getVitalHologramList()) {
                registerVitalComponent(vitalHologram);
            }
        }

        log.info("VitalHologramManager online!");
    }

    /**
     * Called when this VitalHologramManagement instance is unregistered.
     */
    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NonNull VitalHologram vitalHologram) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalHologram vitalHologram) {

    }

    @Override
    public Class<VitalHologram> managedType() {
        return VitalHologram.class;
    }
}
