package me.xra1ny.vital.core.exception;

import me.xra1ny.vital.core.VitalComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Custom exception class for handling Vital-Framework exceptions.
 * Represents exceptions that may occur within {@link VitalComponent}.
 *
 * @author xRa1ny
 */
public class VitalException extends RuntimeException {
    /**
     * Constructs a new {@link VitalException}.
     *
     * @param source The {@link VitalComponent} associated with the exception.
     * @param text   Additional text describing the exception (can be null).
     */
    public VitalException(@NotNull VitalComponent source, @Nullable String text) {
        super(text == null ? "Exception in Vital component " + source : text + source);
    }
}
