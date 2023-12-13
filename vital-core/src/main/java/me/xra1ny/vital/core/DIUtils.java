package me.xra1ny.vital.core;

import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
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
public class DIUtils {
    public static <T> Optional<T> getDependencyInjectedInstance(@NotNull Class<T> type) {
        if (VitalComponent.class.isAssignableFrom(type)) {
            // check if instance is already existent on base vital manager...
            final Class<? extends VitalComponent> vitalComponentClass = (Class<? extends VitalComponent>) type;
            final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
            final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();

            final Optional<? extends VitalComponent> optionalVitalComponent = vitalComponentManager.getVitalComponent(vitalComponentClass);

            if (optionalVitalComponent.isPresent()) {
                return (Optional<T>) optionalVitalComponent;
            }

            // if not, attempt to get instance from any manager on vital...
            for (VitalComponentListManager<?> vitalComponentListManager : vitalComponentManager.getVitalComponentList(VitalComponentListManager.class)) {
                if (!vitalComponentListManager.managedType().isAssignableFrom(vitalComponentClass)) {
                    continue;
                }

                final Optional<? extends VitalComponent> optionalVitalComponent1 = vitalComponentListManager.getVitalComponent(vitalComponentClass);

                if (optionalVitalComponent1.isPresent()) {
                    return (Optional<T>) optionalVitalComponent1;
                }
            }
        }

        final VitalDI vitalDI = type.getDeclaredAnnotation(VitalDI.class);

        // check if component is viable for automatic dependency injection (DI).
        if (vitalDI == null) {
            return Optional.empty();
        }

        log.warning("Vital is attempting to create a dependency injected instance of " + type.getSimpleName());

        // if the class we are trying to automatically DI is abstract, cancel operation since no instance can be created without throwing exceptions.
        if (Modifier.isAbstract(type.getModifiers())) {
            log.severe(type.getSimpleName() + " is annotated with `@VitalDI` but abstract and can therefore not be dependency injected by Vital");

            return Optional.empty();
        }

        try {
            // attempt to get default constructor...
            final Constructor<T> defaultConstructor = type.getDeclaredConstructor();

            try {
                final T instance = defaultConstructor.newInstance();

                return Optional.of(instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            for (Constructor<?> constructor : type.getDeclaredConstructors()) {
                final List<Object> injectableList = new ArrayList<>();

                for (Parameter parameter : constructor.getParameters()) {
                    if (!VitalComponent.class.isAssignableFrom(parameter.getType()) && !JavaPlugin.class.isAssignableFrom(parameter.getType())) {
                        // ignore when parameter is not of type `JavaPlugin` or `VitalComponent`.
                        break;
                    }

                    if (JavaPlugin.class.isAssignableFrom(parameter.getType())) {
                        injectableList.add(VitalCore.getVitalCoreInstance().getJavaPlugin());

                        continue;
                    }

                    if (VitalComponentListManager.class.isAssignableFrom(parameter.getType())) {
                        final Class<? extends VitalComponentListManager<?>> managerClass = (Class<? extends VitalComponentListManager<?>>) parameter.getType();
                        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
                        final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();

                        final Optional<? extends VitalComponentListManager<?>> optionalVitalComponentListManager = vitalComponentManager.getVitalComponent(managerClass);

                        optionalVitalComponentListManager.ifPresent(injectableList::add);

                        continue;
                    }

                    // normal vital component. VitalConfig
                    final Class<? extends VitalComponent> vitalComponentClass = (Class<? extends VitalComponent>) parameter.getType();
                    final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
                    final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();

                    final Optional<? extends VitalComponent> optionalVitalComponent = vitalComponentManager.getVitalComponent(vitalComponentClass);

                    if (optionalVitalComponent.isPresent()) {
                        injectableList.add(optionalVitalComponent.get());

                        continue;
                    }

                    // if not present on base.
                    for (VitalComponentListManager<?> vitalComponentListManager : vitalComponentManager.getVitalComponentList(VitalComponentListManager.class)) {
                        if (!vitalComponentListManager.managedType().isAssignableFrom(vitalComponentClass)) {
                            continue;
                        }

                        // enable that manager for potentially missing components.
                        vitalComponentListManager.enable();

                        // afterward attempt to get the instance we're looking for.
                        final Optional<? extends VitalComponent> optionalVitalComponent1 = vitalComponentListManager.getVitalComponent(vitalComponentClass);

                        optionalVitalComponent1.ifPresent(injectableList::add);
                    }
                }

                try {
                    // create dependency injected instance.
                    final T instance = (T) constructor.newInstance(injectableList.toArray());

                    log.info("Vital has successfully created dependency injected instance of " + type.getSimpleName());

                    return Optional.of(instance);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }

            }
        }

        return Optional.empty();
    }
}
