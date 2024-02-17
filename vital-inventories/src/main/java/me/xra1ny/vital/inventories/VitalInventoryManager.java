package me.xra1ny.vital.inventories;

import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.essentia.inject.DIFactory;
import me.xra1ny.essentia.inject.annotation.Component;
import org.bukkit.entity.Player;

import java.util.Optional;

@Log
@Component
public class VitalInventoryManager {
    public void afterInit() {
        log.info("VitalInventoryManager online!");
    }

    /**
     * Opens a registered {@link VitalInventory} for the given {@link Player}.
     *
     * @param player The {@link Player} to open the given {@link VitalInventory} for.
     * @param vitalInventoryClass The class of the {@link VitalInventory} to open for the given {@link Player}.
     */
    @SneakyThrows // TODO
    public static void openVitalInventory(@NonNull Player player, @NonNull Class<? extends VitalInventory> vitalInventoryClass) {
        final Optional<? extends VitalInventory> optionalVitalInventory = Optional.ofNullable(DIFactory.getInstance(vitalInventoryClass));

        if(optionalVitalInventory.isEmpty()) {
            return;
        }

        final VitalInventory vitalInventory = optionalVitalInventory.get();

        player.openInventory(vitalInventory.getInventory());
    }
}
