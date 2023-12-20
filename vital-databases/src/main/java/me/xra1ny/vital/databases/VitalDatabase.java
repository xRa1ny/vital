package me.xra1ny.vital.databases;

import lombok.Getter;
import lombok.NonNull;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.databases.annotation.VitalDatabaseInfo;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * The {@link VitalDatabase} class represents a component responsible for managing database connections and
 * interacting with database entities. It extends {@link VitalComponentListManager} and implements the
 * {@link AnnotatedVitalComponent} interface, which is annotated with the {@link VitalDatabaseInfo} annotation.
 *
 * @author xRa1ny
 */
@VitalDI
public class VitalDatabase extends VitalComponentListManager<VitalRepository> implements AnnotatedVitalComponent<VitalDatabaseInfo> {
    /**
     * Stores the configuration for the database connection.
     */
    @Getter
    @NonNull
    private final Configuration configuration;

    /**
     * Represents the Hibernate SessionFactory for managing database sessions.
     */
    @Getter
    @NonNull
    private SessionFactory sessionFactory;

    /**
     * Constructs a new database for Vital-Framework integrations.
     */
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
    public final void onVitalComponentRegistered(@NonNull VitalRepository vitalRepository) {
        // Add the annotated class for the managed entity of the VitalRepository to the configuration.
        configuration.addAnnotatedClass(vitalRepository.managedEntityType());
    }

    @Override
    public final void onVitalComponentUnregistered(@NonNull VitalRepository vitalRepository) {

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
