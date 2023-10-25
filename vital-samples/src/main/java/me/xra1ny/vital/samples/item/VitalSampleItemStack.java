package me.xra1ny.vital.samples.item;

import me.xra1ny.vital.items.VitalItemStack;
import me.xra1ny.vital.items.VitalItemStackInfo;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("DefaultAnnotationParam")
@VitalItemStackInfo(
        name = "",
        type = Material.STICK,
        amount = 1,
        cooldown = 0,
        enchanted = false,
        itemFlags = {
                ItemFlag.HIDE_ENCHANTS
        },
        localised = false,
        lore = {
                "lore1",
                "lore2"
        }
)
public class VitalSampleItemStack extends VitalItemStack {
    @Override
    public Class<VitalItemStackInfo> requiredAnnotationType() {
        return null;
    }

    @Override
    public void onVitalComponentRegistered() {

    }

    @Override
    public void onVitalComponentUnregistered() {

    }

    @Override
    public void onLeftClick(@NotNull PlayerInteractEvent e, @NotNull Player player) {

    }

    @Override
    public void onRightClick(@NotNull PlayerInteractEvent e, @NotNull Player player) {

    }

    @Override
    public void onCooldown(@NotNull PlayerInteractEvent e, @NotNull Player player) {

    }

    @Override
    public void onCooldownExpire(@NotNull Player player) {

    }
}
