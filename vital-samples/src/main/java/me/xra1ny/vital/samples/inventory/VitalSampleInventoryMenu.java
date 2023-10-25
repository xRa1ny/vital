package me.xra1ny.vital.samples.inventory;

import me.xra1ny.vital.inventories.VitalInventoryMenu;
import me.xra1ny.vital.inventories.VitalInventoryMenuInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("DefaultAnnotationParam")
@VitalInventoryMenuInfo(title = "VitalSampleInventoryMenu", background = Material.AIR, size = 9)
public class VitalSampleInventoryMenu extends VitalInventoryMenu {
    public VitalSampleInventoryMenu(@Nullable VitalInventoryMenu previousMenu) {
        super(previousMenu);
    }

    @Override
    public Class<VitalInventoryMenuInfo> requiredAnnotationType() {
        return VitalInventoryMenuInfo.class;
    }

    @Override
    public void onVitalComponentRegistered() {

    }

    @Override
    public void onVitalComponentUnregistered() {

    }

    @Override
    public void setItems(@NotNull Player player) {
        // Here we set any items
    }

    @Override
    public void onOpen(@NotNull InventoryOpenEvent e) {

    }

    @Override
    public void onClose(@NotNull InventoryCloseEvent e) {

    }

    @Override
    public void onClick(@NotNull InventoryClickEvent e) {
        // Here we handle any click logic
    }
}
