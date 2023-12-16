package me.xra1ny.vital.configs.annotation;

import me.xra1ny.vital.configs.VitalConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to specify a field to be an enumeration value from {@link VitalConfig}.
 *
 * @author xRa1ny
 * @apiNote The config value is wrapped into the specified enum type by name.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalConfigEnum {

}
