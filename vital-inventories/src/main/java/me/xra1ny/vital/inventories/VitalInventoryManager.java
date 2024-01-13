package me.xra1ny.vital.inventories;

import lombok.NonNull;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@VitalDI
@VitalAutoRegistered
public class VitalInventoryManager extends VitalComponentListManager<VitalInventory> {
    private static VitalInventoryManager instance;

    @Override
    public void onRegistered() {
        instance = this;
    }

    @Override
    public @NotNull Class<VitalInventory> managedType() {
        return VitalInventory.class;
    }

    /**
     * Opens a registered {@link VitalInventory} for the given {@link Player}.
     *
     * @param player The {@link Player} to open the given {@link VitalInventory} for.
     * @param vitalInventoryClass The class of the {@link VitalInventory} to open for the given {@link Player}.
     */
    public static void openVitalInventory(@NonNull Player player, @NonNull Class<? extends VitalInventory> vitalInventoryClass) {
        final Optional<? extends VitalInventory> optionalVitalInventory = instance.getVitalComponent(vitalInventoryClass);

        if(optionalVitalInventory.isEmpty()) {
            return;
        }

        final VitalInventory vitalInventory = optionalVitalInventory.get();

        player.openInventory(vitalInventory.getInventory());
    }
}
