package me.xra1ny.vital.configs;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import me.xra1ny.vital.configs.annotation.VitalConfigEnum;
import me.xra1ny.vital.configs.annotation.VitalConfigInfo;
import me.xra1ny.vital.configs.annotation.VitalConfigList;
import me.xra1ny.vital.configs.annotation.VitalConfigPath;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.core.VitalAutoRegisterable;
import me.xra1ny.vital.core.VitalCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.reflections.ReflectionUtils;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * Abstract base class for managing configuration files in the Vital-Framework.
 * Provides functionality for reading, writing, and updating configuration settings.
 *
 * @author xRa1ny
 */
public abstract class VitalConfig implements AnnotatedVitalComponent<VitalConfigInfo>, VitalAutoRegisterable {
    /**
     * The name of the configuration file.
     */
    @Getter
    @NonNull
    private final String name;

    /**
     * The {@link File} object representing the configuration file.
     */
    @Getter
    @NonNull
    private File configFile;

    /**
     * The {@link FileConfiguration} object used in Spigot/BukkitAPI.
     */
    @Getter
    @NonNull
    private FileConfiguration config;

    /**
     * Constructor for {@link VitalConfig} with a specified name.
     *
     * @param name       The name of the configuration file.
     * @param javaPlugin The {@link JavaPlugin} instance of you plugin.
     */
    public VitalConfig(@NonNull String name, @NonNull JavaPlugin javaPlugin) {
        this.name = name;

        initialize(javaPlugin);
    }

    /**
     * Constructor for {@link VitalConfig} using the name specified in the annotation.
     *
     * @param javaPlugin The {@link JavaPlugin} instance of your plugin.
     */
    public VitalConfig(@NonNull JavaPlugin javaPlugin) {
        final VitalConfigInfo vitalConfigInfo = getRequiredAnnotation();

        this.name = vitalConfigInfo.value();

        initialize(javaPlugin);
    }

