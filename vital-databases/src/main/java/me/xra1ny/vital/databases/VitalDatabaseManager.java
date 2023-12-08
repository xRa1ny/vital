package me.xra1ny.vital.databases;

import me.xra1ny.vital.core.VitalComponentListManager;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code VitalDatabaseManager} class manages the registration and unregistration of {@link VitalDatabase} components.
 * It extends {@link VitalComponentListManager} and provides methods to handle the database components.
 */
public final class VitalDatabaseManager extends VitalComponentListManager<VitalDatabase> {
    @Override
    public void onRegistered() {
    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalDatabase vitalDatabase) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalDatabase vitalDatabase) {

    }

    @Override
    public Class<VitalDatabase> managedType() {
        return VitalDatabase.class;
    }
}
