package me.xra1ny.vital.configs;

import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalSubModule;
import org.jetbrains.annotations.NotNull;

@Component
public class VitalConfigsSubModule implements VitalSubModule {
    @Override
    public @NotNull String getName() {
        return "vital-configs";
    }
}
