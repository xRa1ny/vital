package me.xra1ny.vital.configs;

import com.google.j2objc.annotations.Property;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Optional;

/**
 * Wrapper class to store location data to a config file.
 *
 * @author xRa1ny
 */
@Data
public class ConfigLocation {
    @Property("world")
    private String world;

    @Property("x")
    private double x;

    @Property("y")
    private double y;

    @Property("z")
    private double z;

    @Property("pitch")
    private float pitch;

    @Property("yaw")
    private float yaw;

    @NonNull
    public static ConfigLocation of(@NonNull Location location) {
        final ConfigLocation configLocation = new ConfigLocation();

        configLocation.world = location.getWorld().getName();
        configLocation.x = location.getX();
        configLocation.y = location.getY();
        configLocation.z = location.getZ();
        configLocation.pitch = location.getPitch();
        configLocation.yaw = location.getYaw();

        return configLocation;
    }

    @NonNull
    public Optional<Location> toLocation() {
        final Optional<World> optionalWorld = Optional.ofNullable(Bukkit.getWorld(world));

        return optionalWorld.map(world -> new Location(world, x, y, z, pitch, yaw));

    }
}
