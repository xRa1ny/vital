package me.xra1ny.vital.utils;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.Nullable;

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
        broadcastAction(Player::clearActivePotionEffects);
    }

    /**
     * Clears all potion effects for all players currently connected to this server.
     *
     * @param playerPredicate The {@link Predicate} specifying the condition in which all potion effects are removed.
     */
    public static void broadcastClearPotionEffects(@NonNull Predicate<Player> playerPredicate) {
        broadcastAction(Player::clearActivePotionEffects, playerPredicate);
    }
}
