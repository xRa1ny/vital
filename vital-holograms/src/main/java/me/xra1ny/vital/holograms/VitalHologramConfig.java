package me.xra1ny.vital.holograms;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.configs.VitalConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Getter
@Setter
public final class VitalHologramConfig extends VitalConfig {
    /**
     * The list of currently saved VitalHolograms
     */
    private List<VitalHologram> vitalHologramList;

    public VitalHologramConfig(@NotNull String name, @NotNull JavaPlugin javaPlugin) {
        super(name, javaPlugin);
    }
}
