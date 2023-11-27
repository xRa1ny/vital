package me.xra1ny.vital.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies metadata for the plugin's main class.
 * If combined with `vital-core-processor` dependency as annotation processor, can automatically generate the `plugin.yml` on compile-time.
 *
 * @author xRa1ny
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface VitalPluginInfo {
    /**
     * Defines the name of this plugin.
     *
     * @return The name of this plugin.
     */
    String value();

    /**
     * Defines the description of this plugin.
     *
     * @return The description of this plugin.
     */
    String description() default "A Vital Plugin";

    /**
     * Defines the api version this plugin uses.
     * NOTE: api-version may be identical to server environment version.
     *
     * @return The api version this plugin uses.
     */
    String apiVersion() default "1.20";

    /**
     * Defines the version of this plugin.
     *
     * @return The version of this plugin.
     */
    String version() default "1.0";

    /**
     * The author/s of this plugin.
     *
     * @return The author/s of this plugin.
     */

    String authors() default "";
}
