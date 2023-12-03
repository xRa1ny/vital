package me.xra1ny.vital.core;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Abstract class for managing a list of Vital components.
 * Provides methods to register, unregister, and retrieve components from the list.
 *
 * @param <T> The type of Vital components managed by this class.
 * @author xRa1ny
 */
public abstract class VitalComponentListManager<T extends VitalComponent> implements VitalComponent {
    @Getter(onMethod = @__(@NotNull))
    private final List<T> vitalComponentList = new ArrayList<>();

    public final <T> List<T> getVitalComponentList(@NotNull Class<T> clazz) {
        return vitalComponentList.stream()
                .filter(vitalComponent -> clazz.isAssignableFrom(vitalComponent.getClass()))
                .map(clazz::cast)
                .toList();
    }

    /**
     * Checks if a VitalComponent is registered with the specified UUID.
     *
     * @param vitalComponentUniqueId The UUID of the VitalComponent.
     * @return true if a VitalComponent is registered with the specified UUID, false otherwise.
     */
    public final boolean isVitalComponentRegistered(@NotNull UUID vitalComponentUniqueId) {
        return getVitalComponent(vitalComponentUniqueId).isPresent();
    }

    /**
     * Checks if a VitalComponent is registered with the specified name.
     *
     * @param vitalComponentName The name of the VitalComponent.
     * @return true if a VitalComponent is registered with the specified name, false otherwise.
     */
    public final boolean isVitalComponentRegistered(@NotNull String vitalComponentName) {
        return getVitalComponent(vitalComponentName).isPresent();
    }

    /**
     * Checks if a VitalComponent is registered with the specified class.
     *
     * @param vitalComponentClass The class of the VitalComponent.
     * @return true if a VitalComponent is registered with the specified class, false otherwise.
     */
    public final boolean isVitalComponentRegistered(@NotNull Class<? extends VitalComponent> vitalComponentClass) {
        return getVitalComponent(vitalComponentClass).isPresent();
    }

    /**
     * Gets the VitalComponent by the specified uniqueId.
     *
     * @param vitalComponentUniqueId The uniqueId of the VitalComponent.
     * @return An Optional with the value of the fetched VitalComponent, an empty Optional otherwise.
     */
    public final Optional<T> getVitalComponent(@NotNull UUID vitalComponentUniqueId) {
        return vitalComponentList.stream()
                .filter(vitalComponent -> vitalComponentUniqueId.equals(vitalComponent.getUniqueId()))
                .findFirst();
    }

    /**
     * Gets the VitalComponent by the specified name.
     *
     * @param name The name of the VitalComponent.
     * @return An Optional with the value of the fetched VitalComponent, an empty Optional otherwise.
     */
    public final Optional<T> getVitalComponent(@NotNull String name) {
        return vitalComponentList.stream()
                .filter(vitalComponent -> name.equals(vitalComponent.getName()))
                .findFirst();
    }

    /**
     * Gets the VitalComponent by the specified class.
     *
     * @param vitalComponentClass The class of the VitalComponent.
     * @return An Optional with the value of the fetched VitalComponent, an empty Optional otherwise.
     */
    public final <X extends VitalComponent> Optional<X> getVitalComponent(@NotNull Class<X> vitalComponentClass) {
        return vitalComponentList.stream()
                .filter(vitalComponent -> vitalComponentClass.equals(vitalComponent.getClass()))
                .map(vitalComponentClass::cast)
                .findFirst();
    }

    /**
     * Registers the specified VitalComponent.
     *
     * @param vitalComponent The VitalComponent to register.
     */
    public final void registerVitalComponent(@NotNull T vitalComponent) {
        if (isVitalComponentRegistered(vitalComponent.getUniqueId())) {
            return;
        }

        vitalComponentList.add(vitalComponent);
        vitalComponent.onRegistered();
        onVitalComponentRegistered(vitalComponent);
    }

    /**
     * Unregisters the specified VitalComponent.
     *
     * @param vitalComponent The VitalComponent to unregister.
     */
    public final void unregisterVitalComponent(@NotNull T vitalComponent) {
        vitalComponentList.remove(vitalComponent);
        vitalComponent.onUnregistered();
        onVitalComponentUnregistered(vitalComponent);
    }

    /**
     * Called when the specified VitalComponent is registered.
     *
     * @param vitalComponent The VitalComponent registered.
     */
    public abstract void onVitalComponentRegistered(@NotNull T vitalComponent);

    /**
     * Called when the specified VitalComponent is unregistered.
     *
     * @param vitalComponent The VitalComponent unregistered.
     */
    public abstract void onVitalComponentUnregistered(@NotNull T vitalComponent);

    protected void onEnable() {

    }

    public final void enable() {
        onEnable();
    }
}
