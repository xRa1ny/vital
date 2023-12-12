package me.xra1ny.vital.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated Class is viable for automatic registration when its respective manager instance is enabled.
 *
 * @apiNote Autoregistered Components also need to be annotated with `@VitalDI` so Vital is able to Inject all needed Dependencies for Creation.
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalManagerAutoRegistered {
}
