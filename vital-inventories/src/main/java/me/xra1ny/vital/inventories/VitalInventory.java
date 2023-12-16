package me.xra1ny.vital.inventories;

import lombok.Getter;
import lombok.NonNull;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import me.xra1ny.vital.inventories.annotation.VitalInventoryInfo;
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
import org.jetbrains.annotations.Nullable;

/**
 * An abstract class for creating interactive inventories.
 *
 * @author xRa1ny
 */
public abstract class VitalInventory implements AnnotatedVitalComponent<VitalInventoryInfo>, InventoryHolder {

    /**
     * The title of this inventory.
     */
    @Getter
    @NonNull
    private final String title;

    /**
     * The size in slots of this inventory.
     */
    @Getter
    private final int size;

    /**
     * The inventory of this inventory menu.
     */
    @Getter
    @NonNull
    private Inventory inventory;

    /**
     * The background item of this inventory menu.
     */
    @Getter
    @NonNull
    private final ItemStack background;

    /**
     * The previous menu of this inventory menu, if any.
     */
    @Getter
    private final Inventory previousInventory;

    /**
     * Constructs a new VitalInventoryMenu instance.
     */
    public VitalInventory() {
        final VitalInventoryInfo info = getRequiredAnnotation();

        this.title = info.value();
        this.size = info.size();
        this.background = new VitalItemStackBuilder()
                .name(null)
                .type(info.background())
                .build();
        this.previousInventory = null;
    }

    /**
     * Constructs a new VitalInventoryMenu instance.
     *
     * @param previousInventory The previous menu to navigate back to, or null if there is none.
     */
    public VitalInventory(@Nullable Inventory previousInventory) {
        final VitalInventoryInfo info = getRequiredAnnotation();

        this.title = info.value();
        this.size = info.size();
        this.background = new VitalItemStackBuilder()
                .name(null)
                .type(info.background())
                .build();
        this.previousInventory = previousInventory;
    }

    @Override
    public Class<VitalInventoryInfo> requiredAnnotationType() {
        return VitalInventoryInfo.class;
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
    public void setItems(@NonNull Player player) {

    }

    /**
     * Called when this inventory menu opens.
     *
     * @param e The InventoryOpenEvent.
     */
    public void onOpen(@NonNull InventoryOpenEvent e) {

    }

    /**
     * Called when this inventory menu closes.
     *
     * @param e The InventoryCloseEvent.
     */
    public void onClose(@NonNull InventoryCloseEvent e) {

    }

    /**
     * Handles a player's click within this inventory menu.
     *
     * @param e The InventoryClickEvent.
     */
    public final void handleClick(@NonNull InventoryClickEvent e) {
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
    public void onClick(@NonNull InventoryClickEvent e) {

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
     * Builds this {@link Inventory} instance with all specified information.
     *
     * @return The {@link Inventory} built.
     * @apiNote Use this method to build an {@link Inventory} and then show it to the player using {@link Player#openInventory(Inventory)}
     */
    public final Inventory build() {
        inventory = Bukkit.createInventory(this, size, title);

        return inventory;
    }
}
