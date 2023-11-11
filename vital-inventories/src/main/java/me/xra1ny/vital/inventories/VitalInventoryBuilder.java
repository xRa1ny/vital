package me.xra1ny.vital.inventories;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.util.HashMap;
import java.util.Map;

public class VitalInventoryBuilder implements InventoryHolder {
    @Getter(onMethod = @__(@NotNull))
    private final Map<Integer, ItemStack> slotItemStackMap = new HashMap<>();

    @Getter(onMethod = @__(@NotNull))
    private final Map<Integer, VitalInventoryClickEvent> slotClickEventMap = new HashMap<>();

    @Getter(onMethod = @__(@NotNull))
    private final Map<ItemStack, VitalInventoryClickEvent> itemStackClickEventMap = new HashMap<>();

    private int size = 9;

    private InventoryType inventoryType;

    private String name = "VitalInventory";

    @Getter(onMethod = @__(@Nullable))
    @Setter(onParam = @__(@Nullable))
    private Inventory previousInventory;

    @Getter
    private Inventory inventory;

    @Getter(onMethod = @__(@NotNull))
    private VitalInventoryOpenEvent vitalInventoryOpenEvent = (player) -> {
    };

    @Getter(onMethod = @__(@NotNull))
    private VitalInventoryClickEvent vitalInventoryClickEvent = (player, itemStack) -> VitalInventoryClickEvent.Action.DO_NOTHING;

    @Getter(onMethod = @__(@NotNull))
    private VitalInventoryCloseEvent vitalInventoryCloseEvent = player -> {
    };

    public VitalInventoryBuilder size(@Range(from = 9, to = 54) int size) {
        this.size = size;

        return this;
    }

    public VitalInventoryBuilder inventoryType(@NotNull InventoryType inventoryType) {
        this.inventoryType = inventoryType;

        return this;
    }

    public VitalInventoryBuilder name(@NotNull String name) {
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

        for(Map.Entry<Integer, ItemStack> entry : slotItemStackMap.entrySet()) {
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
    public VitalInventoryBuilder onOpen(@NotNull VitalInventoryOpenEvent vitalInventoryOpenEvent) {
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
    public VitalInventoryBuilder setItemStack(int slot, @NotNull ItemStack itemStack, @NotNull VitalInventoryClickEvent vitalInventoryClickEvent) {
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
    public VitalInventoryBuilder setItemStack(int slot, @NotNull ItemStack itemStack) {
        slotItemStackMap.put(slot, itemStack);

        return this;
    }

    public VitalInventoryBuilder onClick(@NotNull VitalInventoryClickEvent vitalInventoryClickEvent) {
        this.vitalInventoryClickEvent = vitalInventoryClickEvent;

        return this;
    }

    /**
     * Callback to when this Inventory closes.
     *
     * @param vitalInventoryCloseEvent The close event.
     * @return This builder instance.
     */
    public VitalInventoryBuilder onClose(@NotNull VitalInventoryCloseEvent vitalInventoryCloseEvent) {
        this.vitalInventoryCloseEvent = vitalInventoryCloseEvent;

        return this;
    }
}
