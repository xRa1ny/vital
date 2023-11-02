package me.xra1ny.vital.databases;

import jakarta.persistence.Entity;
import me.xra1ny.vital.core.AnnotatedVitalComponent;

/**
 * The {@code VitalEntity} interface represents entities that can be managed by the Vital Framework.
 * It extends the {@link AnnotatedVitalComponent} interface and enforces the requirement of an {@link Entity} annotation.
 */
public interface VitalEntity extends AnnotatedVitalComponent<Entity> {
    @Override
    default Class<Entity> requiredAnnotationType() {
        return Entity.class;
    }
}
