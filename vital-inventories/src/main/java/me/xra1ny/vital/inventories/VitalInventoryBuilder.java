package me.xra1ny.vital.inventories;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;

/**
 * Defines a builder all interactive inventory disobeying the "Component-Class" pattern.
 *
 * @author xRa1ny
 */
public class VitalInventoryBuilder implements InventoryHolder {
    @Getter
    @NonNull
    private final Map<Integer, ItemStack> slotItemStackMap = new HashMap<>();

    @Getter
    @NonNull
    private final Map<Integer, VitalInventoryClickEvent> slotClickEventMap = new HashMap<>();

    @Getter
    @NonNull
    private final Map<ItemStack, VitalInventoryClickEvent> itemStackClickEventMap = new HashMap<>();

    private int size = 9;

    private InventoryType inventoryType;

    private String name = "VitalInventory";

    @Getter
    @Setter
    private Inventory previousInventory;

    @Getter
    private Inventory inventory;

    @Getter
    @NonNull
    private VitalInventoryOpenEvent vitalInventoryOpenEvent = (player) -> {
    };

    @Getter
    @NonNull
    private VitalInventoryClickEvent vitalInventoryClickEvent = (player, itemStack) -> VitalInventoryClickEvent.Action.DO_NOTHING;

    @Getter
    @NonNull
    private VitalInventoryCloseEvent vitalInventoryCloseEvent = player -> {
    };

    /**
     * Define the size of this inventory.
     *
     * @param size The size of this inventory
     * @return This builder instance.
     */
    public VitalInventoryBuilder size(@Range(from = 9, to = 54) int size) {
        this.size = size;

        return this;
    }

    /**
     * Define the {@link InventoryType} to use for this inventory.
     *
     * @param inventoryType The {@link InventoryType}.
     * @return This builder instance.
     */
    public VitalInventoryBuilder inventoryType(@NonNull InventoryType inventoryType) {
        this.inventoryType = inventoryType;

        return this;
    }

    /**
     * Define the name to use for this inventory.
     *
     * @param name The name for this inventory.
     * @return This builder instance.
     */
    public VitalInventoryBuilder name(@NonNull String name) {
        this.name = name;

        return this;
    }

    /**
     * Builds this Inventory for player usages.
     *
     * @return The built Inventory to open for entities.
     */
    public final Inventory build() {
        if (inventoryType == null) {
            inventory = Bukkit.createInventory(this, size, name);
        } else {
            inventory = Bukkit.createInventory(this, inventoryType, name);
        }

        for (Map.Entry<Integer, ItemStack> entry : slotItemStackMap.entrySet()) {
            inventory.setItem(entry.getKey(), entry.getValue());
        }

        return inventory;
    }

    /**
     * Callback to when this Inventory opens.
     *
     * @param vitalInventoryOpenEvent The open event.
     * @return This builder instance.
     */
    public VitalInventoryBuilder onOpen(@NonNull VitalInventoryOpenEvent vitalInventoryOpenEvent) {
        this.vitalInventoryOpenEvent = vitalInventoryOpenEvent;

        return this;
    }

    /**
     * Sets the specified ItemStack to the specified slot, with click handling.
     *
     * @param slot                     The slot of the ItemStack.
     * @param itemStack                The ItemStack that should be displayed.
     * @param vitalInventoryClickEvent The click event expression.
     * @return This builder instance.
     */
    public VitalInventoryBuilder setItemStack(int slot, @NonNull ItemStack itemStack, @NonNull VitalInventoryClickEvent vitalInventoryClickEvent) {
        slotClickEventMap.put(slot, vitalInventoryClickEvent);
        slotItemStackMap.put(slot, itemStack);

        return this;
    }

    /**
     * Sets the specified ItemStack to the specified slot, without click handling.
     *
     * @param slot      The slot of the ItemStack.
     * @param itemStack The ItemStack that should be displayed.
     * @return This builder instance.
     */
    public VitalInventoryBuilder setItemStack(int slot, @NonNull ItemStack itemStack) {
        slotItemStackMap.put(slot, itemStack);

        return this;
    }

    /**
     * Define any logic for when a player clicks within the bounds of this inventory.
     *
     * @param vitalInventoryClickEvent The event logic to call on click.
     * @return This builder instance.
     */
    public VitalInventoryBuilder onClick(@NonNull VitalInventoryClickEvent vitalInventoryClickEvent) {
        this.vitalInventoryClickEvent = vitalInventoryClickEvent;

        return this;
    }

    /**
     * Callback to when this Inventory closes.
     *
     * @param vitalInventoryCloseEvent The close event.
     * @return This builder instance.
     */
    public VitalInventoryBuilder onClose(@NonNull VitalInventoryCloseEvent vitalInventoryCloseEvent) {
        this.vitalInventoryCloseEvent = vitalInventoryCloseEvent;

        return this;
    }
}
