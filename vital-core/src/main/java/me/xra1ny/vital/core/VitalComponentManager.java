package me.xra1ny.vital.core;

import lombok.NonNull;

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
    public void onVitalComponentRegistered(@NonNull VitalComponent vitalComponent) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalComponent vitalComponent) {

    }

    @Override
    public Class<VitalComponent> managedType() {
        return VitalComponent.class;
    }
}
