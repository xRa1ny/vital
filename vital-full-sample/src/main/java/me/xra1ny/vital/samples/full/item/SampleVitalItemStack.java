package me.xra1ny.vital.samples.full.item;

import me.xra1ny.vital.holograms.VitalHologram;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStack;
import me.xra1ny.vital.items.VitalItemStackInfo;
import me.xra1ny.vital.samples.full.inventorymenu.SampleVitalInventoryMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Here we define the meta information of our ItemStack.
 */
@VitalItemStackInfo(name = "SampleVitalItemStack", type = Material.STICK, cooldown = 1000/* measured in ms */)
public final class SampleVitalItemStack extends VitalItemStack {
    private final VitalHologramManager vitalHologramManager;

    public SampleVitalItemStack(@NotNull VitalHologramManager vitalHologramManager) {
        this.vitalHologramManager = vitalHologramManager;
    }

    @Override
    public Class<VitalItemStackInfo> requiredAnnotationType() {
        return VitalItemStackInfo.class;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    @Override
    public void onLeftClick(@NotNull PlayerInteractEvent e, @NotNull Player player) {
        final VitalHologram vitalHologram = new VitalHologram(player.getName(), player.getLocation(), Material.STICK, "line1", "line2", "line3");

        vitalHologramManager.registerVitalComponent(vitalHologram);

        player.sendMessage("leftclicked");
    }

    @Override
    public void onRightClick(@NotNull PlayerInteractEvent e, @NotNull Player player) {
        final SampleVitalInventoryMenu sampleVitalInventoryMenu = new SampleVitalInventoryMenu(null);

        sampleVitalInventoryMenu.open(player);

        player.sendMessage("rightclicked");
    }

    @Override
    public void onCooldown(@NotNull PlayerInteractEvent e, @NotNull Player player) {
        player.sendMessage("cooling down...");
        player.sendMessage("remaining: " + getCurrentCooldown());
    }

    @Override
    public void onCooldownExpire(@NotNull Player player) {

    }
}
