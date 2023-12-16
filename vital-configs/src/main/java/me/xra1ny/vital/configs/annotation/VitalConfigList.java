package me.xra1ny.vital.configs.annotation;

import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigSerializable;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Defines that the annotated field is a {@link java.util.List} that manages the specified type within an implementing {@link VitalConfig}.
 *
 * @author xRa1ny
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalConfigList {
    /**
     * Defines the type to be managed by the annotated field {@link java.util.List}.
     *
     * @return The managed type.
     */
    Class<? extends VitalConfigSerializable> value();
}
