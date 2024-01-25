package me.xra1ny.vital.core;

import lombok.NonNull;

public class VitalExceptionsHandlerManager extends VitalComponentListManager<VitalExceptionsHandler> {
    @Override
    public @NonNull Class<VitalExceptionsHandler> managedType() {
        return VitalExceptionsHandler.class;
    }
}
