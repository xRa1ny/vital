package me.xra1ny.vital.commands;

import me.xra1ny.vital.core.VitalSubModuleIdentification;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

@VitalDI
@VitalAutoRegistered
public class VitalCommandSubModuleIdentification implements VitalSubModuleIdentification {
    @Override
    public @NotNull String getName() {
        return "vital-commands";
    }
}
