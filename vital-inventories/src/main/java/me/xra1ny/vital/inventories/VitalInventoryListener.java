package me.xra1ny.vital.inventories;

import me.xra1ny.vital.core.VitalListener;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Listener for handling VitalInventoryMenu related events.
 *
 * @author xRa1ny
 */
public final class VitalInventoryListener extends VitalListener {
    /**
     * Handles the event when a player opens an inventory.
     *
     * @param e The InventoryOpenEvent.
     */
    @EventHandler
    public void onPlayerOpenInventory(@NotNull InventoryOpenEvent e) {
        final Player player = (Player) e.getPlayer();

        if (e.getInventory().getHolder() instanceof VitalInventory vitalInventory) {
            vitalInventory.onOpen(e);

            vitalInventory.setBackground();
            vitalInventory.setItems(player);

            if (vitalInventory instanceof VitalPagedInventory vitalPagedInventoryMenu) {
                vitalPagedInventoryMenu.setPage(1, player);
            }
        } else if (e.getInventory().getHolder() instanceof VitalInventoryBuilder vitalInventoryBuilder) {
            final VitalInventoryOpenEvent vitalInventoryOpenEvent = vitalInventoryBuilder.getVitalInventoryOpenEvent();

            vitalInventoryOpenEvent.onVitalInventoryOpen(player);
        }
    }

    /**
     * Handles the event when a player clicks in an inventory.
     *
     * @param e The InventoryClickEvent.
     */
    @EventHandler
    public void onPlayerClickInInventory(@NotNull InventoryClickEvent e) {
        final InventoryHolder inventoryHolder = e.getInventory().getHolder();
        final Player player = (Player) e.getWhoClicked();

        if (inventoryHolder instanceof VitalInventory vitalInventory) {
            // If the User clicks outside of Inventory Window, close it
            if (e.getClickedInventory() == null && vitalInventory.getPreviousInventory() != null) {
                player.openInventory(vitalInventory.getPreviousInventory());

                return;
            }

            if (e.getCurrentItem() == null) {
                return;
            }

            vitalInventory.handleClick(e);
            e.setCancelled(true);
        } else if (inventoryHolder instanceof VitalInventoryBuilder vitalInventoryBuilder) {
            // If the User clicks outside of Inventory Window, close it
            if (e.getClickedInventory() == null && vitalInventoryBuilder.getPreviousInventory() != null) {
                player.openInventory(vitalInventoryBuilder.getPreviousInventory());

                return;
            }

            if (e.getCurrentItem() == null) {
                return;
            }

            // Default click event (global)
            final VitalInventoryClickEvent vitalInventoryClickEvent = vitalInventoryBuilder.getVitalInventoryClickEvent();

            // Slot based click event
            final Map<Integer, VitalInventoryClickEvent> slotClickEventMap = vitalInventoryBuilder.getSlotClickEventMap();

            // ItemStack based click event
            final Map<ItemStack, VitalInventoryClickEvent> itemStackClickEventMap = vitalInventoryBuilder.getItemStackClickEventMap();

            final VitalInventoryClickEvent.Action inventoryAction;

            // Compute the associated click event with mapped elements.
            if (slotClickEventMap.containsKey(e.getSlot())) {
                // If we mapped a by SLOT element click event, handle it here...
                final VitalInventoryClickEvent clickEvent = slotClickEventMap.get(e.getSlot());

                inventoryAction = clickEvent.onVitalInventoryClick(player, e.getCurrentItem());
            } else if (itemStackClickEventMap.containsKey(e.getCurrentItem())) {
                // If we mapped a by ITEMSTACK element click event, handle it here...
                final VitalInventoryClickEvent clickEvent = itemStackClickEventMap.get(e.getCurrentItem());

                inventoryAction = clickEvent.onVitalInventoryClick(player, e.getCurrentItem());
            } else {
                // Call the general click event if no other events have been mapped.
                inventoryAction = vitalInventoryClickEvent.onVitalInventoryClick(player, e.getCurrentItem());
            }

            // check the action of our click result, if set to `CLOSE_INVENTORY`, close the inventory accordingly.
            if (inventoryAction == VitalInventoryClickEvent.Action.CLOSE_INVENTORY) {
                player.closeInventory();
            } else if (inventoryAction == VitalInventoryClickEvent.Action.OPEN_PREVIOUS_INVENTORY) {
                final Inventory previousInventory = vitalInventoryBuilder.getPreviousInventory();

                if (previousInventory != null) {
                    player.openInventory(previousInventory);
                }
            }

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
        final InventoryHolder inventoryHolder = e.getInventory().getHolder();
        final Player player = (Player) e.getPlayer();

        if (inventoryHolder instanceof VitalInventory vitalInventory) {
            vitalInventory.onClose(e);
        } else if (inventoryHolder instanceof VitalInventoryBuilder vitalInventoryBuilder) {
            vitalInventoryBuilder.getVitalInventoryCloseEvent().onVitalInventoryClose(player);
        }
    }
}
