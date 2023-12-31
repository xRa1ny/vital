package me.xra1ny.vital.core;

import org.jetbrains.annotations.NotNull;

public interface VitalSubModuleIdentification extends VitalComponent {
    @Override
    @NotNull
    String getName();

    @Override
    default void onRegistered() {

    }

    @Override
    default void onUnregistered() {

    }
}