    /**
     * Initializes the configuration by creating the file and loading its contents.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    private void initialize(@NonNull JavaPlugin javaPlugin) {
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
     * @return An Optional holding either the value of the configuration key, or null if the key does not exist.
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public final <T> Optional<T> get(@NonNull Class<T> type, @NonNull String key) {
        final Optional<Field> optionalField = ReflectionUtils.getAllFields(getClass()).stream()
                .filter(field -> field.getDeclaredAnnotation(VitalConfigPath.class) != null)
                .filter(field -> field.getType().equals(type))
                .filter(field -> field.getDeclaredAnnotation(VitalConfigPath.class).value().equalsIgnoreCase(key))
                .findFirst();

        if (optionalField.isEmpty()) {
            return Optional.empty();
        }

        final Field field = optionalField.get();
        final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

        return Optional.ofNullable((T) this.config.get(path.value()));
    }

    /**
     * Saves the configuration settings to the file.
     */
    @SuppressWarnings({"unchecked", "deprecation"})
    @SneakyThrows
    public final void save() {
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            // If our Field is transient, we want to skip this iteration.
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final Optional<VitalConfigPath> optionalVitalConfigPath = Optional.ofNullable(field.getAnnotation(VitalConfigPath.class));

            if (optionalVitalConfigPath.isEmpty()) {
                continue;
            }

            final VitalConfigPath vitalConfigPath = optionalVitalConfigPath.get();
            Object fieldValue = field.get(this);

            if (fieldValue instanceof String stringFieldValue) {
                //noinspection UnnecessaryUnicodeEscape
                fieldValue = ChatColor.translateAlternateColorCodes('\u00A7', stringFieldValue);
            } else if (fieldValue instanceof List<?> fieldValueList) {
                try {
                    // Attempt to serialize every element in List.
                    final List<Map<String, Object>> serializedContentMap = ((List<? extends VitalConfigSerializable>) fieldValueList).stream()
                            .map(VitalConfigSerializable::serialize)
                            .toList();

                    fieldValue = serializedContentMap;
                } catch (ClassCastException ignored) {
                    // If elements in List, are not of Type VitalConfigSerializable, attempt to use Spigot's default Config Mapping.
                }
            } else if (fieldValue instanceof VitalConfigSerializable vitalConfigSerializable) {
                // Attempt to serialize the Object we are trying to save.
                fieldValue = vitalConfigSerializable.serialize();
            }

            this.config.set(vitalConfigPath.value(), fieldValue);
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
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final Optional<VitalConfigPath> optionalVitalConfigPath = Optional.ofNullable(field.getAnnotation(VitalConfigPath.class));

            if (optionalVitalConfigPath.isEmpty()) {
                continue;
            }

            final VitalConfigPath vitalConfigPath = optionalVitalConfigPath.get();
            final Optional<Object> optionalConfigValue = Optional.ofNullable(this.config.get(vitalConfigPath.value()));

            if (optionalConfigValue.isEmpty()) {
                continue;
            }

            Object configValue = optionalConfigValue.get();

            if (configValue instanceof String stringConfigValue) {
                configValue = ChatColor.translateAlternateColorCodes('&', stringConfigValue);
            } else if (configValue instanceof Map<?, ?> configValueMap) {
                final Map<String, Object> stringObjectMap = (Map<String, Object>) configValueMap;

                configValue = deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap);
            } else if (configValue instanceof MemorySection memorySection) {
                final Map<String, Object> stringObjectMap = new LinkedHashMap<>();

                for (String key : memorySection.getKeys(false)) {
                    stringObjectMap.put(key, memorySection.get(key));
                }

                configValue = deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap);
            } else if (configValue instanceof List<?> list) {
                final Optional<VitalConfigList> optionalVitalConfigList = Optional.ofNullable(field.getDeclaredAnnotation(VitalConfigList.class));

                // If we specified the list annotation with a complex type, attempt to decipher...
                if (optionalVitalConfigList.isPresent()) {
                    final VitalConfigList vitalConfigList = optionalVitalConfigList.get();
                    final Class<? extends VitalConfigSerializable> vitalConfigListType = vitalConfigList.value();
                    final List<LinkedHashMap<String, Object>> linkedHashMapList = (List<LinkedHashMap<String, Object>>) list;
                    final List<VitalConfigSerializable> vitalConfigSerializableList = new ArrayList<>();

                    for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
                        final VitalConfigSerializable vitalConfigSerializable = deserialize(vitalConfigListType, linkedHashMap);

                        vitalConfigSerializableList.add(vitalConfigSerializable);
                    }

                    configValue = vitalConfigSerializableList;
                }
            }

            field.set(this, configValue);
        }
    }

    /**
     * Attempts to deserialize the specified class from the specified serialized object map.
     *
     * @param clazz      The class to deserialize into.
     * @param serialized The serialized content to deserialize from.
     * @param <T>        The type of the object for deserialization.
     * @return The object that is deserialized into.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @SneakyThrows
    public final <T extends VitalConfigSerializable> T deserialize(@NonNull Class<T> clazz, @NonNull Map<String, Object> serialized) {
        final Map<Field, Object> fieldObjectMap = new LinkedHashMap<>();

        for (Field field : ReflectionUtils.getAllFields(clazz)) {
            // If our Field is transient, we want to skip it.
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final Optional<VitalConfigPath> optionalVitalConfigPath = Optional.ofNullable(field.getAnnotation(VitalConfigPath.class));

            if (optionalVitalConfigPath.isEmpty()) {
                continue;
            }

            final VitalConfigPath vitalConfigPath = optionalVitalConfigPath.get();
            final String vitalConfigPathValue = vitalConfigPath.value();

            if (!serialized.containsKey(vitalConfigPathValue)) {
                // Could not deserialize, since ConfigPath value was not found on serialized content Map!

                continue;
            }

            for (Map.Entry<String, Object> stringObjectEntry : serialized.entrySet()) {
                if (!stringObjectEntry.getKey().equals(vitalConfigPathValue)) {
                    continue;
                }

                Object value = stringObjectEntry.getValue();

                if (value instanceof MemorySection memorySection) {
                    final Map<String, Object> stringObjectMap = new LinkedHashMap<>();

                    for (String key : memorySection.getKeys(false)) {
                        stringObjectMap.put(key, memorySection.get(key));
                    }

                    value = deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap);
                } else if (value instanceof List<?> list) {
                    final Optional<VitalConfigList> optionalVitalConfigList = Optional.ofNullable(field.getDeclaredAnnotation(VitalConfigList.class));

                    // decipher, if we have specified a complex type. via annotation..
                    if (optionalVitalConfigList.isPresent()) {
                        final VitalConfigList vitalConfigList = optionalVitalConfigList.get();
                        final Class<? extends VitalConfigSerializable> vitalConfigListType = vitalConfigList.value();
                        final List<LinkedHashMap<String, Object>> linkedHashMapList = (List<LinkedHashMap<String, Object>>) list;
                        final List<VitalConfigSerializable> vitalConfigSerializableList = new ArrayList<>();

                        for (LinkedHashMap<String, Object> linkedHashMap : linkedHashMapList) {
                            final VitalConfigSerializable vitalConfigSerializable = deserialize(vitalConfigListType, linkedHashMap);

                            vitalConfigSerializableList.add(vitalConfigSerializable);
                        }

                        value = vitalConfigSerializableList;
                    }
                } else if (value instanceof Map<?, ?> map) {
                    final Map<String, Object> stringObjectMap = (Map<String, Object>) map;

                    value = deserialize((Class<VitalConfigSerializable>) field.getType(), stringObjectMap);
                }

                fieldObjectMap.put(field, value);
            }

            final Optional<VitalConfigEnum> optionalVitalConfigEnum = Optional.ofNullable(field.getDeclaredAnnotation(VitalConfigEnum.class));

            if (optionalVitalConfigEnum.isPresent()) {
                fieldObjectMap.replace(field, Enum.valueOf((Class<Enum>) field.getType(), fieldObjectMap.get(field).toString()));
            }
        }

        Constructor<T> constructor;

        try {
            final Class<?>[] constructorTypes = fieldObjectMap.keySet().stream()
                    .map(Field::getType)
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

    @Override
    public void autoRegister(@NonNull Class<? extends JavaPlugin> javaPluginType) {
        final VitalCore<? extends JavaPlugin> vitalCore = VitalCore.getVitalCoreInstance(javaPluginType);

        final Optional<VitalConfigManager> optionalVitalConfigManager = vitalCore.getVitalComponentManager().getVitalComponent(VitalConfigManager.class);
        final VitalConfigManager vitalConfigManager = optionalVitalConfigManager.get();

        vitalConfigManager.registerVitalComponent(this);
    }
}
