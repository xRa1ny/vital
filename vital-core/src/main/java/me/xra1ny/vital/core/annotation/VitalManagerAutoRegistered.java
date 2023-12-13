package me.xra1ny.vital.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies that the annotated class is viable for automatic registration when its respective manager instance is enabled.
 *
 * @apiNote Autoregistered Components also need to be annotated with {@link VitalDI} so Vital is able to inject all needed dependencies for creation.
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalManagerAutoRegistered {
}
