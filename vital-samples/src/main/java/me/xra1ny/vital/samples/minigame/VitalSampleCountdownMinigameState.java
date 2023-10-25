package me.xra1ny.vital.samples.minigame;

import me.xra1ny.vital.minigames.VitalCountdownMinigameState;
import me.xra1ny.vital.minigames.VitalCountdownMinigameStateInfo;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

@VitalCountdownMinigameStateInfo(interval = 1000, countdown = 10)
public class VitalSampleCountdownMinigameState extends VitalCountdownMinigameState {
    public VitalSampleCountdownMinigameState(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @Override
    public Class<VitalCountdownMinigameStateInfo> requiredAnnotationType() {
        return VitalCountdownMinigameStateInfo.class;
    }

    @Override
    public void onCountdownStart() {
        // Called when the countdown of this minigame state is started.
    }

    @Override
    public void onCountdownTick() {
        // Called every countdown tick of this minigame state, in this case it would be 1000ms or 1s as it is defined at the top of this Class.
    }

    @Override
    public void onCountdownStop() {
        // Called when the countdown of this minigame state is stopped.
    }

    @Override
    public void onCountdownExpire() {
        // Called when the countdown of this minigame state expires, in this case it would be called after 10 ticks since we defined 10 at the top of this Class as the countdown.
    }

    @Override
    public void onVitalMinigameStateRegistered() {
        // Called when this minigame state is registered (enabled).
    }

    @Override
    public void onVitalMinigameStateUnregistered() {
        // Called when this minigame state is unregistered (disabled).
    }
}
