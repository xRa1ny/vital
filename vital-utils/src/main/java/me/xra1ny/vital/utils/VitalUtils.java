package me.xra1ny.vital.utils;

import lombok.NonNull;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.title.Title;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static net.kyori.adventure.text.Component.empty;

/**
 * Utility class for operations many developers might find useful.
 *
 * @author xRa1ny
 * @apiNote This class can be used standalone, detached from any Vital project. It only contains utilities for easier interaction with the SpigotAPI.
 */
@SuppressWarnings("unused")
public class VitalUtils {
    /**
     * Broadcasts an action to be performed for each player currently connected to this server.
     *
     * @param action          The action to perform for each player.
     * @param playerPredicate The {@link Predicate} specifying the condition in which each action is performed.
     */
    public static void broadcastAction(@NonNull Predicate<Player> playerPredicate, @NonNull Consumer<Player> action) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .forEach(action);
    }

    /**
     * Broadcasts an action to be performed for each player currently connected to this server.
     *
     * @param action The action to perform for each player.
     */
    public static void broadcastAction(@NonNull Consumer<Player> action) {
        broadcastAction(p -> true, action);
    }

    /**
     * Broadcasts a message to all players currently connected to the server, matching the given {@link Predicate}.
     *
     * @param message         The message to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the message should be broadcast.
     * @param tagResolvers    Any tag resolvers for custom minimessage tag syntax.
     */
    public static void broadcastMessage(@NonNull String message, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(playerPredicate, player -> player.sendRichMessage(message, tagResolvers));
    }

    /**
     * Broadcasts a message to all connected players on the server.
     *
     * @param message      The message to broadcast.
     * @param tagResolvers Any tag resolvers for custom minimessage tag syntax.
     * @apiNote The given message will be broadcast in minimessage syntax.
     */
    public static void broadcastMessage(@NonNull String message, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastMessage(message, player -> true, tagResolvers);
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param volume          The volume of the sound.
     * @param pitch           The pitch of the sound.
     * @param playerPredicate The Predicate specifying the condition in which the sound is broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound, float volume, float pitch, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(playerPredicate, player -> player.playSound(player, sound, volume, pitch));
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     *
     * @param sound  The sound to broadcast.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void broadcastSound(@NonNull Sound sound, float volume, float pitch) {
        broadcastSound(sound, volume, pitch, player -> true);
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the sound should be broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound, @NonNull Predicate<Player> playerPredicate) {
        broadcastSound(sound, 1f, 1f, playerPredicate);
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     * volume: 1f, pitch: 1f.
     *
     * @param sound The sound to broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound) {
        broadcastSound(sound, player -> true);
    }

    /**
     * Sends a title to the given player in minimessage syntax with the specified predicate and tag resolvers for any custom minimessage tags for replacement.
     *
     * @param player          The player.
     * @param title           The title.
     * @param subtitle        The subtitle.
     * @param fadeIn          The fade in times (measured in ticks).
     * @param stay            The stay times (measured in ticks).
     * @param fadeOut         The fade out times (measured in ticks).
     * @param playerPredicate The predicate the player MUST MATCH when sending the title.
     * @param tagResolvers    Any custom tag resolvers for custom minimessage tags.
     */
    public static void sendTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @Range(from = 0, to = 72_000) int stay, @Range(from = 0, to = 72_000) int fadeOut, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        if (playerPredicate.test(player)) {
            player.showTitle(Title.title(
                    title == null ? empty() : MiniMessage.miniMessage().deserialize(title, tagResolvers),
                    subtitle == null ? empty() : MiniMessage.miniMessage().deserialize(subtitle, tagResolvers),
                    Title.Times.times(Duration.ofMillis((long) ((fadeIn / 20f) * 1_000)), Duration.ofMillis((long) ((stay / 20f) * 1_000)), Duration.ofMillis((long) ((fadeOut / 20f) * 1_000)))
            ));
        }
    }

    /**
     * Sends a title to the given player in minimessage syntax with the specified tag resolvers for any custom minimessage tags for replacement.
     *
     * @param player       The player.
     * @param title        The title.
     * @param subtitle     The subtitle.
     * @param fadeIn       The fade in times (measured in ticks).
     * @param stay         The stay times (measured in ticks).
     * @param fadeOut      The fade out times (measured in ticks).
     * @param tagResolvers Any custom tag resolvers for custom minimessage tags.
     */
    public static void sendTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @Range(from = 0, to = 72_000) int stay, @Range(from = 0, to = 72_000) int fadeOut, @NonNull TagResolver @NonNull ... tagResolvers) {
        sendTitle(player, title, subtitle, fadeIn, stay, fadeOut, p -> true, tagResolvers);
    }

    public static void sendTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        if(playerPredicate.test(player)) {
            player.showTitle(Title.title(
                    title == null ? empty() : MiniMessage.miniMessage().deserialize(title, tagResolvers),
                    subtitle == null ? empty() : MiniMessage.miniMessage().deserialize(subtitle, tagResolvers)
            ));
        }
    }

    public static void sendTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @NonNull TagResolver @NonNull ... tagResolvers) {
        sendTitle(player, title, subtitle, p -> true, tagResolvers);
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param fadeIn          The fade-in amount (in ticks).
     * @param stay            The stay amount (in ticks).
     * @param fadeOut         The fade-out amount (in ticks).
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     * @param tagResolvers    Any tag resolvers for custom minimessage replacement syntax.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @Range(from = 0, to = 72_000) int stay, @Range(from = 0, to = 72_000) int fadeOut, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(player -> sendTitle(player, title, subtitle, fadeIn, stay, fadeOut, tagResolvers));
    }

    /**
     * Broadcasts a title to all players currently connected to this server, matching the given {@link Predicate}
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     * @param tagResolvers    Any tag resolvers for custom minimessage tag syntax.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(player -> sendTitle(player, title, subtitle, playerPredicate, tagResolvers));
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title        The title to broadcast.
     * @param subtitle     The subtitle to broadcast.
     * @param tagResolvers Any tag resolvers for custom minimessage tag syntax.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastTitle(title, subtitle, player -> true, tagResolvers);
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title        The title to broadcast.
     * @param subtitle     The subtitle to broadcast.
     * @param fadeIn       The fade-in amount (in ticks).
     * @param stay         The stay amount (in ticks).
     * @param fadeOut      The fade-out amount (in ticks).
     * @param tagResolvers Any tag resolvers for custom minimessage tag syntax.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastTitle(title, subtitle, fadeIn, stay, fadeOut, player -> true, tagResolvers);
    }

    /**
     * Sends a persistent (permanent) title to the given player in minimessage syntax with the specified predicate and tag resolvers for any custom minimessage tags for replacement.
     *
     * @param player          The player.
     * @param title           The title.
     * @param subtitle        The subtitle.
     * @param fadeIn          The fade in times (measured in ticks).
     * @param playerPredicate The predicate the player MUST MATCH when sending the title.
     * @param tagResolvers    Any custom tag resolvers for custom minimessage tags.
     * @apiNote The title will stay approx. 1h
     */
    public static void sendPersistentTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        sendTitle(player, title, subtitle, fadeIn, 72_000 /* 1h */, 0, playerPredicate, tagResolvers);
    }

    /**
     * Sends a persistent (permanent) title to the given player in minimessage syntax with the specified tag resolvers for any custom minimessage tags for replacement.
     *
     * @param player       The player.
     * @param title        The title.
     * @param subtitle     The subtitle.
     * @param fadeIn       The fade in times (measured in ticks).
     * @param tagResolvers Any custom tag resolvers for custom minimessage tags.
     * @apiNote The title will stay approx. 1h
     */
    public static void sendPersistentTitle(@NonNull Player player, @Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @NonNull TagResolver @NonNull ... tagResolvers) {
        sendTitle(player, title, subtitle, fadeIn, 72_000 /* 1h */, 0, p -> true, tagResolvers);
    }

    /**
     * Broadcasts a persistent (permanent) title to all players in minimessage syntax with the specified predicate and tag resolvers for any custom minimessage tags for replacement.
     *
     * @param title           The title.
     * @param subtitle        The subtitle.
     * @param fadeIn          The fade in times (measured in ticks).
     * @param playerPredicate The predicate the player MUST MATCH when sending the title.
     * @param tagResolvers    Any custom tag resolvers for custom minimessage tags.
     * @apiNote The title will stay approx. 1h
     */
    public static void broadcastPersistentTitle(@Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(player -> sendPersistentTitle(player, title, subtitle, fadeIn, playerPredicate, tagResolvers));
    }

    /**
     * Broadcasts a persistent (permanent) title to all players in minimessage syntax with the specified tag resolvers for any custom minimessage tags for replacement.
     *
     * @param title        The title.
     * @param subtitle     The subtitle.
     * @param fadeIn       The fade in times (measured in ticks).
     * @param tagResolvers Any custom tag resolvers for custom minimessage tags.
     * @apiNote The title will stay approx. 1h
     */
    public static void broadcastPersistentTitle(@Nullable String title, @Nullable String subtitle, @Range(from = 0, to = 72_000) int fadeIn, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(player -> sendPersistentTitle(player, title, subtitle, fadeIn, tagResolvers));
    }

    /**
     * Broadcast a {@link PotionEffect} to all players currently connected to this server.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     * @param duration         The duration (in ticks).
     * @param amplifier        The amplifier.
     * @param playerPredicate  The {@link Predicate} specifying the condition in which the potion effect is broadcast.
     */
    public static void broadcastPotionEffect(@NonNull PotionEffectType potionEffectType, int duration, int amplifier, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(playerPredicate, player -> player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)));
    }

    /**
     * Broadcasts a {@link PotionEffect} to all players currently connected to this server.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     * @param duration         The duration (in ticks).
     * @param amplifier        The amplifier.
     */
    public static void broadcastPotionEffect(@NonNull PotionEffectType potionEffectType, int duration, int amplifier) {
        broadcastPotionEffect(potionEffectType, duration, amplifier, player -> true);
    }

    /**
     * Clears a potion effect for all players currently connected to this server, matching the given {@link PotionEffectType}.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     * @param playerPredicate  The {@link Predicate} specifying the condition in which the potion effect is removed.
     */
    public static void broadcastClearPotionEffect(@NonNull PotionEffectType potionEffectType, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(playerPredicate, player -> player.removePotionEffect(potionEffectType));
    }

    /**
     * Clears a potion effect for all players currently connected to this server matching the given {@link PotionEffectType}.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     */
    public static void broadcastClearPotionEffect(@NonNull PotionEffectType potionEffectType) {
        broadcastClearPotionEffect(potionEffectType, player -> true);
    }

    /**
     * Clears all potion effects for all players currently connected to this server.
     *
     * @param playerPredicate The {@link Predicate} specifying the condition in which all potion effects are removed.
     */
    public static void broadcastClearPotionEffects(@NonNull Predicate<Player> playerPredicate) {
        broadcastAction(playerPredicate, player -> player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect));
    }

    /**
     * Clears all potion effects for all players currently connected to this server.
     */
    public static void broadcastClearPotionEffects() {
        broadcastClearPotionEffects(player -> true);
    }

    /**
     * Checks if the given {@link Material} type is valid for placement in midair.
     *
     * @param material The {@link Material} type.
     * @return true if the type can be placed in midair; false otherwise.
     */
    public static boolean canBePlacedInMidAir(@NonNull Material material) {
        return !material.hasGravity() &&
                !isVegetation(material) &&
                (material != Material.REDSTONE &&
                        material != Material.REDSTONE_TORCH &&
                        material != Material.REPEATER &&
                        material != Material.COMPARATOR &&
                        material != Material.LEVER &&
                        material != Material.TRIPWIRE &&
                        !material.name().contains("BUTTON") &&
                        !material.name().contains("PRESSURE_PLATE") &&
                        !material.name().contains("RAIL"));
    }

    /**
     * Checks if the given {@link Material} type is vegetation or not.
     *
     * @param material The {@link Material} type.
     * @return true if the given type is vegetation; false otherwise.
     */
    public static boolean isVegetation(@NonNull Material material) {
        return material.name().contains("SAPLING") ||
                material.name().contains("FLOWER") ||
                material.name().contains("WHEAT") ||
                material.name().contains("SEEDS") ||
                material.name().contains("CROP") ||
                material.name().contains("KELP") ||
                material.name().contains("BUSH") ||
                material.name().contains("MUSHROOM") ||
                material.name().contains("CHORUS") ||
                material.name().contains("FERN") ||
                material.name().contains("POTTED") ||
                material.name().contains("ROSE") ||
                material.name().contains("POPPY") ||
                material == Material.MELON_STEM ||
                material == Material.PUMPKIN_STEM ||
                material == Material.BAMBOO ||
                material == Material.SUGAR_CANE ||
                material == Material.SEA_PICKLE ||
                material == Material.NETHER_WART ||
                material == Material.LILY_PAD ||
                material == Material.VINE ||
                material == Material.GLOW_LICHEN ||
                material == Material.SCULK_VEIN ||
                material == Material.CACTUS ||
                material == Material.LILAC ||
                material == Material.PEONY ||
                material == Material.TALL_GRASS ||
                material == Material.TALL_SEAGRASS ||
                material == Material.MANGROVE_PROPAGULE;
    }

    /**
     * Checks if the given {@link Material} type is a redstone machine like, redstone torch, piston, comparator, etc.
     *
     * @param material The {@link Material} type.
     * @return true if the type is a redstone machine; false otherwise.
     */
    public static boolean isRedstoneMachine(@NonNull Material material) {
        return material.getCreativeCategory() == CreativeCategory.REDSTONE && (
                material == Material.REDSTONE_TORCH ||
                        material.name().contains("PISTON") ||
                        material.name().contains("BUTTON") ||
                        material.name().contains("PRESSURE_PLATE") ||
                        material.name().contains("DETECTOR") ||
                        material.name().contains("LAMP") ||
                        material == Material.COMPARATOR ||
                        material == Material.REPEATER ||
                        material == Material.REDSTONE ||
                        material == Material.REDSTONE_WIRE ||
                        material == Material.OBSERVER ||
                        material == Material.DROPPER ||
                        material == Material.DISPENSER ||
                        material == Material.HOPPER ||
                        material == Material.HOPPER_MINECART);
    }

    /**
     * Checks if the given location is contained within the mapped location1 and location2 area.
     *
     * @param location1 The first location.
     * @param location2 The second location.
     * @param location  The location to check if it is contained within the area.
     * @return true if the location is within the area; false otherwise.
     */
    public static boolean isInsideLocationArea(@NonNull Location location1, @NonNull Location location2, @NonNull Location location) {
        final double ourMinX = Math.min(location1.getX(), location2.getX());
        final double ourMaxX = Math.max(location1.getX(), location2.getX());

        final double ourMinY = Math.min(location1.getY(), location2.getY());
        final double ourMaxY = Math.max(location1.getY(), location2.getY());

        final double ourMinZ = Math.min(location1.getZ(), location2.getZ());
        final double ourMaxZ = Math.max(location1.getZ(), location2.getZ());

        final double theirX = location.getX();
        final double theirY = location.getY();
        final double theirZ = location.getZ();

        return theirX >= ourMinX && theirX <= ourMaxX &&
                theirY >= ourMinY && theirY <= ourMaxY &&
                theirZ >= ourMinZ && theirZ <= ourMaxZ;
    }

    /**
     * Gets a random location within the mapped location1 and location2 area.
     *
     * @param location1 The first location.
     * @param location2 The second location.
     * @return The randomly calculated area location.
     */
    @NonNull
    public static Location getRandomLocationInLocationArea(@NonNull Location location1, @NonNull Location location2) {
        final double ourMinX = Math.min(location1.getX(), location2.getX());
        final double ourMaxX = Math.max(location1.getX(), location2.getX());

        final double ourMinY = Math.min(location1.getY(), location2.getY());
        final double ourMaxY = Math.max(location1.getY(), location2.getY());

        final double ourMinZ = Math.min(location1.getZ(), location2.getZ());
        final double ourMaxZ = Math.max(location1.getZ(), location2.getZ());

        final double randomX = new Random().nextDouble(ourMinX, ourMaxX);
        final double randomY = new Random().nextDouble(ourMinY, ourMaxY);
        final double randomZ = new Random().nextDouble(ourMinZ, ourMaxZ);

        return new Location(location1.getWorld(), randomX, randomY, randomZ);
    }

    /**
     * Gets the centered offset block location of the given location.
     *
     * @param location The block location.
     * @param xOffset  The x offset.
     * @param yOffset  The y offset.
     * @param zOffset  The z offset.
     * @return The centered offset location.
     */
    @NonNull
    public static Location getCenterBlockLocation(@NonNull Location location, double xOffset, double yOffset, double zOffset) {
        final Location finalLocation = location.getBlock().getLocation().clone()
                .add(.5, .5, .5)
                .add(xOffset, yOffset, zOffset);

        finalLocation.setPitch(location.getPitch());
        finalLocation.setYaw(location.getYaw());
        finalLocation.setDirection(location.getDirection());

        return finalLocation;
    }

    /**
     * Gets the center location of the targeted location block.
     *
     * @return The centered block location.
     */
    @NonNull
    public static Location getCenterBlockLocation(@NonNull Location location) {
        return getCenterBlockLocation(location, 0, 0, 0);
    }

    /**
     * Gets the top location of the targeted location block, while offsetting only the y-axis to be on top of the block.
     *
     * @param location The location.
     * @return The location.
     */
    @NonNull
    public static Location getCenterBlockTopLocation(@NonNull Location location) {
        return getCenterBlockLocation(location, 0, .5, 0);
    }

    /**
     * Gets the horizontal centered location of the targeted location block, while offsetting only the x- and z-axis of the block.
     *
     * @param location The location.
     * @return The location.
     */
    @NonNull
    public static Location getCenterBlockSideLocation(@NonNull Location location) {
        return getCenterBlockLocation(location, 0, -.5, 0);
    }

    /**
     * Takes the given world and "cleans" all rules for minigame or stale world purposes.
     *
     * @param world The world.
     * @apiNote Calling this method sets the following values:
     * <ul>
     *  <li>DO_DAYLIGHT_CYCLE       : false</li>
     *  <li>DO_FIRE_TICK            : false</li>
     *  <li>DO_MOB_SPAWNING         : false</li>
     *  <li>ANNOUNCE_ADVANCEMENTS   : false</li>
     *  <li>DO_MOB_LOOT             : false</li>
     *  <li>DO_MOB_SPAWNING         : false</li>
     *  <li>DO_TRADER_SPAWNING      : false</li>
     *  <li>DO_VINES_SPREAD         : false</li>
     *  <li>MOB_GRIEFING            : false</li>
     *  <li>SHOW_DEATH_MESSAGES     : false</li>
     *  <li>KEEP_INVENTORY          : true</li>
     *  <li>DISABLE_RAIDS           : true</li>
     *  <li>TIME                    : 0</li>
     *  <li>DIFFICULTY              : PEACEFUL</li>
     *  <li>DO_WEATHER_CYCLE        : false</li>
     *  <li>WEATHER_DURATION        : 0</li>
     * </ul>
     */
    public static void cleanGameRules(@NonNull World world) {
        world.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        world.setGameRule(GameRule.DO_FIRE_TICK, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false);
        world.setGameRule(GameRule.DO_MOB_LOOT, false);
        world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        world.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        world.setGameRule(GameRule.DO_VINES_SPREAD, false);
        world.setGameRule(GameRule.MOB_GRIEFING, false);
        world.setGameRule(GameRule.SHOW_DEATH_MESSAGES, false);
        world.setGameRule(GameRule.KEEP_INVENTORY, true);
        world.setGameRule(GameRule.DISABLE_RAIDS, true);
        world.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        world.setTime(0);
        world.setDifficulty(Difficulty.PEACEFUL);
        world.setWeatherDuration(0);
    }

    /**
     * Takes the given world by name and "cleans" all gamerules for minigame or clean world purposes.
     *
     * @param worldName The name of the world to clean.
     * @see VitalUtils#cleanGameRules(World) for more information about cleansed world rules.
     */
    public static void cleanGameRules(@NonNull String worldName) {
        final World world = Optional.ofNullable(Bukkit.getWorld(worldName))
                .orElseThrow(() -> new RuntimeException("World %s does not exist"
                        .formatted(worldName)));

        cleanGameRules(world);
    }

    /**
     * Sends an action bar message to the given player in minimessage syntax.
     *
     * @param player The player.
     * @param message The message in minimessage syntax.
     * @param playerPredicate The predicate the player MUST MATCH WITH.
     * @param tagResolvers Any custom tag resolvers for minimessage tag syntax.
     */
    public static void sendActionBar(@NonNull Player player, @NonNull String message, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        if(playerPredicate.test(player)) {
            player.sendActionBar(MiniMessage.miniMessage().deserialize(message, tagResolvers));
        }
    }

    /**
     * Sends an action bar message to the given player in minimessage syntax.
     *
     * @param player The player.
     * @param message The message in minimessage syntax.
     * @param tagResolvers Any custom tag resolver for minimessage tag syntax.
     */
    public static void sendActionBar(@NonNull Player player, @NonNull String message, @NonNull TagResolver @NonNull ... tagResolvers) {
        sendActionBar(player, message, p -> true, tagResolvers);
    }

    /**
     * Broadcasts an action bar message for all players in minimessage syntax.
     *
     * @param message The message.
     * @param playerPredicate The predicate every player MUST MATCH WITH.
     * @param tagResolvers Any custom tag resolvers for minimessage tag syntax.
     */
    public static void broadcastActionBar(@NonNull String message, @NonNull Predicate<Player> playerPredicate, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastAction(player -> sendActionBar(player, message, playerPredicate, tagResolvers));
    }

    /**
     * Broadcasts an action bar message for all players in minimessage syntax.
     *
     * @param message The message.
     * @param tagResolvers Any custom tag resolvers for minimessage tag syntax.
     */
    public static void broadcastActionBar(@NonNull String message, @NonNull TagResolver @NonNull ... tagResolvers) {
        broadcastActionBar(message, player -> true, tagResolvers);
    }

    /**
     * Teleports the given player to the specified location with the given effect.
     *
     * @param player The player to teleport.
     * @param location The location to teleport the player to.
     * @param potionEffectType The potion effect for the teleportation.
     */
    public static void teleport(@NonNull Player player, @NonNull Location location, @NonNull PotionEffectType potionEffectType) {
        player.removePotionEffect(potionEffectType);
        player.addPotionEffect(new PotionEffect(potionEffectType, 2, Integer.MAX_VALUE));
        player.teleport(location);
        player.removePotionEffect(potionEffectType);
    }

    /**
     * Teleports the given player to the specified location with an effect.
     *
     * @param player The player to teleport.
     * @param location The location to teleport the player to.
     */
    public static void teleport(@NonNull Player player, @NonNull Location location) {
        teleport(player, location, PotionEffectType.SLOW);
    }

    /**
     * Teleports the given player to the specified target entity with an effect.
     *
     * @param player The player to teleport.
     * @param to The entity to teleport to.
     */
    public static void teleport(@NonNull Player player, @NonNull Entity to) {
        teleport(player, to.getLocation(), PotionEffectType.SLOW);
    }
}
