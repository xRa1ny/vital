package me.xra1ny.vital.samples.full.persistence.service;

import me.xra1ny.vital.samples.full.persistence.entity.SampleVitalPlayerEntity;
import me.xra1ny.vital.samples.full.persistence.repository.SampleVitalPlayerRepository;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * A Service will perform db actions with the specified repositories on its dependency hierarchy.
 * We will always call the Service to perform db actions AND NOT THE REPOSITORY DIRECTLY.
 * <p>
 * Using this approach, we make sure, that our code is always modular and can be tested later on. (mocking, etc.)
 */
public class SampleVitalPlayerService {
    private final SampleVitalPlayerRepository sampleVitalPlayerRepository;

    public SampleVitalPlayerService(@NotNull SampleVitalPlayerRepository sampleVitalPlayerRepository) {
        this.sampleVitalPlayerRepository = sampleVitalPlayerRepository;
    }

    public boolean vitalPlayerExistsById(@NotNull UUID uuid) {
        return sampleVitalPlayerRepository.existsById(SampleVitalPlayerEntity.class, uuid);
    }

    public void createVitalPlayerEntity(@NotNull UUID uuid, @NotNull String name, long coins, long level) {
        final SampleVitalPlayerEntity sampleVitalPlayerEntity = SampleVitalPlayerEntity.builder()
                .uniqueId(uuid)
                .name(name)
                .coins(coins)
                .level(level)
                .build();

        sampleVitalPlayerRepository.persist(sampleVitalPlayerEntity);
    }

    public void removeVitalPlayerEntityById(@NotNull UUID uuid) {
        final Optional<SampleVitalPlayerEntity> optionalSampleVitalPlayerEntity = sampleVitalPlayerRepository.findById(SampleVitalPlayerEntity.class, uuid);

        if (optionalSampleVitalPlayerEntity.isPresent()) {
            final SampleVitalPlayerEntity sampleVitalPlayerEntity = optionalSampleVitalPlayerEntity.get();

            sampleVitalPlayerRepository.remove(sampleVitalPlayerEntity);
        }
    }

    /**
     * Just a sample method to show how to communicate with a DB.
     */
    public void sampleRepositoryActionMethod() {
        // Attempt to fetch SampleVitalPlayerEntity from DB.
        final Optional<SampleVitalPlayerEntity> optionalSampleVitalPlayerEntity = sampleVitalPlayerRepository.findById(SampleVitalPlayerEntity.class, UUID.randomUUID()); // With random UUID

        if (optionalSampleVitalPlayerEntity.isEmpty()) {
            // Create a new SampleVitalPlayerEntity on DB level.
            final SampleVitalPlayerEntity sampleVitalPlayerEntity = SampleVitalPlayerEntity.builder()
                    .uniqueId(UUID.randomUUID())
                    .name("playerName")
                    .coins(0)
                    .level(0)
                    .build();

            sampleVitalPlayerRepository.persist(sampleVitalPlayerEntity);

            return;
        }

        // Modify a SampleVitalPlayerEntity on DB level.
        final SampleVitalPlayerEntity sampleVitalPlayerEntity = optionalSampleVitalPlayerEntity.get();

        sampleVitalPlayerEntity.setCoins(sampleVitalPlayerEntity.getCoins()-100);

        sampleVitalPlayerRepository.persist(sampleVitalPlayerEntity);

        // Get a List of all SampleVitalPlayerEntities.
        final List<SampleVitalPlayerEntity> sampleVitalPlayerEntityList = sampleVitalPlayerRepository.findAll(SampleVitalPlayerEntity.class);

        // Get a List of all SampleVitalPlayerEntities with the specified DB column values.
        final List<SampleVitalPlayerEntity> sampleVitalPlayerEntityList1 = sampleVitalPlayerRepository.findAll(SampleVitalPlayerEntity.class,
                Map.entry("uniqueId", UUID.randomUUID()), // reads from class scoped variables NOT FROM DB COLUMNS!!!
                Map.entry("name", "playerName")
        );
    }
}
