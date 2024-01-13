package me.xra1ny.vital.databases;

import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code VitalDatabaseManager} class manages the registration and unregistration of {@link VitalDatabase} components.
 * It extends {@link VitalComponentListManager} and provides methods to handle the database components.
 */
@Log
@VitalDI
public final class VitalDatabaseManager extends VitalComponentListManager<VitalDatabase> {
    @Override
    public void onRegistered() {
        log.info("VitalDatabaseManager online!");
    }

    @Override
    public @NotNull Class<VitalDatabase> managedType() {
        return VitalDatabase.class;
    }
}
