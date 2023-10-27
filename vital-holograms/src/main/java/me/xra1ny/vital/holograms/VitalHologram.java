package me.xra1ny.vital.holograms;

import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import me.xra1ny.vital.core.VitalComponent;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hologram in the Vital framework.
 * Implements the VitalComponent interface.
 *
 * @author xRa1ny
 */
public final class VitalHologram implements VitalComponent {

    /**
     * The name of this hologram.
     */
    @Getter(onMethod = @__(@NotNull))
    @Setter(onParam = @__(@NotNull))
    private String name;

    /**
     * The base armor stand of this hologram.
     */
    @Getter(onMethod = @__(@NotNull))
    private ArmorStand base;

    /**
     * The armor stand lines of this hologram.
     */
    @Getter(onMethod = @__(@NotNull))
    @Setter(onParam = @__(@NotNull))
    private List<ArmorStand> baseLines = new ArrayList<>();

    /**
     * The lines of this hologram.
     */
    @Getter(onMethod = @__({@NotNull, @Unmodifiable}))
    @Setter(onParam = @__(@NotNull))
    private List<String> lines = new ArrayList<>();

    /**
     * The location of this hologram.
     */
    @Getter(onMethod = @__(@NotNull))
    @Setter(onParam = @__(@NotNull))
    private Location location;

    /**
     * The item this hologram displays.
     */
    @Getter(onMethod = @__(@NotNull))
    @Setter(onParam = @__(@NotNull))
    private Material displayType;

    /**
     * Constructs a new VitalHologram instance.
     *
     * @param name        The name of the hologram.
     * @param location    The location where the hologram should be displayed.
     * @param displayType The material type to display as an item (nullable).
     * @param lines       The lines of text to display in the hologram.
     */
    @SneakyThrows
    public VitalHologram(@NotNull String name, @NotNull Location location, @Nullable Material displayType, @NotNull String... lines) {
        this.name = name;
        this.lines.addAll(List.of(lines));
        this.location = location;
        this.displayType = displayType;
    }

    /**
     * Removes this hologram and its associated entities.
     */
    public void remove() {
        for (Entity entity : this.base.getPassengers()) {
            entity.remove();
        }

        for (ArmorStand armorStand : this.baseLines) {
            armorStand.remove();
        }

        this.base.remove();
    }

    /**
     * Updates the hologram by recreating its elements.
     */
    @SuppressWarnings("deprecation")
    public void update() {
        if(base == null) {
            base = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        }

        this.base.setVisible(false);
        this.base.setMarker(true);
        base.teleport(location);

        if (this.displayType != null) {
            for(Entity entity : base.getPassengers()) {
                entity.remove();
            }

            final Item item = this.location.getWorld().dropItem(this.base.getEyeLocation(), new ItemStack(this.displayType));

            item.setPickupDelay(Integer.MAX_VALUE);
            this.base.addPassenger(item);
        }

        final int initialBaseLineSize = baseLines.size();

        for (int i = this.lines.size() - 1; i > -1; i--) {
            final String line = this.lines.get(i);
            final Location lineLocation = this.location.clone().add(0, this.lines.size(), 0);
            final ArmorStand armorStand;

            if(i >= initialBaseLineSize) {
                armorStand = (ArmorStand) this.location.getWorld().spawnEntity(lineLocation.subtract(0, 2 + (.25*i), 0), EntityType.ARMOR_STAND);
                this.baseLines.add(armorStand);
            }else {
                armorStand = baseLines.get(i);
                armorStand.teleport(lineLocation);
            }

            armorStand.setVisible(false);
            armorStand.setMarker(true);
            armorStand.setCustomName(line);
            armorStand.setCustomNameVisible(true);
        }
    }

    /**
     * Called when this VitalHologram is registered.
     * Calls the update method to initialize the hologram.
     */
    @Override
    public void onRegistered() {
        update();
    }

    /**
     * Called when this VitalHologram is unregistered.
     * Calls the remove method to clean up the hologram and associated entities.
     */
    @Override
    public void onUnregistered() {
        remove();
    }
}
