package me.xra1ny.vital.items;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

/**
 * A builder class for creating ItemStack objects with custom attributes.
 * This class provides a fluent builder pattern for creating items with names, lore, enchantments, and more.
 *
 * @author xRa1ny
 */
@Builder(toBuilder = true)
@Getter(onMethod = @__(@NotNull))
public final class ItemBuilder {
	private String name;

	@SuppressWarnings("CanBeFinal")
	@Builder.Default
	private Material type = Material.COBBLESTONE;

	@Singular("lore")
	private List<String> lore;

	@Singular("itemflag")
	private List<ItemFlag> itemFlags;

	@Singular("enchantment")
	private Map<Enchantment, Integer> enchantments;

	@SuppressWarnings("CanBeFinal")
	@Builder.Default
	private int amount = 1;

	/**
	 * Converts the builder's configuration into an ItemStack.
	 *
	 * @return The constructed ItemStack.
	 */
	@SuppressWarnings("deprecation")
	@NotNull
	public ItemStack toItemStack() {
		// Create ItemStack and ItemMeta
		final ItemStack item = new ItemStack((type != null ? type : Material.COBBLESTONE), amount);

		if (this.type != Material.AIR) {
			final ItemMeta meta = item.getItemMeta();
			meta.getPersistentDataContainer().set(NamespacedKeys.ITEM_UUID, PersistentDataType.STRING, UUID.randomUUID().toString());

			if (this.name != null) {
				if (this.name.isBlank()) {
					meta.setDisplayName(ChatColor.RESET.toString());
				} else {
					meta.setDisplayName(this.name);
				}
			}

			// Set Enchantments if set
			if (!enchantments.isEmpty()) {
				for (Entry<Enchantment, Integer> entrySet : enchantments.entrySet()) {
					meta.addEnchant(entrySet.getKey(), entrySet.getValue(), true);
				}
			}

			// Set ItemFlags if set, else use all
			if (!itemFlags.isEmpty()) {
				for (ItemFlag itemFlag : itemFlags) {
					meta.addItemFlags(itemFlag);
				}
			} else {
				for (ItemFlag itemFlag : ItemFlag.values()) {
					meta.addItemFlags(itemFlag);
				}
			}

			// Set Lore if set
			if (!lore.isEmpty()) {
				meta.setLore(lore);
			}

			// Set created ItemStack's ItemMeta
			item.setItemMeta(meta);
		}

		return item;
	}
}

