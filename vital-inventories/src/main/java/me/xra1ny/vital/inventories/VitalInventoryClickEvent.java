package me.xra1ny.vital.inventories;

import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * Interface defining an event that is called when a player clicks within an {@link VitalInventoryBuilder} inventory.
 *
 * @author xRa1ny
 */
public interface VitalInventoryClickEvent {
    /**
     * Event method called for every click to the specified {@link ItemStack}.
     *
     * @param player    The {@link Player} that clicked the passed {@link ItemStack}.
     * @param itemStack The {@link ItemStack} clicked.
     * @return The {@link Action} to perform after the click has been processed.
     */
    Action onVitalInventoryClick(@NonNull Player player, @Nullable ItemStack itemStack);

    /**
     * Defines an action taken on any {@link VitalInventoryBuilder} inventory instance.
     *
     * @author xRa1ny
     */
    enum Action {
        /**
         * Do nothing.
         */
        DO_NOTHING,

        /**
         * Close the inventory menu.
         */
        CLOSE_INVENTORY,

        /**
         * Open the previous inventory menu.
         */
        OPEN_PREVIOUS_INVENTORY
    }
}
