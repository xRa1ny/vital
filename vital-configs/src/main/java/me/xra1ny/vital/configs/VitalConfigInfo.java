package me.xra1ny.vital.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to provide metadata for Vital configuration classes.
 * Defines the name of the associated configuration file.
 *
 * @author xRa1ny
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface VitalConfigInfo {
    /**
     * Defines the name of the associated configuration file.
     *
     * @return The name of the configuration file.
     */
    String value();
}
