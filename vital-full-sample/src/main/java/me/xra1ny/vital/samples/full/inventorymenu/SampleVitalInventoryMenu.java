package me.xra1ny.vital.samples.full.inventorymenu;

import me.xra1ny.vital.inventories.VitalInventoryMenu;
import me.xra1ny.vital.inventories.VitalInventoryMenuInfo;
import org.jetbrains.annotations.Nullable;

/**
 * Here we define meta information for our InventoryMenu.
 */
@VitalInventoryMenuInfo(title = "SampleVitalInventoryMenu")
public final class SampleVitalInventoryMenu extends VitalInventoryMenu {
    public SampleVitalInventoryMenu(@Nullable VitalInventoryMenu previousMenu) {
        super(previousMenu);
    }
}
