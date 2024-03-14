package me.xra1ny.vital.inventories;

import lombok.NonNull;
import lombok.extern.java.Log;
import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalCore;
import org.bukkit.entity.Player;

@Log
@Component
public class VitalInventoryManager implements VitalComponent {
    private static VitalInventoryManager instance;

    private final VitalCore<?> vitalCore;

    public VitalInventoryManager(VitalCore<?> vitalCore) {
        this.vitalCore = vitalCore;
    }

    @Override
    public void onRegistered() {
        instance = this;
    }

    @Override
    public void onUnregistered() {

    }

    /**
     * Opens a registered {@link VitalInventory} for the given {@link Player}.
     *
     * @param player              The {@link Player} to open the given {@link VitalInventory} for.
     * @param vitalInventoryClass The class of the {@link VitalInventory} to open for the given {@link Player}.
     */
    public static void openVitalInventory(@NonNull Player player, @NonNull Class<? extends VitalInventory> vitalInventoryClass) {
        final VitalInventory vitalInventory = instance.vitalCore.getComponentByType(vitalInventoryClass)
                .orElseThrow(() -> new RuntimeException("attempted opening unregistered inventory %s"
                        .formatted(vitalInventoryClass.getSimpleName())));

        player.openInventory(vitalInventory.getInventory());
    }
}
