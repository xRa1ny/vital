package me.xra1ny.vital.core.processor;

import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalSubModule;
import org.jetbrains.annotations.NotNull;

@Component
public class VitalCoreProcessorSubModule implements VitalSubModule {
    @Override
    @NotNull
    public String getName() {
        return "vital-core-processor";
    }
}
