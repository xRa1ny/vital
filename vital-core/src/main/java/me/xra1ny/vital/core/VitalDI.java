package me.xra1ny.vital.core;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation provided to an implementing subclass of a Vital Component, indicating, that its implementation is viable for dependency injection.
 * <p>
 * All implementing subclass components annotated with this Annotation, are told that they are viable for automatic registration when their responsible Manager is enabled.
 * <p>
 * e.g:
 * <p>
 * When Vital is enabled and the VitalCommandManager is enabled, it scans for all VitalCommands are annotated with `@VitalDI` and attempts to auto-register their dependency injected instance into the VitalCommandManager.
 * <p>
 * This Annotation is implemented to separate dependency injectable classes from non dependency injectable classes...
 *
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalDI {
    // nothing to see here...
}
