package me.xra1ny.vital.core;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class for dependency injection utilities.
 *
 * @author xRa1ny
 */
@Log
public class VitalDIUtils {
    /**
     * Attempts to create a dependency injected instance of the supplied class type.
     *
     * @param type The class from which the di instance should be created.
     * @param <T>  The type of the supplied class object.
     * @param register If the dependency injected instance should be registered upon creation.
     * @return An {@link Optional} holding either the newly created instance; or empty.
     */
    public static <T extends VitalComponent> Optional<T> getDependencyInjectedInstance(@NonNull Class<T> type, boolean register) {
        if (!type.isAnnotationPresent(VitalDI.class)) {
            log.severe("Vital cannot create a dependency injected instance of type " + type.getSimpleName());
            log.severe("@VitalDI not present");

            return Optional.empty();
        }

        final Optional<T> optionalBaseVitalComponent = getDependencyInjectedInstanceFromVitalCore(type);

        // when the instance is found on the base manager of Vital, return it.
        if (optionalBaseVitalComponent.isPresent()) {
            return optionalBaseVitalComponent;
        }

        // else create it.
        final Optional<Constructor<T>> optionalConstructor = getConstructorViableForDependencyInjection(type);

        if (optionalConstructor.isEmpty()) {
            log.severe("Vital could not decipher constructor viable for dependency injection for type " + type.getSimpleName());

            return Optional.empty();
        }

        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
        final Constructor<T> constructor = optionalConstructor.get();
        final List<Object> parameterList = new ArrayList<>();

        for (Parameter parameter : constructor.getParameters()) {
            // attempt to get implementation of parameter from base.
            if (JavaPlugin.class.isAssignableFrom(parameter.getType())) {
                parameterList.add(vitalCore.getJavaPlugin());

                continue;
            }

            if (VitalCore.class.isAssignableFrom(parameter.getType())) {
                parameterList.add(vitalCore);

                continue;
            }

            final Class<? extends VitalComponent> vitalComponentClass = (Class<? extends VitalComponent>) parameter.getType();
            final Optional<? extends VitalComponent> optionalVitalComponent = getDependencyInjectedInstance(vitalComponentClass, true);

            if (optionalVitalComponent.isEmpty()) {
                log.severe("Vital could not decipher implementation of type " + type.getSimpleName());

                continue;
            }

            parameterList.add(optionalVitalComponent.get());
        }

        final Object[] parameterArray = parameterList.toArray();
        try {
            final T instance = constructor.newInstance(parameterArray);

            // search for the types origin (if it should be registered on any particular manager, or rather the base).
            for (VitalComponentListManager<?> manager : vitalCore.getVitalComponentList(VitalComponentListManager.class)) {
                if (manager.managedType().isAssignableFrom(type)) {
                    final VitalComponentListManager<T> vitalComponentListManager = (VitalComponentListManager<T>) manager;

                    if(register) {
                        vitalComponentListManager.registerVitalComponent(instance);
                    }

                    return Optional.of(instance);
                }
            }

            if(register) {
                // not found on any child manager, register on base...
                vitalCore.registerVitalComponent(instance);
            }

            return Optional.of(instance);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.severe("Vital encountered a critical error while attempting to create a dependency injected instance of type " + type.getSimpleName());

            return Optional.empty();
        }
    }

    /**
     * Attempts to create a dependency injected instance of the supplied class type.
     * Upon creation, registers the instance within Vital.
     *
     * @param type The class from which the di instance should be created.
     * @param <T>  The type of the supplied class object.
     * @return An {@link Optional} holding either the newly created instance; or empty.
     */
    public static <T extends VitalComponent> Optional<T> getDependencyInjectedInstance(@NonNull Class<T> type) {
        return getDependencyInjectedInstance(type, true);
    }

    /**
     * Attempts to get a {@link Constructor} from the supplied type, which is viable for dependency injection within Vital.
     *
     * @param type The type which contains the {@link Constructor}.
     * @param <T>  The type of the class which holds the {@link Constructor}.
     * @return An {@link Optional} holding either the {@link Constructor}; or empty.
     */
    @NonNull
    private static <T extends VitalComponent> Optional<Constructor<T>> getConstructorViableForDependencyInjection(Class<T> type) {
        try {
            // attempt to get and return default constructor.
            return Optional.of(type.getDeclaredConstructor());
        } catch (NoSuchMethodException e) {
            // attempt to get constructor with parameters that are viable for dependency injection.
            for (Constructor<?> constructor : type.getConstructors()) {
                int parameterCount = 0;

                for (Parameter parameter : constructor.getParameters()) {
                    if (!JavaPlugin.class.isAssignableFrom(parameter.getType()) &&
                            !VitalComponent.class.isAssignableFrom(parameter.getType())) {
                        parameterCount = 0;

                        break;
                    }

                    parameterCount++;
                }

                if (parameterCount == constructor.getParameterCount()) {
                    return Optional.of((Constructor<T>) constructor);
                }
            }
        }

        return Optional.empty();
    }

    /**
     * Attempts to get a dependency injected instance from Vital's main container.
     *
     * @param type The class from which the di instance should be created.
     * @param <T>  The type of the supplied class object.
     * @return An {@link Optional} holding either the dependency injected instance; or empty.
     */
    @NonNull
    private static <T extends VitalComponent> Optional<T> getDependencyInjectedInstanceFromVitalCore(@NonNull Class<T> type) {
        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
        final Optional<T> optionalBaseVitalComponent = vitalCore.getVitalComponent(type);

        // when the type we are searching for, is not contained within Vital's main container, search all registered managers for the type.
        if (optionalBaseVitalComponent.isPresent()) {
            return optionalBaseVitalComponent;
        }

        for (VitalComponentListManager<?> manager : vitalCore.getVitalComponentList(VitalComponentListManager.class)) {
            // if the manager we currently have in this iteration, does not manage the type of our param, skip iteration.
            if (!manager.managedType().isAssignableFrom(type)) {
                continue;
            }

            // we now have a manager with the correct managing type.
            // attempt to grab instance from this manager.
            final Optional<T> optionalVitalComponent = manager.getVitalComponent(type);

            // when this instance exists on this manager, return the instance.
            if (optionalVitalComponent.isPresent()) {
                return optionalVitalComponent;
            }
        }

        return Optional.empty();
    }
}

