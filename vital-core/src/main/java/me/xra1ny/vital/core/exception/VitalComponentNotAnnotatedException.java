package me.xra1ny.vital.core.exception;

import me.xra1ny.vital.core.VitalComponent;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Annotation;

/**
 * Exception thrown when a {@link VitalComponent} is not annotated as expected.
 * Indicates that a specific annotation is required but is missing.
 *
 * @author xRa1ny
 */
public class VitalComponentNotAnnotatedException extends VitalException {
    /**
     * Constructs a new {@link VitalComponentNotAnnotatedException}.
     *
     * @param source          The {@link VitalComponent} that is not annotated as expected.
     * @param annotationClass The class of the expected annotation.
     */
    public VitalComponentNotAnnotatedException(
            @NotNull VitalComponent source,
            @NotNull Class<? extends Annotation> annotationClass
    ) {
        super(source, "Vital component " + source + " must be annotated with " + annotationClass);
    }
}
