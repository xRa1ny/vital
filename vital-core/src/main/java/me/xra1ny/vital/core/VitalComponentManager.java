package me.xra1ny.vital.core;

import org.jetbrains.annotations.NotNull;

/**
 * Class for managing a list of {@link VitalComponent}.
 *
 * @author xRa1ny
 */
public final class VitalComponentManager extends VitalComponentListManager<VitalComponent> {
    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalComponent vitalComponent) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalComponent vitalComponent) {

    }

    @Override
    public Class<VitalComponent> managedType() {
        return VitalComponent.class;
    }
}
