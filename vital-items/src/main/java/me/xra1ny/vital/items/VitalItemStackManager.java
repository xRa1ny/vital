package me.xra1ny.vital.items;

import lombok.Getter;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Manages custom item stacks and their associated cooldowns.
 * This class is responsible for managing a collection of custom item stacks and coordinating their usage cooldowns.
 *
 * @author xRa1ny
 */
@Log
public final class VitalItemStackManager extends VitalComponentListManager<VitalItemStack> {
    /**
     * The cooldown handler responsible for managing cooldowns of custom item stacks.
     */
    @Getter(onMethod = @__(@NotNull))
    private final VitalItemStackCooldownHandler cooldownHandler;

    /**
     * Constructs a new instance of the VitalItemStackManagement class.
     *
     * @param javaPlugin The JavaPlugin instance responsible for managing the plugin.
     */
    public VitalItemStackManager(@NotNull JavaPlugin javaPlugin) {
        // Create a cooldown handler and start it to manage item stack cooldowns.
        this.cooldownHandler = new VitalItemStackCooldownHandler(javaPlugin, this);
        this.cooldownHandler.start();
    }

    @Override
    public void onRegistered() {
        log.info("Successfully registered VitalItemStackManagement!");
    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onVitalComponentRegistered(@NotNull VitalItemStack vitalItemStack) {

    }

    @Override
    public void onVitalComponentUnregistered(@NotNull VitalItemStack vitalItemStack) {

    }
}

