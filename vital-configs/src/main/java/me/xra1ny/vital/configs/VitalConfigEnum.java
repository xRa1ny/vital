package me.xra1ny.vital.configs;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This Annotation is used to specify a Field to be an Enumeration Value from Config, it translates the String fetched from Config into the Enumeration Type.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalConfigEnum {

}
