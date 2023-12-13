package me.xra1ny.vital.players;

import lombok.NonNull;
import me.xra1ny.vital.tasks.VitalRepeatableTask;
import me.xra1ny.vital.tasks.VitalRepeatableTaskInfo;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A repeatable task for managing player timeouts and unregistering them if they remain offline for too long.
 *
 * @param <T> The type of VitalPlayer to manage.
 * @author xRa1ny
 */
@VitalRepeatableTaskInfo(value = 50)
public class VitalPlayerTimeoutHandler<T extends VitalPlayer> extends VitalRepeatableTask {
    private final VitalPlayerManager<T> vitalPlayerManager;

    /**
     * Constructs a VitalPlayerTimeoutHandler instance.
     *
     * @param javaPlugin         The JavaPlugin instance associated with this handler.
     * @param vitalPlayerManager The management component responsible for VitalPlayers.
     */
    public VitalPlayerTimeoutHandler(@NonNull JavaPlugin javaPlugin, @NonNull VitalPlayerManager<T> vitalPlayerManager) {
        super(javaPlugin);
        this.vitalPlayerManager = vitalPlayerManager;
    }

    @Override
    public final Class<VitalRepeatableTaskInfo> requiredAnnotationType() {
        return VitalRepeatableTaskInfo.class;
    }

    @Override
    public final void onStart() {

    }

    @Override
    public final void onStop() {

    }

    /**
     * Called periodically based on the specified interval to manage player timeouts.
     * Checks if offline players have reached their timeout threshold and unregisters them if necessary.
     */
    @Override
    public final void onTick() {
        for (T vitalPlayer : vitalPlayerManager.getVitalComponentList()) {
            // Check if the player is currently online. If so, skip to the next player.
            if (vitalPlayer.getPlayer().isOnline()) {
                continue;
            }

            // Check if the player's timeout has reached or fallen below zero.
            if (vitalPlayer.getTimeout() >= vitalPlayerManager.getVitalPlayerTimeout()) {
                // Unregister the VitalPlayer if the timeout threshold is exceeded.
                vitalPlayerManager.unregisterVitalComponent(vitalPlayer);
            } else {
                // Increase the player's timeout by 1 if they are still within the threshold.
                vitalPlayer.setTimeout(vitalPlayer.getTimeout() + 1);
            }
        }
    }

    @Override
    public final void onRegistered() {

    }

    @Override
    public final void onUnregistered() {

    }
}
