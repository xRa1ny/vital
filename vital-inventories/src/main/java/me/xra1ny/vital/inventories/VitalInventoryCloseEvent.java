package me.xra1ny.vital.inventories;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public interface VitalInventoryCloseEvent {
    void onVitalInventoryClose(@NotNull Player player);
}
