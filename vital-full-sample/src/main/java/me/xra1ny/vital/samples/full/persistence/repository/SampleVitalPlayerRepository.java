package me.xra1ny.vital.samples.full.persistence.repository;

import me.xra1ny.vital.databases.VitalDatabase;
import me.xra1ny.vital.databases.VitalRepository;
import me.xra1ny.vital.samples.full.persistence.entity.SampleVitalPlayerEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A repository defines which Entity from DB is managed, and which ID is used for the Entity in DB (PK Primary Key).
 */
public class SampleVitalPlayerRepository extends VitalRepository<SampleVitalPlayerEntity, UUID> {
    public SampleVitalPlayerRepository(@NotNull VitalDatabase vitalDatabase) {
        super(vitalDatabase);
    }

    @Override
    public Class<SampleVitalPlayerEntity> managedEntityType() {
        return SampleVitalPlayerEntity.class;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
