package me.xra1ny.vital.items;

import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.core.AnnotatedVitalComponent;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * Used to create ItemStacks that can be interacted with.
 * This class provides a foundation for creating custom items with specific behaviors,
 * cooldowns, and interactions.
 *
 * @author xRa1ny
 */
public abstract class VitalItemStack extends ItemStack implements AnnotatedVitalComponent<VitalItemStackInfo> {
    /**
     * The current Cooldown of this VitalItemStack.
     */
    @Getter
    @Setter
    private int currentCooldown = 0;

    /**
     * The Cooldown of this VitalItemStack.
     */
    @Getter
    private int cooldown = 0;

    @Getter
    private final boolean localised;

    /**
     * Creates a new VitalItemStack based on annotation-defined properties.
     * @see VitalItemStackInfo
     */
    public VitalItemStack() {
        @Nullable
        final VitalItemStackInfo info = getRequiredAnnotation();
        final ItemStack itemStack = ItemBuilder.builder()
                .type(info.type())
                .name(info.name())
                .amount(info.amount())
                .lore(List.of(info.lore()))
                .itemFlags(List.of(info.itemFlags()))
                .build()
                .toItemStack();
        final ItemMeta meta = itemStack.getItemMeta();

        if(info.enchanted()) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }

        setType(itemStack.getType());
        setAmount(itemStack.getAmount());
        setItemMeta(meta);
        this.cooldown = info.cooldown();
        this.localised = info.localised();
    }

    /**
     * Creates a new VitalItemStack based on an existing ItemStack.
     * @param itemStack The base ItemStack.
     * @param enchanted Whether to add enchantments.
     * @param localised Whether to consider localization in comparison.
     */
    public VitalItemStack(@NotNull ItemStack itemStack, boolean enchanted, boolean localised) {
        final ItemMeta meta = itemStack.getItemMeta();

        if(enchanted) {
            meta.addEnchant(Enchantment.LUCK, 1, true);
        }

        setType(itemStack.getType());
        setAmount(itemStack.getAmount());
        setItemMeta(meta);
        this.localised = localised;
    }

    /**
     * Called when this item has been left-clicked.
     * @param e The player interact event.
     * @param player The player.
     */
    public abstract void onLeftClick(@NotNull PlayerInteractEvent e, @NotNull Player player);

    /**
     * Called when this item has been right-clicked.
     * @param e The player interact event.
     * @param player The player.
     */
    public abstract void onRightClick(@NotNull PlayerInteractEvent e, @NotNull Player player);

    /**
     * Called when this item has been left or right clicked, but the cooldown has not yet expired.
     * @param e The player interact event.
     * @param player The player.
     */
    public abstract void onCooldown(@NotNull PlayerInteractEvent e, @NotNull Player player);

    /**
     * Called when the cooldown of this item expires for the specified player.
     * @param player The player.
     */
    public abstract void onCooldownExpire(@NotNull Player player);

    /**
     * Handles player interaction with this item, considering cooldowns.
     * @param e The player interact event.
     * @param player The player.
     */
    public final void handleInteraction(@NotNull PlayerInteractEvent e, @NotNull Player player) {
        if(currentCooldown >= 1) {
            onCooldown(e, player);
            return;
        }

        final Action action = e.getAction();

        if(action.isLeftClick()) {
            onLeftClick(e, player);
        } else {
            onRightClick(e, player);
        }

        currentCooldown = cooldown;
    }

    @Override
    public final String toString() {
        return super.toString().replace(getType() + " x " + getAmount(), getType() + " x 1");
    }

    @Override
    public final boolean equals(Object obj) {
        if(!(obj instanceof ItemStack item)) {
            return false;
        }

        if(!item.getItemMeta().getPersistentDataContainer().has(NamespacedKeys.ITEM_UUID, PersistentDataType.STRING)) {
            String toString = toString();

            if(this.localised) {
                toString = toString.replace(getItemMeta().getDisplayName(), "");
            }

            return toString.equals(item.toString().replace(item.getType() + " x " + item.getAmount(), item.getType() + " x 1"));
        }

        final UUID uuid = UUID.fromString(getItemMeta().getPersistentDataContainer().get(NamespacedKeys.ITEM_UUID, PersistentDataType.STRING));
        final UUID otherUuid = UUID.fromString(item.getItemMeta().getPersistentDataContainer().get(NamespacedKeys.ITEM_UUID, PersistentDataType.STRING));

        return uuid.equals(otherUuid);
    }

    /**
     * Checks if this item is enchanted.
     * @return true if the item is enchanted, false otherwise.
     */
    public final boolean isEnchanted() {
        return !getItemMeta().getEnchants().isEmpty();
    }
}

