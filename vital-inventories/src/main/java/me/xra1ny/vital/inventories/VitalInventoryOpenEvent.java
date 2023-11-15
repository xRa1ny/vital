package me.xra1ny.vital.inventories;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface VitalInventoryOpenEvent {
    void onVitalInventoryOpen(@NotNull Player player);
}
