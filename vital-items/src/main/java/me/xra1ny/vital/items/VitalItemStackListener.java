package me.xra1ny.vital.items;

import me.xra1ny.vital.core.VitalListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Listens for player interactions with custom item stacks and handles their interactions accordingly.
 * This class is responsible for detecting player interactions with custom items and invoking their associated actions.
 *
 * @author xRa1ny
 */
public final class VitalItemStackListener extends VitalListener {
    /**
     * The management system for custom item stacks.
     */
    private final VitalItemStackManager vitalItemStackManager;

    /**
     * Constructs a new instance of the VitalItemStackListener.
     *
     * @param javaPlugin             The JavaPlugin instance responsible for managing the plugin.
     * @param vitalItemStackManager The management system for custom item stacks.
     */
    public VitalItemStackListener(@NotNull JavaPlugin javaPlugin, @NotNull VitalItemStackManager vitalItemStackManager) {
        super(javaPlugin);
        this.vitalItemStackManager = vitalItemStackManager;
    }

    /**
     * Listens for player interactions with items and handles them.
     *
     * @param e The PlayerInteractEvent triggered by the player's interaction.
     */
    @EventHandler
    public void onPlayerInteract(@NotNull PlayerInteractEvent e) {
        if (e.getItem() == null) {
            // Ignore interactions with empty hands (no item in hand).
            return;
        }

        // Find the custom item stack that matches the player's interaction item.
        vitalItemStackManager.getVitalComponentList()
                .stream()
                .filter(i -> i.equals(e.getItem()))
                .findFirst()
                .ifPresent(itemStack -> itemStack.handleInteraction(e, e.getPlayer()));
    }
}

