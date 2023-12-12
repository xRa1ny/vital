package me.xra1ny.vital.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines that the annotated `VitalComponent`, is automatically registered and attempted to be dependency injected with all Constructor arguments,
 * on the base Component Manager of Vital `VitalComponentManager`.
 *
 * @apiNote Autoregistered Components also need to be annotated with `@VitalDI` so Vital is able to Inject all needed Dependencies for Creation.
 * @author xRa1ny
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalAutoRegistered {
}
