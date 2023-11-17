package me.xra1ny.vital.configs;

import lombok.Getter;
import lombok.SneakyThrows;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import org.bukkit.ChatColor;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.reflections.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @SuppressWarnings("ResultOfMethodCallIgnored")
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
    @SuppressWarnings("unchecked")
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
    @SuppressWarnings({"unchecked", "deprecation"})
    @SneakyThrows
    public final void save() {
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            // If our Field is transient, we want to skip this iteration.
            if(Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            Object fieldValue = field.get(this);

            if (fieldValue instanceof String stringFieldValue) {
                //noinspection UnnecessaryUnicodeEscape
                fieldValue = ChatColor.translateAlternateColorCodes('\u00A7', stringFieldValue);
            }

            if (fieldValue instanceof List<?> fieldValueList) {
                try {
                    // Attempt to serialize every element in List.
                    final List<Map<String, Object>> serializedContentMap = ((List<? extends VitalConfigSerializable>) fieldValueList).stream()
                            .map(VitalConfigSerializable::serialize)
                            .toList();

                    config.set(path.value(), serializedContentMap);
                } catch (ClassCastException ignored) {
                    // If elements in List, are not of Type VitalConfigSerializable, attempt to use Spigot's default Config Mapping.
                    this.config.set(path.value(), fieldValue);
                }
            } else if (fieldValue instanceof VitalConfigSerializable vitalConfigSerializable) {
                // Attempt to serialize the Object we are trying to save.
                config.set(path.value(), vitalConfigSerializable.serialize());
            }
        }

        this.config.save(this.configFile);
    }

    /**
     * Updates the configuration, reloading all values from the file into class fields.
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    @SneakyThrows
    public final void update() {
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            // If our Field is transient, we want to skip this iteration.
            if(Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            Object configValue = this.config.get(path.value());

            if (configValue == null) {
                continue;
            }

            if (configValue instanceof String stringConfigValue) {
                configValue = ChatColor.translateAlternateColorCodes('&', stringConfigValue);
                field.set(this, configValue);
            } else if (configValue instanceof Map<?, ?> configValueMap) {
                final Map<String, Object> stringObjectMap = (Map<String, Object>) configValueMap;

                field.set(this, deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap));
            } else if (configValue instanceof MemorySection memorySection) {
                final Map<String, Object> stringObjectMap = new HashMap<>();

                for (String key : memorySection.getKeys(false)) {
                    stringObjectMap.put(key, memorySection.get(key));
                }

                field.set(this, deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap));
            }
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @SneakyThrows
    public final <T extends VitalConfigSerializable> T deserialize(@NotNull Class<T> clazz, @NotNull Map<String, Object> serialized) {
        final Map<Field, Object> fieldObjectMap = new HashMap<>();

        for (Field field : ReflectionUtils.getAllFields(clazz)) {
            // If our Field is transient, we want to skip it.
            if(Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            if (!serialized.containsKey(path.value())) {
                // Could not deserialize, since ConfigPath value was not found on serialized content Map!

                continue;
            }

            for (Map.Entry<String, Object> stringObjectEntry : serialized.entrySet()) {
                if (!stringObjectEntry.getKey().equals(path.value())) {
                    continue;
                }

                if (stringObjectEntry.getValue() instanceof MemorySection memorySection) {
                    final Map<String, Object> stringObjectMap = new HashMap<>();

                    for (String key : memorySection.getKeys(false)) {
                        stringObjectMap.put(key, memorySection.get(key));
                    }

                    fieldObjectMap.put(field, deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap));
                } else {
                    fieldObjectMap.put(field, stringObjectEntry.getValue());
                }
            }

            final VitalConfigEnum vitalConfigEnum = field.getDeclaredAnnotation(VitalConfigEnum.class);

            if (vitalConfigEnum != null) {
                fieldObjectMap.replace(field, Enum.valueOf((Class<Enum>) field.getType(), fieldObjectMap.get(field).toString()));
            }
        }

        Constructor<T> constructor;

        try {
            final Class<?>[] constructorTypes = fieldObjectMap.keySet().stream()
                    .map(Field::getClass)
                    .toArray(Class[]::new);

            // Attempt to get Constructor matching ALL mapped Fields.
            constructor = clazz.getDeclaredConstructor(constructorTypes);

            final Object[] constructorValues = fieldObjectMap.values().toArray(new Object[0]);

            // Constructor found, Continue with Object creation...
            return constructor.newInstance(constructorValues);
        } catch (NoSuchMethodException e) {
            // If no such Constructor was found, attempt getting default POJO Constructor.
            // No Constructor found, Cannot continue, as Object Class is incorrectly configured / mapped.
            constructor = clazz.getDeclaredConstructor();

            // Default Constructor found, Continue with Object creation...
            final T instance = constructor.newInstance();

            for (Map.Entry<Field, Object> fieldObjectEntry : fieldObjectMap.entrySet()) {
                fieldObjectEntry.getKey().set(instance, fieldObjectEntry.getValue());
            }

            return instance;
        }
    }

    @Override
    public final Class<VitalConfigInfo> requiredAnnotationType() {
        return VitalConfigInfo.class;
    }

    @Override
    public final void onRegistered() {
        update();
        save();
    }

    @Override
    public final void onUnregistered() {
        save();
    }
}
