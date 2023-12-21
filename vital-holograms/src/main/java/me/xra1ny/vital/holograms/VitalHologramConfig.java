package me.xra1ny.vital.holograms;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.annotation.VitalConfigInfo;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

/**
 * The default Vital implementation of a {@link VitalConfig} that manages a list of {@link VitalHologram}.
 *
 * @author xRa1ny
 */
@VitalDI
@VitalConfigInfo("holograms.yml")
public final class VitalHologramConfig extends VitalConfig {
    /**
     * The list of currently saved VitalHolograms
     */
    @Getter
    @Setter
    @NonNull
    public List<VitalHologram> vitalHologramList;

    /**
     * Constructs a new hologram config for default Vital implementations of {@link VitalHologram} persistence.
     *
     * @param javaPlugin The {@link JavaPlugin} instance.
     */
    public VitalHologramConfig(@NonNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}
