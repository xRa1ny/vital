package me.xra1ny.vital.items;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.essentia.inject.DIFactory;
import me.xra1ny.essentia.inject.annotation.AfterInit;
import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalCore;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Manages custom item stacks and their associated cooldowns.
 * This class is responsible for managing a collection of custom item stacks and coordinating their usage cooldowns.
 *
 * @author xRa1ny
 */
@Log
@Component
public final class VitalItemStackManager {
    private final VitalCore<?> vitalCore;

    public VitalItemStackManager(VitalCore<?> vitalCore) {
        this.vitalCore = vitalCore;
    }

    @AfterInit
    public void afterInit() {
        log.info("VitalItemStackManager online!");
    }

    public List<VitalItemStack> getComponentList() {
        return vitalCore.getComponentList(VitalItemStack.class);
    }

    /**
     * Attempts to set the specified {@link VitalItemStack} by its class.
     *
     * @param inventory      The {@link Inventory} to add the item to.
     * @param itemStackClass The class of the {@link VitalItemStack} (must be registered).
     * @return The {@link Map} containing all items that didn't fit.
     */
    @SneakyThrows // TODO
    @NonNull
    public static Map<Integer, ItemStack> addItem(@NonNull Inventory inventory, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        final Optional<? extends VitalItemStack> optionalVitalItemStack = Optional.ofNullable(DIFactory.getInstance(itemStackClass));

        if(optionalVitalItemStack.isPresent()) {
            return inventory.addItem(optionalVitalItemStack.get());
        }

        return Map.of();
    }

    /**
     * Attempts to set the specified {@link VitalItemStack} by its class.
     *
     * @param player         The {@link Player} to add the item to.
     * @param itemStackClass The class of the {@link VitalItemStack} (must be registered).
     * @return The {@link Map} containing all items that didn't fit.
     */
    @NonNull
    public static Map<Integer, ItemStack> addItem(@NonNull Player player, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        return addItem(player.getInventory(), itemStackClass);
    }

    /**
     * Attempts to set the {@link VitalItemStack} by its given class to the given slot in the specified {@link Inventory}.
     *
     * @param inventory The {@link Inventory}.
     * @param slot The slot.
     * @param itemStackClass The class of te {@link VitalItemStack} (must be registered).
     */
    @SneakyThrows // TODO
    public static void setItem(@NonNull Inventory inventory, int slot, @NonNull Class<? extends VitalItemStack> itemStackClass) {
        final Optional<? extends VitalItemStack> optionalVitalItemStack = Optional.ofNullable(DIFactory.getInstance(itemStackClass));

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

