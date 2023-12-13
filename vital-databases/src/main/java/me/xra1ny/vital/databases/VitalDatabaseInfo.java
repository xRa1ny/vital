package me.xra1ny.vital.databases;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation used to specify the URL to the Hibernate configuration XML file
 * that defines the database connection settings and entity mappings for a database component. This annotation
 * should be applied to classes that implement the {@code VitalDatabase} component.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VitalDatabaseInfo {
    /**
     * The URL to the .cfg.xml file that configures the Hibernate database connection.
     *
     * @return The URL to the .cfg.xml file.
     */
    String value();
}
