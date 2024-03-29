package me.xra1ny.vital.items;

import lombok.Getter;
import lombok.NonNull;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.Map.Entry;

/**
 * A builder class for creating ItemStack objects with custom attributes.
 * This class provides a fluent builder pattern for creating items with names, lore, enchantments, and more.
 *
 * @author xRa1ny
 */
public class VitalItemStackBuilder {
    @Getter
    @NonNull
    private final List<String> lore = new ArrayList<>();
    @Getter
    @NonNull
    private final List<ItemFlag> itemFlagList = new ArrayList<>();
    @Getter
    @NonNull
    private final Map<Enchantment, Integer> enchantmentLevelMap = new HashMap<>();
    @Getter
    @NonNull
    private final Map<NamespacedKey, Map.Entry<PersistentDataType<?, ?>, ?>> namespacedKeyMap = new HashMap<>();
    @Getter
    private String name;
    @Getter
    @NonNull
    private Material type = Material.COBBLESTONE;
    @Getter
    private int amount = 1;
    @Getter
    private boolean unbreakable;

    /**
     * Define the name for this {@link ItemStack}.
     *
     * @param name The name.
     * @return This builder instance.
     */
    public VitalItemStackBuilder name(@Nullable String name) {
        this.name = name;

        return this;
    }

    /**
     * Define the type for this {@link ItemStack}.
     *
     * @param type The {@link Material}.
     * @return This builder instance.
     */
    public VitalItemStackBuilder type(Material type) {
        this.type = type;

        return this;
    }

    /**
     * Define the lore for this {@link ItemStack}.
     *
     * @param lore The lore {@link List}.
     * @return This builder instance.
     */
    public VitalItemStackBuilder lore(@NonNull List<String> lore) {
        this.lore.addAll(lore);

        return this;
    }

    /**
     * Add ONE lore LINE for this {@link ItemStack}.
     *
     * @param lore The lore line to add.
     * @return This builder instance.
     */
    public VitalItemStackBuilder lore(@NonNull String lore) {
        this.lore.add(lore);

        return this;
    }

    /**
     * Define the item flags for this {@link ItemStack}.
     *
     * @param itemFlagList The {@link List} of all {@link ItemFlag} instances..
     * @return This builder instance.
     */
    public VitalItemStackBuilder itemFlags(@NonNull List<ItemFlag> itemFlagList) {
        this.itemFlagList.addAll(itemFlagList);

        return this;
    }

    /**
     * Add ONE {@link ItemFlag} for this {@link ItemStack}.
     *
     * @param itemFlag The {@link ItemFlag} to add.
     * @return This builder instance.
     */
    public VitalItemStackBuilder itemFlag(@NonNull ItemFlag itemFlag) {
        itemFlagList.add(itemFlag);

        return this;
    }

    /**
     * Define the enchantments for this {@link ItemStack}.
     *
     * @param enchantmentLevelMap The {@link Map} of all {@link Enchantment} instances and their level.
     * @return This builder instance.
     */
    public VitalItemStackBuilder enchantments(@NonNull Map<Enchantment, Integer> enchantmentLevelMap) {
        this.enchantmentLevelMap.putAll(enchantmentLevelMap);

        return this;
    }

    /**
     * Add one {@link Enchantment} and its level for this {@link ItemStack}.
     *
     * @param enchantment      The {@link Enchantment}.
     * @param enchantmentLevel The enchantment level.
     * @return This builder instance.
     */
    public VitalItemStackBuilder enchantment(@NonNull Enchantment enchantment, int enchantmentLevel) {
        enchantmentLevelMap.put(enchantment, enchantmentLevel);

        return this;
    }

    /**
     * Define the amount for this {@link ItemStack}.
     *
     * @param amount The amount.
     * @return This builder instance.
     */
    public VitalItemStackBuilder amount(int amount) {
        this.amount = amount;

        return this;
    }

    /**
     * Define if this {@link ItemStack} is unbreakable or not.
     *
     * @param unbreakable True if the {@link ItemStack} should be unbreakable; false otherwise.
     * @return This builder instance.
     */
    public VitalItemStackBuilder unbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;

