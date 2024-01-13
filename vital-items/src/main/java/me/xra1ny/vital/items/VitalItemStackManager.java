package me.xra1ny.vital.items;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Manages custom item stacks and their associated cooldowns.
 * This class is responsible for managing a collection of custom item stacks and coordinating their usage cooldowns.
 *
 * @author xRa1ny
 */
@Log
@VitalDI
public final class VitalItemStackManager extends VitalComponentListManager<VitalItemStack> {
    private static VitalItemStackManager instance;

    @Override
    public void onRegistered() {
        instance = this;
        log.info("VitalItemStackManager online!");
    }

    @Override
    public @NotNull Class<VitalItemStack> managedType() {
        return VitalItemStack.class;
    }

    /**
     * Attempts to set the specified {@link VitalItemStack} by its class.
     *
     * @param inventory The {@link Inventory} to add the item to.
     * @param itemStackClass The class of the {@link VitalItemStack} (must be registered).
     */
    public static void addItem(@NonNull Inventory inventory, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        final Optional<? extends VitalItemStack> optionalVitalItemStack = instance.getVitalComponent(itemStackClass);

        optionalVitalItemStack.ifPresent(inventory::addItem);
    }

    /**
     * Attempts to set the specified {@link VitalItemStack} by its class.
     *
     * @param player The {@link Player} to add the item to.
     * @param itemStackClass The class of the {@link VitalItemStack} (must be registered).
     */
    public static void addItem(@NonNull Player player, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        addItem(player.getInventory(), itemStackClass);
    }

    /**
     * Attempts to set the {@link VitalItemStack} by its given class to the given slot in the specified {@link Inventory}.
     *
     * @param inventory The {@link Inventory}.
     * @param slot The slot.
     * @param itemStackClass The class of te {@link VitalItemStack} (must be registered).
     */
    public static void setItem(@NonNull Inventory inventory, int slot, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        final Optional<? extends VitalItemStack> optionalVitalItemStack = instance.getVitalComponent(itemStackClass);

        optionalVitalItemStack.ifPresent(vitalItemStack -> inventory.setItem(slot, vitalItemStack));
    }

    /**
     * Attempts to set the {@link VitalItemStack} by its given class to the given slot in the specified {@link Player}'s {@link Inventory}.
     *
     * @param player The {@link Player}.
     * @param slot The slot.
     * @param itemStackClass The class of te {@link VitalItemStack} (must be registered).
     */
    public static void setItem(@NonNull Player player, int slot, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        setItem(player.getInventory(), slot, itemStackClass);
    }
}

