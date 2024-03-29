package me.xra1ny.vital.items;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public class VitalHeadItemStackBuilder extends VitalItemStackBuilder {
    private OfflinePlayer owningPlayer;

    @Override
    public VitalHeadItemStackBuilder name(@Nullable String name) {
        super.name(name);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder type(Material type) {
        super.type(type);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder lore(@NonNull List<String> lore) {
        super.lore(lore);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder lore(@NonNull String lore) {
        super.lore(lore);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder itemFlags(@NonNull List<ItemFlag> itemFlagList) {
        super.itemFlags(itemFlagList);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder itemFlag(@NonNull ItemFlag itemFlag) {
        super.itemFlag(itemFlag);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder enchantments(@NonNull Map<Enchantment, Integer> enchantmentLevelMap) {
        super.enchantments(enchantmentLevelMap);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder enchantment(@NonNull Enchantment enchantment, int enchantmentLevel) {
        super.enchantment(enchantment, enchantmentLevel);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder amount(int amount) {
        super.amount(amount);

        return this;
    }

    @Override
    public VitalHeadItemStackBuilder unbreakable(boolean unbreakable) {
        super.unbreakable(unbreakable);

        return this;
    }


    @Override
    public <Z> VitalHeadItemStackBuilder namespacedKey(@NonNull String key, @NonNull PersistentDataType<?, Z> persistentDataType, @NonNull Z value) {
        super.namespacedKey(key, persistentDataType, value);

        return this;
    }

    @Override
    public <Z> VitalHeadItemStackBuilder namespacedKey(@NonNull NamespacedKey namespacedKey, @NonNull PersistentDataType<?, Z> persistentDataType, @NonNull Z value) {
        super.namespacedKey(namespacedKey, persistentDataType, value);

        return this;
    }

    @Override
    public @NonNull <Z> ItemStack build() {
        final ItemStack itemStack = super.build();
        final SkullMeta skullMeta = (SkullMeta) itemStack.getItemMeta();

        skullMeta.setOwningPlayer(owningPlayer);
        itemStack.setItemMeta(skullMeta);

        return itemStack;
    }

    public VitalHeadItemStackBuilder owningPlayer(@Nullable OfflinePlayer owningPlayer) {
        this.owningPlayer = owningPlayer;

        return this;
    }
}