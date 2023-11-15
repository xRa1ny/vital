package me.xra1ny.vital.inventories;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface VitalInventoryClickEvent {
    Action onVitalInventoryClick(@NotNull Player player, @Nullable ItemStack itemStack);

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
