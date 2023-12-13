package me.xra1ny.vital.inventories;

import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * Defines an event that is called when a player opens an {@link VitalInventoryBuilder} inventory.
 *
 * @author xRa1ny
 */
public interface VitalInventoryOpenEvent {
    /**
     * Event method called upon opening a {@link VitalInventoryBuilder} inventory instance.
     *
     * @param player The {@link Player} instance that opened the inventory.
     */
    void onVitalInventoryOpen(@NonNull Player player);
}
