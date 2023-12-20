package me.xra1ny.vital.core;

import lombok.Getter;
import lombok.NonNull;
import me.xra1ny.vital.core.annotation.VitalManagerAutoRegistered;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Abstract class for managing a list of Vital components.
 * Provides methods to register, unregister, and retrieve components from the list.
 *
 * @param <T> The type of Vital components managed by this class.
 * @author xRa1ny
 */
public abstract class VitalComponentListManager<T extends VitalComponent> implements VitalComponent {
    /**
     * Cache a set of all subclasses of the type this manager manages.
     */
    private Set<Class<?>> vitalComponentClassSet = new LinkedHashSet<>();

    @Getter
    @NonNull
    private final List<T> vitalComponentList = new ArrayList<>();

    /**
     * Gets a {@link List} of all {@link VitalComponent} instance matching the supplied class.
     *
     * @param clazz The class to match the registered {@link VitalComponent} of this manager with.
     * @param <T>   The type of the {@link VitalComponent} to match with.
     * @return A {@link List} of all {@link VitalComponent} instances matching the supplied class.
     */
    public final <T> List<T> getVitalComponentList(@NonNull Class<T> clazz) {
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
    public final boolean isVitalComponentRegistered(@NonNull UUID vitalComponentUniqueId) {
        return getVitalComponent(vitalComponentUniqueId).isPresent();
    }

    /**
     * Checks if a VitalComponent is registered with the specified name.
     *
     * @param vitalComponentName The name of the VitalComponent.
     * @return true if a VitalComponent is registered with the specified name, false otherwise.
     */
    public final boolean isVitalComponentRegistered(@NonNull String vitalComponentName) {
        return getVitalComponent(vitalComponentName).isPresent();
    }

    /**
     * Checks if a VitalComponent is registered with the specified class.
     *
     * @param vitalComponentClass The class of the VitalComponent.
     * @return true if a VitalComponent is registered with the specified class, false otherwise.
     */
    public final boolean isVitalComponentRegistered(@NonNull Class<? extends VitalComponent> vitalComponentClass) {
        return getVitalComponent(vitalComponentClass).isPresent();
    }

    /**
     * Gets the VitalComponent by the specified uniqueId.
     *
     * @param vitalComponentUniqueId The uniqueId of the VitalComponent.
     * @return An Optional with the value of the fetched VitalComponent, an empty Optional otherwise.
     */
    public final Optional<T> getVitalComponent(@NonNull UUID vitalComponentUniqueId) {
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
    public final Optional<T> getVitalComponent(@NonNull String name) {
        return vitalComponentList.stream()
                .filter(vitalComponent -> name.equals(vitalComponent.getName()))
                .findFirst();
    }

    /**
     * Gets the VitalComponent by the specified class.
     *
     * @param vitalComponentClass The class of the VitalComponent.
     * @param <X>                 The {@link VitalComponent} type to grab from this manager instance.
     * @return An Optional with the value of the fetched VitalComponent, an empty Optional otherwise.
     */
    public final <X extends VitalComponent> Optional<X> getVitalComponent(@NonNull Class<X> vitalComponentClass) {
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
    public final void registerVitalComponent(@NonNull T vitalComponent) {
        if (isVitalComponentRegistered(vitalComponent.getUniqueId())) {
            return;
        }

        vitalComponentList.add(vitalComponent);
        vitalComponent.onRegistered();
        onVitalComponentRegistered(vitalComponent);
    }

    /**
     * Attempts to register the supplied {@link VitalComponent} class using {@link VitalDIUtils} to create a dependency injected instance.
     *
     * @param vitalComponentClass The class of the {@link VitalComponent} to register if a dependency injected instance could be created successfully.
     */
    public final void registerVitalComponent(@NonNull Class<? extends T> vitalComponentClass) {
        // attempt to get dependency injected instance of parameter class.
        final Optional<? extends T> optionalInstance = VitalDIUtils.getDependencyInjectedInstance(vitalComponentClass);

        optionalInstance.ifPresent(this::registerVitalComponent);
    }

    /**
     * Unregisters the specified VitalComponent.
     *
     * @param vitalComponent The VitalComponent to unregister.
     */
    public final void unregisterVitalComponent(@NonNull T vitalComponent) {
        vitalComponentList.remove(vitalComponent);
        vitalComponent.onUnregistered();
        onVitalComponentUnregistered(vitalComponent);
    }

    /**
     * Attempts to unregister a {@link VitalComponent} by its specified class.
     *
     * @param vitalComponentClass The class of the {@link VitalComponent} to unregister.
     */
    public final void unregisterVitalComponent(@NonNull Class<? extends T> vitalComponentClass) {
        final Optional<? extends T> optionalInstance = getVitalComponent(vitalComponentClass);

        optionalInstance.ifPresent(this::unregisterVitalComponent);
    }

    /**
     * Called when the specified VitalComponent is registered.
     *
     * @param vitalComponent The VitalComponent registered.
     */
    public abstract void onVitalComponentRegistered(@NonNull T vitalComponent);

    /**
     * Called when the specified VitalComponent is unregistered.
     *
     * @param vitalComponent The VitalComponent unregistered.
     */
    public abstract void onVitalComponentUnregistered(@NonNull T vitalComponent);

    /**
     * Defines the type that is managed by this manager
     *
     * @return The type of {@link VitalComponent} this manager manages.
     * @apiNote Needed for dependency injection pattern.
     */
    public abstract Class<T> managedType();

    /**
     * Returns the class value of all classes this component manager manages,
     * if not already loaded, loads and caches all classes found within the bounds of the implementing Vital Main Class Package.
     *
     * @return A Set of all Classes of Components this Manager manages.
     */
    public Set<Class<?>> getVitalComponentClassSet() {
        if (vitalComponentClassSet.isEmpty()) {
            vitalComponentClassSet = VitalCore.getScannedClassSet().stream()
                    .filter(managedType()::isAssignableFrom)
                    .collect(Collectors.toSet());
        }

        return vitalComponentClassSet;
    }

    /**
     * Fetches all classes viable for automatic dependency injection and registers them on this Manager Instance.
     *
     * @apiNote For this implementation to work, {@link VitalComponentListManager#managedType()} has to be implemented and correctly configured.
     */
    public void enable() {
        // iterate over every subclass and attempt to create a dependency injected instance.
        for (Class<?> vitalComponentClass : getVitalComponentClassSet().stream()
                // only get classes annotated with `@VitalManagerAutoRegistered`
                .filter(clazz -> clazz.getDeclaredAnnotation(VitalManagerAutoRegistered.class) != null)
                .toList()) {
            // attempt to get the dependency injected instance of the vitalcomponent this manager manages...
            final Optional<? extends T> optionalVitalComponent = (Optional<? extends T>) VitalDIUtils.getDependencyInjectedInstance(vitalComponentClass);

            // if that component could be dependency injected and created, register it on our manager.
            optionalVitalComponent.ifPresent(this::registerVitalComponent);
        }
    }
}
