package me.xra1ny.vital.samples.full.item;

import me.xra1ny.vital.holograms.VitalHologram;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.items.VitalItemStack;
import me.xra1ny.vital.items.VitalItemStackInfo;
import me.xra1ny.vital.samples.full.config.SampleVitalConfig;
import me.xra1ny.vital.samples.full.inventorymenu.SampleVitalInventoryMenu;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we define the meta information of our ItemStack.
 */
@VitalItemStackInfo(name = "SampleVitalItemStack", type = Material.STICK, cooldown = 1000/* measured in ms */)
public final class SampleVitalItemStack extends VitalItemStack {
    private final VitalHologramManager vitalHologramManager;
    private final SampleVitalConfig sampleVitalConfig;

    public SampleVitalItemStack(@NotNull VitalHologramManager vitalHologramManager, @NotNull SampleVitalConfig sampleVitalConfig) {
        this.vitalHologramManager = vitalHologramManager;
        this.sampleVitalConfig = sampleVitalConfig;

    }

    @Override
    public void onLeftClick(@NotNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final VitalHologram vitalHologram = new VitalHologram(player.getName(), player.getLocation(), Material.STICK, "line1", "line2", "line3");

        vitalHologramManager.registerVitalComponent(vitalHologram);

        if (sampleVitalConfig.getSampleVitalHologramConfigField() == null) {
            player.sendMessage("hologram not yet saved to config! saving...");
            sampleVitalConfig.setSampleVitalHologramConfigField(vitalHologram);
        }

        List<VitalHologram> vitalHologramList = sampleVitalConfig.getSampleVitalHologramListConfigField();

        if(vitalHologramList == null) {
            vitalHologramList = new ArrayList<>();
        }

        vitalHologramList.add(vitalHologram);
        sampleVitalConfig.setSampleVitalHologramListConfigField(vitalHologramList);

        player.sendMessage("added new hologram to config saved list!");

        sampleVitalConfig.save();
        player.sendMessage("config saved!");

        player.sendMessage("leftclicked");
    }

    @Override
    public void onRightClick(@NotNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final SampleVitalInventoryMenu sampleVitalInventoryMenu = new SampleVitalInventoryMenu(null);

        sampleVitalInventoryMenu.open(player);

        player.sendMessage("rightclicked");
    }

    @Override
    public void onCooldown(@NotNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        player.sendMessage("cooling down...");
        player.sendMessage("remaining: " + getCurrentCooldown());
    }
}
