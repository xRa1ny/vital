package me.xra1ny.vital.databases;

import lombok.Getter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.jetbrains.annotations.NotNull;

/**
 * The {@code VitalDatabase} class represents a component responsible for managing database connections and
 * interacting with database entities. It extends {@code VitalComponentListManager} and implements the
 * {@code AnnotatedVitalComponent} interface, which is annotated with the {@code VitalDatabaseInfo} annotation.
 */
@Getter(onMethod = @__(@NotNull))
public class VitalDatabase extends VitalComponentListManager<VitalRepository> implements AnnotatedVitalComponent<VitalDatabaseInfo> {
    /**
     * Stores the configuration for the database connection.
     */
    private final Configuration configuration;

    /**
     * Represents the Hibernate SessionFactory for managing database sessions.
     */
    private SessionFactory sessionFactory;

    public VitalDatabase() {
        final VitalDatabaseInfo vitalDatabaseInfo = getRequiredAnnotation();

        this.configuration = new Configuration().configure(vitalDatabaseInfo.value());
    }

    /**
     * Called when the component is registered. It builds the Hibernate SessionFactory using the provided configuration.
     */
    @Override
    public final void onRegistered() {
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public final void onUnregistered() {

    }

    @Override
    public final void onVitalComponentRegistered(@NotNull VitalRepository vitalRepository) {
        // Add the annotated class for the managed entity of the VitalRepository to the configuration.
        configuration.addAnnotatedClass(vitalRepository.managedEntityType());
    }

    @Override
    public final void onVitalComponentUnregistered(@NotNull VitalRepository vitalRepository) {

    }

    @Override
    public Class<VitalRepository> managedType() {
        return VitalRepository.class;
    }

    @Override
    public final Class<VitalDatabaseInfo> requiredAnnotationType() {
        return VitalDatabaseInfo.class;
    }
}
