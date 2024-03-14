package me.xra1ny.vital.configs;

import lombok.NonNull;
import me.xra1ny.essentia.configs.annotation.Property;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

/**
 * Wrapper class to store player data to a config file.
 *
 * @author xRa1ny
 */
public class ConfigPlayer {
    @Property(String.class)
    public String name;

    @Property(UUID.class)
    public UUID uuid;

    @Property(ConfigLocation.class)
    public ConfigLocation location;

    @Property(double.class)
    public double health;

    @Property(double.class)
    public double foodLevel;

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
