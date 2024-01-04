package me.xra1ny.vital.tasks;

import me.xra1ny.vital.core.VitalSubModuleIdentification;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

@VitalDI
@VitalAutoRegistered
public class VitalTaskSubModuleIdentification implements VitalSubModuleIdentification {
    @Override
    @NotNull
    public String getName() {
        return "vital-tasks";
    }
}
