package me.xra1ny.vital.samples.full.config;

import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigInfo;
import me.xra1ny.vital.configs.VitalConfigPath;
import me.xra1ny.vital.holograms.VitalHologram;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@VitalConfigInfo("sampleconfig.yml")
public final class SampleVitalConfig extends VitalConfig {
    /**
     * This value may be saved in `sampleconfig.yml` under `sample-config-field: VALUE`
     */
    @VitalConfigPath("sample-config-field")
    public String sampleConfigField;

    /**
     * This value may be saved in `sampleconfig.yml` under `sample-vital-hologram-config-field`
     * This Implementation shows, that even complex objects, may be saved in a config, IF THEY IMPLEMENT `VitalConfigSerializable` AND ARE CORRECTLY CONFIGURED FOR INSTANTIATION!
     */
    @VitalConfigPath("sample-vital-hologram-config-field")
    public VitalHologram sampleVitalHologramConfigField;

    /**
     * This value may be saved in `sampleconfig.yml` under `sample-vital-hologram-list-config-field`
     * This Implementation shows, that even a List of complex objects, may be saved in a config, IF THE MANAGED LIST OBJECT IMPLEMENTS `VitalConfigSerializable` AND ARE CORRECTLY CONFIGURED FOR INSTANTIATION!
     */
    @VitalConfigPath("sample-vital-hologram-list-config-field")
    public List<VitalHologram> sampleVitalHologramListConfigField;

    public SampleVitalConfig(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}
