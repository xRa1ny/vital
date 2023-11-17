package me.xra1ny.vital.configs;

import lombok.SneakyThrows;
import org.reflections.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

/**
 * This interface represents an object that can be serialized into a map for configuration purposes.
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
        // Create a new HashMap to store serialized key-value pairs.
        final Map<String, Object> stringObjectMap = new HashMap<>();

        // Iterate through all fields in the implementing class using ReflectionUtils.
        for (Field field : ReflectionUtils.getAllFields(getClass())) {
            // If our Field is transient, we want to skip it in this iteration.
            if(Modifier.isTransient(field.getModifiers())) {
                continue;
            }

            final VitalConfigPath path = field.getAnnotation(VitalConfigPath.class);

            if (path == null) {
                continue;
            }

            Object fieldValue = field.get(this);

            // If the object we are trying to add to our serialized object is of type Enum
            // we want to convert it to a String, so we can safely read it from config later.
            if(fieldValue instanceof Enum<?>) {
                fieldValue = fieldValue.toString();
            }else if(fieldValue instanceof VitalConfigSerializable vitalConfigSerializable) {
                fieldValue = vitalConfigSerializable.serialize();
            }

            // Add the field's value to the serialized map with the key specified by VitalConfigPath annotation.
            stringObjectMap.put(path.value(), fieldValue);
        }

        // Return the map containing the serialized configuration.
        return stringObjectMap;
    }
}
