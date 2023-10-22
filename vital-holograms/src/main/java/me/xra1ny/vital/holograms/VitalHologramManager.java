package me.xra1ny.vital.holograms;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

/**
 * Manages the creation, configuration, and persistence of VitalHologram instances.
 * Extends VitalComponentListManagement<VitalHologram> and implements VitalComponent.
 *
 * @author xRa1ny
 */
@Getter(onMethod = @__(@NotNull))
@Log
public final class VitalHologramManager extends VitalComponentListManager<VitalHologram> {

    private final JavaPlugin javaPlugin;

    /**
     * The config file of this hologram manager.
     */
    private final File configFile;

    /**
     * The config of this hologram manager.
     */
    private final FileConfiguration config;

    /**
     * Constructs a new VitalHologramManagement instance.
     *
     * @param javaPlugin The JavaPlugin instance associated with this manager.
     */
    public VitalHologramManager(@NotNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.configFile = new File(javaPlugin.getDataFolder(), "holograms.yml");
        javaPlugin.saveResource(this.configFile.getName(), false);
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    /**
     * Saves the specified VitalHologram to the configuration file.
     *
     * @param vitalHologram The hologram to save.
     */
    @SneakyThrows
    public void saveVitalHologramToConfig(@NotNull VitalHologram vitalHologram) {
        final ConfigurationSection section = this.config.createSection(vitalHologram.getName());

        section.set("lines", vitalHologram.getLines());
        section.set("material", String.valueOf(vitalHologram.getDisplayType()));
        section.set("location", vitalHologram.getLocation());

        this.config.save(this.configFile);
    }

    /**
     * Removes the specified VitalHologram from the configuration file.
     *
     * @param vitalHologram The hologram to remove.
     */
    @SneakyThrows
    public void removeVitalHologramFromConfig(@NotNull VitalHologram vitalHologram) {
        this.config.set(vitalHologram.getName(), null);
        this.config.save(this.configFile);
    }

    /**
     * Called when this VitalHologramManagement instance is registered.
     * Loads hologram data from the configuration file and creates VitalHologram instances.
     */
    @SuppressWarnings("unchecked")
    @Override
    public void onVitalComponentRegistered() {
        for (String key : this.config.getKeys(false)) {
            final ConfigurationSection section = this.config.getConfigurationSection(key);

            if (section == null) {
                continue;
            }

            final List<String> lines = (List<String>) section.getList("lines");
            final String materialName = section.getString("material");
            final Material material = materialName == null ? null : Material.valueOf(materialName);
            final Location location = section.getLocation("location");

            // Skip hologram creation if values are null
            if (lines == null || location == null) {
                continue;
            }

            // Attempt to create the loaded hologram from config information...
            final VitalHologram vitalHologram = new VitalHologram(key, location, material, lines.toArray(new String[0]));

            registerVitalComponent(vitalHologram);
        }

        log.info("Successfully registered VitalHologramManagement!");
    }

    /**
     * Called when this VitalHologramManagement instance is unregistered.
     */
    @Override
    public void onVitalComponentUnregistered() {
        // Perform any cleanup or actions when unregistered, if needed.
    }
}
