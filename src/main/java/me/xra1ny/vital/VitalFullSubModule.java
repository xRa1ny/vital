package me.xra1ny.vital;

import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalSubModule;
import org.jetbrains.annotations.NotNull;

@Component
public final class VitalFullSubModule implements VitalSubModule {
    @Override
    public @NotNull String getName() {
        return "vital";
    }
}
