package me.xra1ny.vital.configs;

import com.google.j2objc.annotations.Property;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Wrapper class to store itemstack data in a config file.
 *
 * @author xRa1ny
 */
@Data
public class ConfigItemStack {
    @Property("type")
    private Material type;

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
