package me.xra1ny.vital.items;

import lombok.Getter;
import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.vital.core.VitalComponentListManager;
import org.bukkit.plugin.java.JavaPlugin;

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
    @Getter
    @NonNull
    private final VitalItemStackCooldownHandler cooldownHandler;

    /**
     * Constructs a new instance of the VitalItemStackManagement class.
     *
     * @param javaPlugin The JavaPlugin instance responsible for managing the plugin.
     */
    public VitalItemStackManager(@NonNull JavaPlugin javaPlugin) {
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
    public void onVitalComponentRegistered(@NonNull VitalItemStack vitalItemStack) {

    }

    @Override
    public void onVitalComponentUnregistered(@NonNull VitalItemStack vitalItemStack) {

    }

    @Override
    public Class<VitalItemStack> managedType() {
        return VitalItemStack.class;
    }
}

