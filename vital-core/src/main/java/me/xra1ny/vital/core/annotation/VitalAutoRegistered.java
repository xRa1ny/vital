package me.xra1ny.vital.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines that the annotated {@link VitalComponent}, is automatically registered and attempted to be dependency injected on {@link VitalComponentManager}.
 *
 * @author xRa1ny
 * @apiNote Autoregistered components also need to be annotated with {@link VitalDI} so Vital is able to inject all needed dependencies for creation.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalAutoRegistered {
}
