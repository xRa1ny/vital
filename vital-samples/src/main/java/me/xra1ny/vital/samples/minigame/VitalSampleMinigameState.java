package me.xra1ny.vital.samples.minigame;

import me.xra1ny.vital.minigames.VitalMinigameState;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class VitalSampleMinigameState extends VitalMinigameState {
    public VitalSampleMinigameState(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public void onVitalMinigameStateRegistered() {
        // Called when this minigame state is registered (enabled).
    }

    @Override
    public void onVitalMinigameStateUnregistered() {
        // Called wen this minigame state is unregistered (disabled).
    }
}