        return this;
    }

    /**
     * Add one {@link NamespacedKey} for this {@link ItemStack}.
     *
     * @param key                The key.
     * @param persistentDataType The {@link PersistentDataType} of the value to register.
     * @param value              The value the {@link NamespacedKey} instance should hold.
     * @param <Z>                The type of {@link PersistentDataType} the value should be composed of.
     * @return This builder instance.
     */
    public <Z> VitalItemStackBuilder namespacedKey(@NonNull String key, @NonNull PersistentDataType<?, Z> persistentDataType, @NonNull Z value) {
        final NamespacedKey namespacedKey = new NamespacedKey("vital", key);

        namespacedKeyMap.put(namespacedKey, Map.entry(persistentDataType, value));

        return this;
    }

    /**
     * Add one {@link NamespacedKey} for this {@link ItemStack}.
     *
     * @param namespacedKey      The {@link NamespacedKey}.
     * @param persistentDataType The {@link PersistentDataType} of the value to register.
     * @param value              The value the {@link NamespacedKey} instance should hold.
     * @param <Z>                The type of {@link PersistentDataType} the value should be composed of.
     * @return This builder instance.
     */
    public <Z> VitalItemStackBuilder namespacedKey(@NonNull NamespacedKey namespacedKey, @NonNull PersistentDataType<?, Z> persistentDataType, @NonNull Z value) {
        namespacedKeyMap.put(namespacedKey, Map.entry(persistentDataType, value));

        return this;
    }

    /**
     * Converts the builder's configuration into an ItemStack.
     *
     * @param <Z> Placeholder for {@link PersistentDataType} IGNORE.
     * @return The constructed ItemStack.
     */
    @NonNull
    public <Z> ItemStack build() {
        // Create ItemStack and ItemMeta
        final ItemStack item = new ItemStack(type, amount);

        if (type != Material.AIR) {
            final ItemMeta meta = item.getItemMeta();
            final PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

            persistentDataContainer.set(NamespacedKeys.ITEM_UUID, PersistentDataType.STRING, UUID.randomUUID().toString());

            if (name != null) {
                if (!name.isBlank()) {
                    meta.displayName(MiniMessage.miniMessage().deserialize("<reset><white><name>",
                                    Placeholder.parsed("name", name))
                            .decoration(TextDecoration.ITALIC, false));
                }
            }

            // Set Enchantments if set
            if (!enchantmentLevelMap.isEmpty()) {
                for (Entry<Enchantment, Integer> entrySet : enchantmentLevelMap.entrySet()) {
                    meta.addEnchant(entrySet.getKey(), entrySet.getValue(), true);
                }
            }

            // Set ItemFlags if set, else use all
            if (!itemFlagList.isEmpty()) {
                for (ItemFlag itemFlag : itemFlagList) {
                    meta.addItemFlags(itemFlag);
                }
            } else {
                for (ItemFlag itemFlag : ItemFlag.values()) {
                    meta.addItemFlags(itemFlag);
                }
            }

            // Set Lore if set
            if (!lore.isEmpty()) {
                meta.lore(lore.stream()
                        .map(l -> MiniMessage.miniMessage().deserialize(l)
                                .decoration(TextDecoration.ITALIC, false))
                        .toList());
            }

            meta.setUnbreakable(unbreakable);

            if (!namespacedKeyMap.isEmpty()) {
                for (Map.Entry<NamespacedKey, Map.Entry<PersistentDataType<?, ?>, ?>> entry : namespacedKeyMap.entrySet()) {
                    final NamespacedKey namespacedKey = entry.getKey();
                    final PersistentDataType<?, Z> persistentDataType = (PersistentDataType<?, Z>) entry.getValue().getKey();
                    final Z value = (Z) entry.getValue().getValue();

                    if (!persistentDataType.getComplexType().equals(value.getClass())) {
                        continue;
                    }

                    persistentDataContainer.set(namespacedKey, persistentDataType, value);
                }
            }

            // Set created ItemStack's ItemMeta
            item.setItemMeta(meta);
        }

        return item;
    }
}