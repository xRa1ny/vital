package me.xra1ny.vital.samples.full.config;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigInfo;
import me.xra1ny.vital.configs.VitalConfigPath;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@Getter
@Setter
@VitalConfigInfo("sampleconfig.yml")
public final class SampleVitalConfig extends VitalConfig {
    /**
     * This value may be saved in `sampleconfig.yml` under `sample-config-field: VALUE`
     */
    @VitalConfigPath("sample-config-field")
    private String sampleConfigField;

    public SampleVitalConfig(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}
