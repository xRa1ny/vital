package me.xra1ny.vital.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public interface VitalAutoRegisterable {
    void autoRegister(@NotNull Class<JavaPlugin> javaPluginType);
}
