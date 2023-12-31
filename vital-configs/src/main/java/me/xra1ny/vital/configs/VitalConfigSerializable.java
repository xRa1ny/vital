package me.xra1ny.vital.configs;

import lombok.SneakyThrows;
import me.xra1ny.vital.configs.annotation.VitalConfigPath;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * This interface represents an object that can be serialized into a map for {@link VitalConfig} purposes.
 *
 * @author xRa1ny
 */
public interface VitalConfigSerializable {
    /**
     * Serializes the implementing object into a map of key-value pairs for configuration.
     *
     * @return A map containing the serialized configuration data.
     */
    @SuppressWarnings("unchecked")
    @SneakyThrows
    default Map<String, Object> serialize() {
        // Create a new LinkedHashMap to store serialized key-value pairs.
        final Map<String, Object> stringObjectMap = new LinkedHashMap<>();

        // Iterate through all fields in the implementing class using ReflectionUtils.
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            // If our Field is transient, we want to skip it in this iteration.
            if (Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final Optional<VitalConfigPath> optionalVitalConfigPath = Optional.ofNullable(field.getAnnotation(VitalConfigPath.class));

            if (optionalVitalConfigPath.isEmpty()) {
                continue;
            }

            final VitalConfigPath vitalConfigPath = optionalVitalConfigPath.get();

            // force accessibility for `private` field support.
            field.setAccessible(true);

            Object fieldValue = field.get(this);

            // If the object we are trying to add to our serialized object is of type Enum
            // we want to convert it to a String, so we can safely read it from config later.
            if (fieldValue instanceof Enum<?>) {
                fieldValue = fieldValue.toString();
            } else if (fieldValue instanceof VitalConfigSerializable vitalConfigSerializable) {
                fieldValue = vitalConfigSerializable.serialize();
            } else if (fieldValue instanceof List<?> list) {
                try {
                    // attempt to use mapping for complex types...
                    final List<VitalConfigSerializable> vitalConfigSerializableList = (List<VitalConfigSerializable>) list;
                    final List<Map<String, Object>> stringObjectMapList = new ArrayList<>();

                    for (VitalConfigSerializable vitalConfigSerializable : vitalConfigSerializableList) {
                        stringObjectMapList.add(vitalConfigSerializable.serialize());
                    }

                    fieldValue = stringObjectMapList;
                } catch (ClassCastException ignored) {
                    // use default mapping...
                }
            }

            // Add the field's value to the serialized map with the key specified by VitalConfigPath annotation.
            stringObjectMap.put(vitalConfigPath.value(), fieldValue);
        }

        // Return the map containing the serialized configuration.
        return stringObjectMap;
    }
}
