package me.xra1ny.vital.samples.full.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.xra1ny.vital.databases.VitalEntity;

import java.util.UUID;

/**
 * This class defines Database entity `row` which holds all data describes in this class.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "player")
public class SampleVitalPlayerEntity implements VitalEntity {
    @Id
    @Column(name = "UUID")
    private UUID uniqueId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "COINS")
    private long coins;

    @Column(name = "LEVEL")
    private long level;

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
