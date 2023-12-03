package me.xra1ny.vital.core;

import lombok.extern.java.Log;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

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
public class DIUtils {
    public static <T> Optional<T> getDependencyInjectedInstance(@NotNull Class<T> type) {
        try {
            // attempt to get default constructor...
            final Constructor<T> defaultConstructor = type.getDeclaredConstructor();

            try {
                final T instance = defaultConstructor.newInstance();

                return Optional.of(instance);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                log.severe("error: " + e.getCause().getMessage());
            }
        } catch (NoSuchMethodException e) {
            for(Constructor<?> constructor : type.getDeclaredConstructors()) {
                final List<Object> injectableList = new ArrayList<>();

                for(Parameter parameter : constructor.getParameters()) {
                    if(!VitalComponent.class.isAssignableFrom(parameter.getType()) && !JavaPlugin.class.isAssignableFrom(parameter.getType())) {
                        // ignore when parameter is not of type `JavaPlugin` or `VitalComponent`.
                        break;
                    }

                    if(JavaPlugin.class.isAssignableFrom(parameter.getType())) {
                        injectableList.add(VitalCore.getVitalCoreInstance().getJavaPlugin());

                        continue;
                    }

                    if(VitalComponentListManager.class.isAssignableFrom(parameter.getType())) {
                        final Class<? extends VitalComponentListManager<?>> managerClass = (Class<? extends VitalComponentListManager<?>>) parameter.getType();
                        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
                        final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();

                        Optional<? extends VitalComponentListManager<?>> optionalVitalComponentListManager = vitalComponentManager.getVitalComponent(managerClass);

                        log.info("list: " + optionalVitalComponentListManager);

                        optionalVitalComponentListManager.ifPresent(injectableList::add);

                        continue;
                    }

                    // normal vital component.
                    final Class<? extends VitalComponent> vitalComponentClass = (Class<? extends VitalComponent>) parameter.getType();
                    final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
                    final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();

                    final Optional<? extends VitalComponent> optionalVitalComponent = vitalComponentManager.getVitalComponent(vitalComponentClass);

                    if(optionalVitalComponent.isPresent()) {
                        injectableList.add(optionalVitalComponent.get());

                        continue;
                    }

                    log.info(vitalComponentClass + " is not present on base...");

                    // if not present on base.
                    for (VitalComponentListManager<?> vitalComponentListManager : vitalComponentManager.getVitalComponentList(VitalComponentListManager.class)) {
                        if(!vitalComponentClass.isAssignableFrom(vitalComponentListManager.getClass())) {
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
                    log.severe("constructor: " + constructor);
                    log.severe("parameters: " + injectableList);

                    // create dependency injected instance.
                    final T instance = (T) constructor.newInstance(injectableList.toArray());

                    log.warning("successfully created instance of " + type);

                    return Optional.of(instance);
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    throw new RuntimeException(ex);
                }

            }
        }

        return Optional.empty();
    }
//    public static <T> Optional<T> getDependencyInjectedInstance(@NotNull Class<T> clazz) {
//        log.info("Vital is attempting to create a dependency injected instance of " + clazz);
//
//        // attempt to get the default POJO constructor
//        try {
//            final Constructor<T> defaultConstructor = clazz.getDeclaredConstructor();
//
//            // constructor found! create new instance and return.
//            try {
//                final T instance = defaultConstructor.newInstance();
//
//                return Optional.of(instance);
//            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
//                // unexpected error occurred, return with log.
//                log.severe("Error while creating instance of dependency injected class!");
//                e.printStackTrace();
//            }
//        } catch (NoSuchMethodException e) {
////            log.warning("Vital has not detected a suitable default constructor for " + clazz);
////            log.warning("deciphering argumented constructor...");
//            // cannot get default POJO constructor,
//            // therefore there must be a different constructor with arguments...
//            // decipher that constructor.
//
//            // firstly loop over every constructor in our class...
//            for (Constructor<?> constructor : clazz.getDeclaredConstructors()) {
//                final List<Object> injectableList = new ArrayList<>();
//
//                // then loop over every parameter in that constructor to decipher the origin of each type.
//                for (Parameter parameter : constructor.getParameters()) {
//                    // now check if we can inject those parameters, if not, we cancel this operation.
//
//                    if (JavaPlugin.class.isAssignableFrom(parameter.getType())) {
//                        // if the parameter is of type `JavaPlugin` we can add the plugin instance we may fetch from Vital.
//                        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
//
//                        injectableList.add(vitalCore.getJavaPlugin());
//                    } else if (VitalComponentListManager.class.isAssignableFrom(parameter.getType())) {
//                        // if the parameter is of type `VitalComponentListManager` we can fetch this instance from the base manager of Vital.
//                        final Class<? extends VitalComponentListManager<?>> vitalComponentListManagerClass = (Class<? extends VitalComponentListManager<?>>) parameter.getType();
//                        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
//                        final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();
//                        final Optional<? extends VitalComponent> optionalManager = vitalComponentManager.getVitalComponent(vitalComponentListManagerClass);
//
//                        if (optionalManager.isPresent()) {
//                            // if we could find the manager from the base manager of Vital, we may add it to the injected list.
//                            injectableList.add(optionalManager.get());
//                        } else {
//                            // if not, clear the injectable list since this constructor is invalid and cannot be used to inject...
//                            injectableList.clear();
//
//                            break;
//                        }
//                    } else if (VitalComponent.class.isAssignableFrom(parameter.getType())) {
//                        final Class<? extends VitalComponent> vitalComponentClass = (Class<? extends VitalComponent>) parameter.getType();
//                        final VitalCore<?> vitalCore = VitalCore.getVitalCoreInstance();
//                        final VitalComponentManager vitalComponentManager = vitalCore.getVitalComponentManager();
//                        final Optional<? extends VitalComponent> optionalVitalComponent = vitalComponentManager.getVitalComponent(vitalComponentClass);
//
//                        if (optionalVitalComponent.isPresent()) {
//                            injectableList.add(optionalVitalComponent.get());
//
//                            continue;
//                        }
//
//                        for (VitalComponent vitalComponent : vitalComponentManager.getVitalComponentList()) {
//                            if (!(vitalComponent instanceof VitalComponentListManager<?> manager)) {
//                                continue;
//                            }
//
//                            // tell the manager to enable and register potentially unregistered instances of the dependency we need for our instance...
//                            manager.enable();
//
//                            final Optional<? extends VitalComponent> optionalVitalComponent1 = manager.getVitalComponent(vitalComponentClass);
//
//                            if (optionalVitalComponent1.isPresent()) {
//                                injectableList.add(optionalVitalComponent1.get());
//                                log.severe("list: " + injectableList);
//                            } else {
//                                injectableList.clear();
//
//                                break;
//
//                            }
//                        }
//                    }else {
//                        injectableList.clear();
//
//                        break;
//                    }
//                }
//
//                if (!injectableList.isEmpty()) {
//                    final Object[] injectables = injectableList.toArray();
//
//                    // attempt to create instance with parameters...
//                    try {
//                        final T instance = (T) constructor.newInstance(injectables);
//
//                        log.warning("Vital successfully created a dependency injected instace for " + clazz);
//
//                        return Optional.of(instance);
//                    } catch (InstantiationException | IllegalAccessException | InvocationTargetException ex) {
//                        log.warning("Vital cannot create dependency injected instance of " + clazz);
//                        log.warning("reason: " + e.getCause().getMessage());
//                    }
//                } else {
//                    log.info("Vital could not create dependency injected instance of " + clazz);
//                }
//            }
//        }
//
//        return Optional.empty();
//    }
}
