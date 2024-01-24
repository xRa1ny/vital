package me.xra1ny.vital.core;

import me.xra1ny.vital.core.annotation.VitalDI;

/**
 * Services are used to execute "complex" application logic dispatched from any view or "frontend" part of the application.
 *
 * @apiNote This class is an implementation mainly used for convenience so the {@link VitalComponent#onRegistered()} and {@link VitalComponent#onUnregistered()} methods are not REQUIRED to be implemented.
 * @author xRa1ny
 */
@VitalDI
public class VitalService implements VitalComponent {
    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}
