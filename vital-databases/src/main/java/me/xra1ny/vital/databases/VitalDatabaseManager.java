package me.xra1ny.vital.databases;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;

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
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NonNull VitalDatabase vitalDatabase) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalDatabase vitalDatabase) {

    }

    @Override
    public Class<VitalDatabase> managedType() {
        return VitalDatabase.class;
    }
}
