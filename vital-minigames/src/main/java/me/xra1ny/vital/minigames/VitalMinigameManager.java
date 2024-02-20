package me.xra1ny.vital.minigames;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.java.Log;
import me.xra1ny.essentia.inject.DIFactory;
import me.xra1ny.essentia.inject.annotation.AfterInit;
import me.xra1ny.essentia.inject.annotation.Component;
import me.xra1ny.vital.core.VitalComponent;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Optional;

/**
 * Manages the current state of a minigame using the Vital framework.
 *
 * @apiNote This class may be extended from, to add more specific mini-game manager logic or function, depending on the mini-game you are trying to implement.
 * @author xRa1ny
 */
@Log
@Component
public final class VitalMinigameManager implements VitalComponent {
    private static VitalMinigameManager instance;

    /**
     * The currently active minigame state.
     */
    @Getter
    private VitalMinigameState vitalMinigameState;

    private final JavaPlugin javaPlugin;

    /**
     * Constructs a new minigame manager instance with the specified {@link JavaPlugin}.
     *
     * @param javaPlugin The {@link JavaPlugin}.
     */
    public VitalMinigameManager(@NonNull JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
    }

    @AfterInit
    public void afterInit() {
        instance = this;
        log.info("VitalMinigameManager online!");
    }

    /**
     * Checks if the current minigame state matches a specified class.
     *
     * @param vitalMinigameStateClass The class of the minigame state to compare with.
     * @param <T>                     The type of minigame state.
     * @return True if the current state is of the specified class, otherwise false.
     */
    @SuppressWarnings("unused")
    public static <T extends VitalMinigameState> boolean isVitalMinigameState(@NonNull Class<T> vitalMinigameStateClass) {
        if(instance.vitalMinigameState == null) {
            return false;
        }

        return vitalMinigameStateClass.equals(instance.vitalMinigameState.getClass());
    }

    /**
     * Sets the current minigame state by Class.
     *
     * @apiNote this method attempts to construct a dependency injected instance using Vital's DI utils {@link DIFactory}
     * @param vitalMinigameStateClass The Class of the minigame state to set to (must be registered).
     */
    @SneakyThrows // TODO
    public static void setVitalMinigameState(@NonNull Class<? extends VitalMinigameState> vitalMinigameStateClass) {
        final Optional<? extends VitalMinigameState> optionalDiVitalMinigameState = Optional.ofNullable(DIFactory.getInstance(vitalMinigameStateClass));

        optionalDiVitalMinigameState.ifPresent(VitalMinigameManager::setVitalMinigameState);
    }

    /**
     * Sets the current minigame state.
     * If a previous state exists, it is unregistered before registering the new state.
     *
     * @param vitalMinigameState The new minigame state to set.
     */
    @SuppressWarnings("unused")
    public static void setVitalMinigameState(@NonNull VitalMinigameState vitalMinigameState) {
        if (instance.vitalMinigameState != null) {
            if (instance.vitalMinigameState instanceof VitalCountdownMinigameState vitalCountdownMinigameState) {
                vitalCountdownMinigameState.stopCountdown();
            }

            // unregister listener from bukkit.
            HandlerList.unregisterAll(instance.vitalMinigameState);
            instance.vitalMinigameState.onUnregistered();
        }

        instance.vitalMinigameState = vitalMinigameState;
        Bukkit.getPluginManager().registerEvents(vitalMinigameState, instance.javaPlugin);
        vitalMinigameState.onRegistered();
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }
}