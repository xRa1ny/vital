package me.xra1ny.vital.configs;

import com.google.j2objc.annotations.Property;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Wrapper class to store player data to a config file.
 *
 * @author xRa1ny
 */
@Data
public class ConfigPlayer {
    @Property("name")
    private String name;

    @Property("uuid")
    private UUID uuid;

    @Property("location")
    private ConfigLocation location;

    @Property("health")
    private double health;

    @Property("food-level")
    private double foodLevel;

    @NonNull
    public static ConfigPlayer of(@NonNull Player player) {
        final ConfigPlayer configPlayer = new ConfigPlayer();

        configPlayer.name = player.getName();
        configPlayer.uuid = player.getUniqueId();
        configPlayer.location = ConfigLocation.of(player.getLocation());
        configPlayer.health = player.getHealth();
        configPlayer.foodLevel = player.getFoodLevel();

        return configPlayer;
    }

    @NonNull
    public Optional<Player> toPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(uuid));
    }
}
