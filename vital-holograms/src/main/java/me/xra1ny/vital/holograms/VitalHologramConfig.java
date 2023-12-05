package me.xra1ny.vital.holograms;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
@VitalConfigInfo("holograms.yml")
public final class VitalHologramConfig extends VitalConfig {
    /**
     * The list of currently saved VitalHolograms
     */
    public List<VitalHologram> vitalHologramList;

    public VitalHologramConfig(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}
