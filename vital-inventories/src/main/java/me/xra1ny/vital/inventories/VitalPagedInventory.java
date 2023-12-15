package me.xra1ny.vital.inventories;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.Nullable;

/**
 * Used to easily create an interactive paged Inventory Menu.
 * This class extends VitalInventoryMenu for creating paginated menus.
 *
 * @author xRa1ny
 */
public abstract class VitalPagedInventory extends VitalInventory {
    /**
     * The current page of this paged inventory menu.
     */
    @Getter
    private long page = 0;

    /**
     * Constructs a new paged inventory.
     */
    public VitalPagedInventory() {
        super();
    }

    /**
     * Constructs a new paged inventory with the specified previous inventory to open after clicking out of inventory menu bounds.
     *
     * @param previousInventory The previous {@link Inventory} to open after clicking out of inventory bounds.
     */
    public VitalPagedInventory(@Nullable Inventory previousInventory) {
        super(previousInventory);
    }

    /**
     * Called when the page of this paged inventory menu changes.
     *
     * @param page   The new page.
     * @param player The player viewing the inventory.
     */
    protected void onPageChange(long page, @NonNull Player player) {

    }

    /**
     * Sets the current page of this paged inventory menu.
     *
     * @param page   The page.
     * @param player The player viewing the inventory.
     */
    public final void setPage(long page, @NonNull Player player) {
        if (page <= 0) {
            return;
        }

        this.page = page;
        onPageChange(page, player);
    }
}
