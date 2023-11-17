package me.xra1ny.vital.databases;

import lombok.SneakyThrows;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.jetbrains.annotations.NotNull;
import org.reflections.Reflections;

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

    /**
     * Attempts to automatically register all `VitalDatabases` in the specified package.
     *
     * @param packageName The package.
     */
    @SneakyThrows
    public void registerVitalDatabases(@NotNull String packageName) {
        for(Class<? extends VitalDatabase> vitalDatabaseClass : new Reflections(packageName).getSubTypesOf(VitalDatabase.class)) {
            final VitalDatabase vitalDatabase = vitalDatabaseClass.getDeclaredConstructor().newInstance();

            registerVitalComponent(vitalDatabase);
        }
    }
}
