package me.xra1ny.vital.configs;

import lombok.Getter;
import lombok.SneakyThrows;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Abstract base class for managing configuration files in the Vital framework.
 * Provides functionality for reading, writing, and updating configuration settings.
 *
 * @author xRa1ny
 */
public abstract class VitalConfig implements AnnotatedVitalComponent<VitalConfigInfo> {
    /**
     * The name of the configuration file.
     */
    @Getter(onMethod = @__(@NotNull))
    private final String name;

    /**
     * The File object representing the configuration file.
     */
    @Getter(onMethod = @__(@NotNull))
    private File configFile;

    /**
     * The FileConfiguration object for working with the configuration settings.
     */
    @Getter(onMethod = @__(@NotNull))
    private FileConfiguration config;

    /**
     * Constructor for VitalConfig with a specified name.
     *
     * @param name The name of the configuration file.
     */
    public VitalConfig(@NotNull String name, @NotNull JavaPlugin javaPlugin) {
        this.name = name;

        initialize(javaPlugin);
    }

    /**
     * Constructor for VitalConfig using the name specified in the annotation.
     */
    public VitalConfig(@NotNull JavaPlugin javaPlugin) {
        final VitalConfigInfo vitalConfigInfo = getRequiredAnnotation();

        this.name = vitalConfigInfo.value();

        initialize(javaPlugin);
    }

    /**
     * Initializes the configuration by creating the file and loading its contents.
     */
    @SneakyThrows
    private void initialize(@NotNull JavaPlugin javaPlugin) {
        this.configFile = new File(javaPlugin.getDataFolder(), name);

        final File parentConfigFile = configFile.getParentFile();

        parentConfigFile.mkdirs();

        this.configFile.createNewFile();
        this.config = YamlConfiguration.loadConfiguration(this.configFile);
    }

    /**
     * Gets a configuration value by its specified key.
     *
     * @param type The type of the returning value.
     * @param key  The specified configuration key.
     * @param <T>  The type of the returning value.
     * @return The value of the configuration key, or null if the key does not exist.
     */
    @Nullable
    public final <T> T get(@NotNull Class<T> type, @NotNull String key) {
        final Field field = ReflectionUtils.getAllFields(getClass()).stream()
                .filter(_field -> _field.getDeclaredAnnotation(VitalConfigPath.class) != null)
                .filter(_field -> _field.getType().equals(type))
                .filter(_field -> _field.getDeclaredAnnotation(VitalConfigPath.class).value().equalsIgnoreCase(key))
                .findFirst().orElse(null);

        if (field == null) {
            return null;
        }

        final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

        return (T) this.config.get(path.value());
    }

    /**
     * Saves the configuration settings to the file.
     */
    @SneakyThrows
    public final void save() {
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            field.setAccessible(true);

            Object fieldValue = field.get(this);

            if(fieldValue instanceof String stringFieldValue) {
                fieldValue = ChatColor.translateAlternateColorCodes('\u00A7', stringFieldValue);
            }

            this.config.set(path.value(), fieldValue);
        }

        this.config.save(this.configFile);
    }

    /**
     * Updates the configuration, reloading all values from the file into class fields.
     */
    @SneakyThrows
    public final void update() {
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            Object configValue = this.config.get(path.value());

            if(configValue == null) {
                continue;
            }

            if(configValue instanceof String stringConfigValue) {
                configValue = ChatColor.translateAlternateColorCodes('&', stringConfigValue);
            }

            field.setAccessible(true);
            field.set(this, configValue);
        }
    }

    @Override
    public final Class<VitalConfigInfo> requiredAnnotationType() {
        return VitalConfigInfo.class;
    }

    @Override
    public final void onVitalComponentRegistered() {
        update();
        save();
    }

    @Override
    public final void onVitalComponentUnregistered() {
        save();
    }
}
