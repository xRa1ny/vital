package me.xra1ny.vital.inventories;

import lombok.NonNull;
import org.bukkit.entity.Player;

/**
 * Interface defining an event that is called when a player closes an {@link VitalInventoryBuilder} inventory.
 *
 * @author xRa1ny
 */
public interface VitalInventoryCloseEvent {
    /**
     * Event method called upon the {@link VitalInventoryBuilder} inventory closing for the passed {@link Player}.
     *
     * @param player The {@link Player} this inventory instance was closed for.
     */
    void onVitalInventoryClose(@NonNull Player player);
}
