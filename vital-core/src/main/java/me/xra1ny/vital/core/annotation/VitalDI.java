package me.xra1ny.vital.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation provided to an implementing subclass of a Vital Component, indicating, that its implementation is viable for dependency injection.
 *
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalDI {
    // nothing to see here...
}
