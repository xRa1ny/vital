package me.xra1ny.vital.samples.full.item;

import lombok.NonNull;
import me.xra1ny.vital.holograms.VitalHologram;
import me.xra1ny.vital.holograms.VitalHologramManager;
import me.xra1ny.vital.inventories.VitalInventoryBuilder;
import me.xra1ny.vital.inventories.VitalInventoryClickEvent;
import me.xra1ny.vital.items.VitalItemStack;
import me.xra1ny.vital.items.VitalItemStackInfo;
import me.xra1ny.vital.samples.full.config.SampleVitalConfig;
import me.xra1ny.vital.samples.full.inventory.SampleVitalInventory;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we define the meta information of our ItemStack.
 */
@VitalItemStackInfo(name = "SampleVitalItemStack", type = Material.STICK, cooldown = 1000/* measured in ms */)
public final class SampleVitalItemStack extends VitalItemStack {
    private final VitalHologramManager vitalHologramManager;
    private final SampleVitalConfig sampleVitalConfig;

    public SampleVitalItemStack(@NonNull VitalHologramManager vitalHologramManager, @NonNull SampleVitalConfig sampleVitalConfig) {
        this.vitalHologramManager = vitalHologramManager;
        this.sampleVitalConfig = sampleVitalConfig;

    }

    @Override
    public void onLeftClick(@NonNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();
        final VitalHologram vitalHologram = new VitalHologram(player.getName(), player.getLocation(), Material.STICK, "line1", "line2", "line3");

        vitalHologramManager.registerVitalComponent(vitalHologram);

        if (sampleVitalConfig.sampleVitalHologramConfigField == null) {
            player.sendMessage("hologram not yet saved to config! saving...");
            sampleVitalConfig.sampleVitalHologramConfigField = vitalHologram;
        }

        List<VitalHologram> vitalHologramList = sampleVitalConfig.sampleVitalHologramListConfigField;

        if (vitalHologramList == null) {
            vitalHologramList = new ArrayList<>();
        }

        vitalHologramList.add(vitalHologram);
        sampleVitalConfig.sampleVitalHologramListConfigField = vitalHologramList;

        player.sendMessage("added new hologram to config saved list!");

        sampleVitalConfig.save();
        player.sendMessage("config saved!");

        player.sendMessage("leftclicked");
    }

    @Override
    public void onRightClick(@NonNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        // Here we create a new Instance of our SampleVitalInventory.
        final SampleVitalInventory sampleVitalInventory = new SampleVitalInventory();

        // And open it for the right-clicking player.
        player.openInventory(sampleVitalInventory.build());

        final VitalInventoryBuilder secondInventory = new VitalInventoryBuilder()
                .setItemStack(1, new ItemStack(Material.BARRIER), (player1, itemStack) -> {
                    return VitalInventoryClickEvent.Action.OPEN_PREVIOUS_INVENTORY;
                });

        // We can also use the "Builder" Implementation fo non-Component VitalInventories, like below...
        final VitalInventoryBuilder firstInventory = new VitalInventoryBuilder()
                .onOpen(player1 -> {

                })
                .onClose(player1 -> {

                })
                .setItemStack(0, new ItemStack(Material.STICK), (player1, itemStack) -> {
                    // Here we specify what will happen if we click the item we set.

                    // For example, we could say to open the second inventory specified above.
                    player1.openInventory(secondInventory.build());

                    return VitalInventoryClickEvent.Action.DO_NOTHING; // What to do afterward.
                })
                .onClick((player1, itemStack) -> {
                    // Here we specify any log that happens when we click in the inventory (not on a specific item).

                    return VitalInventoryClickEvent.Action.DO_NOTHING; // What to do afterward.
                });

        // Here we set the previous inventory of our `secondInventory` to the `firstInventory`,
        // so when we `return VitalInventoryClickEvent.Action.OPEN_PREVIOUS_INVENTORY` it opens the inventory
        // we specified here, automatically.

        // If you don't want to specify this here, you can also leave this out, and open the previous inventory manually.
        // ex. `onClick -> player.openInventory(previousInventory)`
        secondInventory.setPreviousInventory(firstInventory.build());

        // Here we open the inventory we created using our Builder.
        // TODO: uncomment the line below, to see it in action.
        // player.openInventory(firstInventory.build());

        player.sendMessage("rightclicked");
    }

    @Override
    public void onCooldown(@NonNull PlayerInteractEvent e) {
        final Player player = e.getPlayer();

        player.sendMessage("cooling down...");
        player.sendMessage("remaining: " + getCurrentCooldown());
    }
}
