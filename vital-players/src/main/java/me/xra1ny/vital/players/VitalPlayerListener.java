package me.xra1ny.vital.players;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.core.VitalListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;

/**
 * A listener class that manages VitalPlayer instances when players join and leave the server.
 *
 * @author xRa1ny
 */
public abstract class VitalPlayerListener<T extends VitalPlayer> extends VitalListener {
    private final VitalCore<?> vitalCore;

    protected VitalPlayerListener(VitalCore<?> vitalCore) {
        this.vitalCore = vitalCore;
    }


    /**
     * Handles the event when a player joins the server.
     *
     * @param e The PlayerJoinEvent.
     */
    @SneakyThrows
    @EventHandler
    public final void onPlayerJoinServer(@NonNull PlayerJoinEvent e) {
        // Retrieve the VitalPlayer associated with the joining player, if it exists.
        final Optional<VitalComponent> optionalVitalPlayer = vitalCore.getComponent(e.getPlayer().getUniqueId());

        if (optionalVitalPlayer.isEmpty()) {
            // Create a new VitalPlayer for the joining player.
            final T vitalPlayer = vitalPlayerType().getDeclaredConstructor(Player.class).newInstance(e.getPlayer());

            // Register the VitalPlayer with VitalUserManagement.
            vitalCore.registerComponent(vitalPlayer);
        }
    }

    /**
     * Handles the event when a player leaves the server.
     *
     * @param e The PlayerQuitEvent.
     */
    @EventHandler
    public final void onPlayerLeaveServer(@NonNull PlayerQuitEvent e) {
        // Retrieve the VitalPlayer associated with the leaving player.
        final Optional<VitalComponent> optionalVitalPlayer = vitalCore.getComponent(e.getPlayer().getUniqueId());

        if (optionalVitalPlayer.isEmpty()) {
            return;
        }

        final VitalComponent vitalPlayer = optionalVitalPlayer.get();

        // Unregister the VitalPlayer from VitalUserManagement.
        vitalCore.unregisterComponent(vitalPlayer);
    }

    /**
     * Defines the type this {@link VitalPlayerListener} manages.
     *
     * @return The type of the managed {@link VitalPlayer}.
     */
    protected abstract Class<T> vitalPlayerType();
}

