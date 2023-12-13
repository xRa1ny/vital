package me.xra1ny.vital.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to specify the path of a configuration field in {@link VitalConfig}.
 *
 * @apiNote Defines the path where the field's value is stored within the configuration file.
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface VitalConfigPath {
    /**
     * Defines the path where the field's value is stored within the configuration file.
     *
     * @return The path within the configuration file.
     */
    String value();
}
