package me.xra1ny.vital.inventories;

import lombok.Getter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.items.VitalItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An abstract class for creating interactive inventories.
 * Extends AnnotatedVitalComponent<VitalInventoryMenuInfo> and implements InventoryHolder.
 *
 * @author xRa1ny
 */
public abstract class VitalInventoryMenu implements AnnotatedVitalComponent<VitalInventoryMenuInfo>, InventoryHolder {

    /**
     * The title of this inventory.
     */
    @Getter(onMethod = @__(@NotNull))
    private final String title;

    /**
     * The size in slots of this inventory.
     */
    @Getter
    private final int size;

    /**
     * The inventory of this inventory menu.
     */
    @Getter(onMethod = @__(@NotNull))
    private Inventory inventory;

    /**
     * The background item of this inventory menu.
     */
    @Getter(onMethod = @__(@NotNull))
    private final ItemStack background;

    /**
     * The previous menu of this inventory menu, if any.
     */
    @Getter(onMethod = @__(@Nullable))
    private final VitalInventoryMenu previousMenu;

    /**
     * Constructs a new VitalInventoryMenu instance.
     *
     * @param previousMenu The previous menu to navigate back to, or null if there is none.
     */
    public VitalInventoryMenu(@Nullable VitalInventoryMenu previousMenu) {
        final VitalInventoryMenuInfo info = getRequiredAnnotation();

        this.title = info.title();
        this.size = info.size();
        this.background = new VitalItemStackBuilder()
                .name(null)
                .type(info.background())
                .build();
        this.previousMenu = previousMenu;
    }

    @Override
    public Class<VitalInventoryMenuInfo> requiredAnnotationType() {
        return VitalInventoryMenuInfo.class;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    /**
     * Called when this inventory menu opens and asks to be filled with items.
     *
     * @param player The player for whom the menu is opened.
     */
    public void setItems(@NotNull Player player) {

    }

    /**
     * Called when this inventory menu opens.
     *
     * @param e The InventoryOpenEvent.
     */
    public void onOpen(@NotNull InventoryOpenEvent e) {

    }

    /**
     * Called when this inventory menu closes.
     *
     * @param e The InventoryCloseEvent.
     */
    public void onClose(@NotNull InventoryCloseEvent e) {

    }

    /**
     * Handles a player's click within this inventory menu.
     *
     * @param e The InventoryClickEvent.
     */
    public final void handleClick(@NotNull InventoryClickEvent e) {
        final Player player = (Player) e.getWhoClicked();

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR
                && !e.getCurrentItem().equals(this.background)) {
            player.playSound(player.getLocation(), Sound.BLOCK_LEVER_CLICK, .3f, 1f);
        }

        onClick(e);
    }

    /**
     * Called when a player clicks within this inventory menu.
     *
     * @param e The InventoryClickEvent.
     */
    public void onClick(@NotNull InventoryClickEvent e) {

    }

    /**
     * Sets the background items in the inventory.
     */
    public final void setBackground() {
        for (int i = 0; i < this.inventory.getSize(); i++) {
            if (this.inventory.getItem(i) == null) {
                this.inventory.setItem(i, this.background);
            }
        }
    }

    /**
     * Opens this inventory menu for the specified player.
     *
     * @param player The player for whom the menu is opened.
     */
    @SuppressWarnings({"deprecation", "DataFlowIssue"})
    public final void open(@NotNull Player player) {
        this.inventory = Bukkit.createInventory(this, this.size, this.title);

        // Open the created Inventory for the specified Player
        player.getPlayer().openInventory(this.inventory);
    }
}
