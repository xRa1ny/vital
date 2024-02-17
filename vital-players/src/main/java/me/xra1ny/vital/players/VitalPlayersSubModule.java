package me.xra1ny.vital.players;

import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalSubModule;
import org.jetbrains.annotations.NotNull;

@Component
public class VitalPlayersSubModule implements VitalSubModule {
    @Override
    @NotNull
    public String getName() {
        return "vital-players";
    }
}
