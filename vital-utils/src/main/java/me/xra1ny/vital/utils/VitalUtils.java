package me.xra1ny.vital.utils;

import lombok.NonNull;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.CreativeCategory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Utility class for operations many developers might find useful.
 *
 * @author xRa1ny
 * @apiNote This class can be used standalone, detached from any Vital project. It only contains utilities for easier interaction with the SpigotAPI.
 */
public class VitalUtils {
    /**
     * Broadcasts an action to be performed for each player currently connected to this server.
     *
     * @param action The action to perform for each player.
     */
    public static void broadcastAction(@NonNull Consumer<Player> action) {
        Bukkit.getOnlinePlayers()
                .forEach(action);
    }

    /**
     * Broadcasts an action to be performed for each player currently connected to this server.
     *
     * @param action          The action to perform for each player.
     * @param playerPredicate The {@link Predicate} specifying the condition in which each action is performed.
     */
    public static void broadcastAction(@NonNull Consumer<Player> action, @NonNull Predicate<Player> playerPredicate) {
        Bukkit.getOnlinePlayers().stream()
                .filter(playerPredicate)
                .forEach(action);
    }

    /**
     * Broadcasts a message to all players currently connected to the server.
     * Identical to {@link Bukkit#broadcastMessage(String)}.
     * This method is supplied for convenience.
     *
     * @param message The message to broadcast.
     */
    public static void broadcastMessage(@NonNull String message) {
        broadcastAction(player -> player.sendMessage(message));
    }

    /**
     * Broadcasts a message to all players currently connected to the server, matching the given {@link Predicate}.
     *
     * @param message         The message to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the message should be broadcast.
     */
    public static void broadcastMessage(@NonNull String message, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.sendMessage(message), playerPredicate);
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     * volume: 1f, pitch: 1f.
     *
     * @param sound The sound to broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound) {
        broadcastAction(player -> player.playSound(player, sound, 1f, 1f));
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server, matching the given {@link Predicate}.
     *
     * @param sound           The sound to broadcast.
     * @param playerPredicate The Predicate specifying the condition in which the sound should be broadcast.
     */
    public static void broadcastSound(@NonNull Sound sound, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.playSound(player, sound, 1f, 1f), playerPredicate);
    }

    /**
     * Broadcasts a {@link Sound} to all players currently connected to this server.
     *
     * @param sound  The sound to broadcast.
     * @param volume The volume of the sound.
     * @param pitch  The pitch of the sound.
     */
    public static void broadcastSound(@NonNull Sound sound, float volume, float pitch) {
        broadcastAction(player -> player.playSound(player, sound, volume, pitch));
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
        broadcastAction(player -> player.playSound(player, sound, volume, pitch), playerPredicate);
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle) {
        broadcastAction(player -> player.sendTitle(title, subtitle));
    }

    /**
     * Broadcasts a title to all players currently connected to this server, matching the given {@link Predicate}
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.sendTitle(title, subtitle), playerPredicate);
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     * @param fadeIn   The fade-in amount (in ticks).
     * @param stay     The stay amount (in ticks).
     * @param fadeOut  The fade-out amount (in ticks).
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut) {
        broadcastAction(player -> player.sendTitle(title, subtitle, fadeIn, stay, fadeOut));
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
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fadeIn, int stay, int fadeOut, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.sendTitle(title, subtitle, fadeIn, stay, fadeOut), playerPredicate);
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title    The title to broadcast.
     * @param subtitle The subtitle to broadcast.
     * @param fade     The fade amount used for both fade-in and fade-out (in ticks).
     * @param stay     The stay amount (in ticks).
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fade, int stay) {
        broadcastAction(player -> player.sendTitle(title, subtitle, fade, stay, fade));
    }

    /**
     * Broadcasts a title to all players currently connected to this server.
     *
     * @param title           The title to broadcast.
     * @param subtitle        The subtitle to broadcast.
     * @param fade            The fade amount used for both fade-in and fade-out (in ticks).
     * @param stay            The stay amount (in ticks).
     * @param playerPredicate The {@link Predicate} specifying the condition in which the title is broadcast.
     */
    public static void broadcastTitle(@Nullable String title, @Nullable String subtitle, int fade, int stay, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.sendTitle(title, subtitle, fade, stay, fade), playerPredicate);
    }

    /**
     * Broadcasts a {@link PotionEffect} to all players currently connected to this server.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     * @param duration         The duration (in ticks).
     * @param amplifier        The amplifier.
     */
    public static void broadcastPotionEffect(@NonNull PotionEffectType potionEffectType, int duration, int amplifier) {
        broadcastAction(player -> player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)));
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
        broadcastAction(player -> player.addPotionEffect(new PotionEffect(potionEffectType, duration, amplifier)), playerPredicate);
    }

    /**
     * Clears a potion effect for all players currently connected to this server matching the given {@link PotionEffectType}.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     */
    public static void broadcastClearPotionEffect(@NonNull PotionEffectType potionEffectType) {
        broadcastAction(player -> player.removePotionEffect(potionEffectType));
    }

    /**
     * Clears a potion effect for all players currently connected to this server, matching the given {@link PotionEffectType}.
     *
     * @param potionEffectType The {@link PotionEffectType}.
     * @param playerPredicate  The {@link Predicate} specifying the condition in which the potion effect is removed.
     */
    public static void broadcastClearPotionEffect(@NonNull PotionEffectType potionEffectType, @NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.removePotionEffect(potionEffectType), playerPredicate);
    }

    /**
     * Clears all potion effects for all players currently connected to this server.
     */
    public static void broadcastClearPotionEffects() {
        broadcastAction(player -> player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect));
    }

    /**
     * Clears all potion effects for all players currently connected to this server.
     *
     * @param playerPredicate The {@link Predicate} specifying the condition in which all potion effects are removed.
     */
    public static void broadcastClearPotionEffects(@NonNull Predicate<Player> playerPredicate) {
        broadcastAction(player -> player.getActivePotionEffects().stream()
                .map(PotionEffect::getType)
                .forEach(player::removePotionEffect), playerPredicate);
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
     * @param location The location to check if it is contained within the area.
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
     * @param xOffset The x offset.
     * @param yOffset The y offset.
     * @param zOffset The z offset.
     * @return The centered offset location.
     */
    @NonNull
    public static Location getCenterBlockLocation(@NonNull Location location, double xOffset, double yOffset, double zOffset) {
        return location.getBlock().getLocation().clone()
                .add(.5, .5, .5)
                .add(xOffset, yOffset, zOffset);
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
     * Takes the given world and "cleans" all gamerules for minigame purposes.
     *
     * @apiNote This calling this method sets the following values:
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
     * </ul>
     * @param world The world.
     */
    public static void cleanGamerules(@NonNull World world) {
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

        world.setTime(0);
        world.setDifficulty(Difficulty.PEACEFUL);
    }
}
