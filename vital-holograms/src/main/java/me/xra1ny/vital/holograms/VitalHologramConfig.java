package me.xra1ny.vital.holograms;

import me.xra1ny.vital.configs.VitalConfig;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class VitalHologramConfig extends VitalConfig {
    private List<VitalHologram> vitalHologramList;

    public VitalHologramConfig(@NotNull String name, @NotNull JavaPlugin javaPlugin) {
        super(name, javaPlugin);
    }
}
