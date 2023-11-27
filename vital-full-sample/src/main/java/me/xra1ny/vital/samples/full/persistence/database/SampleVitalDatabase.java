package me.xra1ny.vital.samples.full.persistence.database;

import me.xra1ny.vital.databases.VitalDatabase;
import me.xra1ny.vital.databases.VitalDatabaseInfo;

/**
 * This class defines that we have a database with meta information defined in `@VitalDatabaseInfo's hibernate.cfg.xml file`
 */
@VitalDatabaseInfo(value = "hibernate.cfg.xml")
public class SampleVitalDatabase extends VitalDatabase {
}
