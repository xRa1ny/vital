package me.xra1ny.vital.players;

import lombok.NonNull;
import lombok.SneakyThrows;
import me.xra1ny.vital.core.VitalListener;
import me.xra1ny.vital.core.annotation.VitalDI;
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
@VitalDI
public abstract class VitalPlayerListener<T extends VitalPlayer> extends VitalListener {
    private final VitalPlayerManager<T> vitalPlayerManager;

    /**
     * Creates a new instance of VitalPlayerListener.
     *
     * @param vitalPlayerManager The VitalUserManagement instance to manage VitalPlayer components.
     */
    public VitalPlayerListener(@NonNull VitalPlayerManager<T> vitalPlayerManager) {
        this.vitalPlayerManager = vitalPlayerManager;
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
        final Optional<T> optionalVitalPlayer = vitalPlayerManager.getVitalComponent(e.getPlayer().getUniqueId());

        if (optionalVitalPlayer.isEmpty()) {
            // Create a new VitalPlayer for the joining player.
            final T vitalPlayer = vitalPlayerType().getDeclaredConstructor(Player.class).newInstance(e.getPlayer());

            // Register the VitalPlayer with VitalUserManagement.
            vitalPlayerManager.registerVitalComponent(vitalPlayer);
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
        final Optional<T> optionalVitalPlayer = vitalPlayerManager.getVitalComponent(e.getPlayer().getUniqueId());

        if (optionalVitalPlayer.isEmpty()) {
            return;
        }

        final T vitalPlayer = optionalVitalPlayer.get();

        // Unregister the VitalPlayer from VitalUserManagement.
        vitalPlayerManager.unregisterVitalComponent(vitalPlayer);
    }

    /**
     * Defines the type this {@link VitalPlayerListener} manages.
     *
     * @return The type of the managed {@link VitalPlayer}.
     */
    protected abstract Class<T> vitalPlayerType();
}

