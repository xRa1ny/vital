package me.xra1ny.vital.players;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.core.VitalComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * Represents a player as a VitalComponent, providing access to the player's data and functionality.
 *
 * @author xRa1ny
 */
public class VitalPlayer implements VitalComponent {
    /**
     * The Minecraft player associated with this VitalPlayer.
     */
    @Getter(onMethod = @__(@NotNull))
    private final Player player;

    /**
     * the timeout of this user (unregisters this user when equal to the configured timeout in manager object)
     */
    @Getter
    @Setter
    private int timeout;

    /**
     * Creates a new instance of VitalPlayer for the given Minecraft player.
     *
     * @param player The Minecraft player to associate with this VitalPlayer.
     */
    public VitalPlayer(@NotNull Player player) {
        this.player = player;
    }

    /**
     * Gets the unique ID of the associated Minecraft player.
     *
     * @return The unique ID of the player.
     */
    @Override
    @NotNull
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    /**
     * Gets the name of the associated Minecraft player.
     *
     * @return The name of the player.
     */
    @Override
    @NotNull
    public String getName() {
        return player.getName();
    }

    /**
     * Called when this VitalComponent is registered.
     */
    @Override
    public void onRegistered() {

    }

    /**
     * Called when this VitalComponent is unregistered.
     */
    @Override
    public void onUnregistered() {

    }
}

