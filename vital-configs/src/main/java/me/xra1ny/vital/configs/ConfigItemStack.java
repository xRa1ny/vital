package me.xra1ny.vital.configs;

import lombok.NonNull;
import me.xra1ny.essentia.configs.annotation.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Wrapper class to store itemstack data in a config file.
 *
 * @author xRa1ny
 */
public class ConfigItemStack {
    @Property(Material.class)
    public Material type;

    @NonNull
    public static ConfigItemStack of(@NonNull ItemStack itemStack) {
        final ConfigItemStack configItemStack = new ConfigItemStack();

        configItemStack.type = itemStack.getType();

        return configItemStack;
    }

    @NonNull
    public ItemStack toItemStack() {
        return new ItemStack(type);
    }
}
