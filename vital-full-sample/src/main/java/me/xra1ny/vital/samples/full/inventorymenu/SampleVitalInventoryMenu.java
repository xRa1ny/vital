package me.xra1ny.vital.samples.full.inventorymenu;

import me.xra1ny.vital.inventories.VitalInventoryMenu;
import me.xra1ny.vital.inventories.VitalInventoryMenuInfo;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Here we define meta information for our InventoryMenu.
 */
@VitalInventoryMenuInfo(title = "SampleVitalInventoryMenu")
public final class SampleVitalInventoryMenu extends VitalInventoryMenu {
    public SampleVitalInventoryMenu(@Nullable VitalInventoryMenu previousMenu) {
        super(previousMenu);
    }

    @Override
    public Class<VitalInventoryMenuInfo> requiredAnnotationType() {
        return VitalInventoryMenuInfo.class;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void setItems(@NotNull Player player) {

    }

    @Override
    public void onOpen(@NotNull InventoryOpenEvent e) {

    }

    @Override
    public void onClose(@NotNull InventoryCloseEvent e) {

    }

    @Override
    public void onClick(@NotNull InventoryClickEvent e) {

    }
}
