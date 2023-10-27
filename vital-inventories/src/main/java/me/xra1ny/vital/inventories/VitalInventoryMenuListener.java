package me.xra1ny.vital.inventories;

import me.xra1ny.vital.core.VitalListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Listener for handling VitalInventoryMenu related events.
 *
 * @author xRa1ny
 */
public final class VitalInventoryMenuListener extends VitalListener {
    /**
     * Handles the event when a player opens an inventory.
     *
     * @param e The InventoryOpenEvent.
     */
    @EventHandler
    public void onPlayerOpenInventory(@NotNull InventoryOpenEvent e) {
        final Player player = (Player) e.getPlayer();

        if (e.getInventory().getHolder() instanceof VitalInventoryMenu vitalInventoryMenu) {
            vitalInventoryMenu.onOpen(e);

            vitalInventoryMenu.setBackground();
            vitalInventoryMenu.setItems(player);

            if (vitalInventoryMenu instanceof VitalPagedInventoryMenu vitalPagedInventoryMenu) {
                vitalPagedInventoryMenu.setPage(1, player);
            }
        }
    }

    /**
     * Handles the event when a player clicks in an inventory.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler
    public void onPlayerClickInInventory(@NotNull InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();

        if (player.getPlayer().getOpenInventory().getTopInventory().getHolder() instanceof VitalInventoryMenu vitalInventoryMenu) {
            // If the User clicks outside of Inventory Window, close it
            if (e.getClickedInventory() == null && vitalInventoryMenu.getPreviousMenu() != null) {
                vitalInventoryMenu.getPreviousMenu().open(player);
                return;
            }

            vitalInventoryMenu.handleClick(e);
            e.setCancelled(true);
        }
    }

    /**
     * Handles the event when a player closes an inventory.
     *
     * @param e The InventoryCloseEvent.
     */
    @EventHandler
    public void onPlayerCloseInventory(@NotNull InventoryCloseEvent e) {
        if (e.getInventory().getHolder() instanceof VitalInventoryMenu vitalInventoryMenu) {
            vitalInventoryMenu.onClose(e);
        }
    }
}
