package me.xra1ny.vital.core.exception;

import me.xra1ny.vital.core.VitalComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Custom exception class for handling Vital framework exceptions.
 * Represents exceptions that may occur within Vital components.
 *
 * @author xRa1ny
 */
public class VitalException extends RuntimeException {
    /**
     * Constructs a new VitalException.
     *
     * @param source The Vital component associated with the exception.
     * @param text   Additional text describing the exception (can be null).
     */
    public VitalException(@NotNull VitalComponent source, @Nullable String text) {
        super(text == null ? "Exception in Vital component " + source : text + source);
    }
}
