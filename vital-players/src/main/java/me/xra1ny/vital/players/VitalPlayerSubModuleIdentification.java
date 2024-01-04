package me.xra1ny.vital.players;

import me.xra1ny.vital.core.VitalSubModuleIdentification;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

@VitalDI
@VitalAutoRegistered
public class VitalPlayerSubModuleIdentification implements VitalSubModuleIdentification {
    @Override
    @NotNull
    public String getName() {
        return "vital-players";
    }
}
