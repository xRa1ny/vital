package me.xra1ny.vital.scoreboards;

import me.xra1ny.vital.core.VitalSubModuleIdentification;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.jetbrains.annotations.NotNull;

@VitalDI
@VitalAutoRegistered
public class VitalScoreboardSubModuleIdentification implements VitalSubModuleIdentification {
    @Override
    @NotNull
    public String getName() {
        return "vital-scoreboards";
    }
}
