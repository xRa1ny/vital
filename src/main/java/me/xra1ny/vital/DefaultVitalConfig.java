package me.xra1ny.vital;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigInfo;
import me.xra1ny.vital.configs.VitalConfigPath;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

/**
 * Configuration class that maps the configuration values from "config.yml" to Java fields.
 * Extends the VitalConfig class for convenient configuration handling.
 *
 * @author xRa1ny
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@VitalConfigInfo("config.yml")
public final class DefaultVitalConfig extends VitalConfig {

    /**
     * Flag indicating whether the database is enabled.
     * Set to true if the database should be enabled, false otherwise.
     * Example: false
     */
    @VitalConfigPath("vital.database-enabled")
    private boolean vitalDatabaseEnabled = false;

    /**
     * Prefix to be used in Vital messages.
     * Example: "§l§cVital  "
     */
    @VitalConfigPath("vital.prefix")
    private String vitalPrefix = "§l§cVital  ";

    /**
     * Color for chat messages.
     * Example: "GRAY"
     */
    @VitalConfigPath("vital.chat-color")
    private String vitalChatColorName = "GRAY";

    /**
     * Error message to display when a player lacks permission to perform an action.
     * Example: "§l§cERROR! §r§cNo permissions!"
     */
    @VitalConfigPath("vital.player-no-permission-error-message")
    private String vitalPlayerNoPermissionErrorMessage = "§l§cERROR! §r§cNo permissions!";

    /**
     * Error message to display when a player is not found.
     * Example: "§l§cERROR! Player not found!"
     */
    @VitalConfigPath("vital.player-not-found-error-message")
    private String vitalPlayerNotFoundErrorMessage = "§l§cERROR! Player not found!";

    /**
     * Error message displayed when a command can only be executed by a player.
     * Defines the error message shown to a user when they attempt to run a command
     * that is intended for player use only. This message should inform the user
     * that the command is exclusive to players.
     * Example: "§l§cERROR! Command can only be executed by a Player!"
     */
    @VitalConfigPath("vital.command-only-player-error-message")
    private String vitalCommandOnlyPlayerErrorMessage = "§l§cERROR! Command can only be executed by a Player!";

    /**
     * Error message to display when a command encounters an error.
     * Example: "§l§cERROR! An error occurred while performing Command!"
     */
    @VitalConfigPath("vital.command-error-message")
    private String vitalCommandErrorMessage = "§l§cERROR! An error occurred while performing Command!";

    /**
     * Error message to display when a command receives invalid arguments.
     * Example: "§l§cERROR! Command does not support provided Arguments!"
     */
    @VitalConfigPath("vital.command-invalid-args-error-message")
    private String vitalCommandInvalidArgsErrorMessage = "§l§cERROR! Command does not support provided Arguments!";

    /**
     * Error message to display when an internal error occurs during command execution.
     * Example: "§l§cERROR! Internal error while performing Command!"
     */
    @VitalConfigPath("vital.command-internal-error-message")
    private String vitalCommandInternalErrorMessage = "§l§cERROR! Internal error while performing Command!";

    /**
     * Session timeout duration in seconds for user sessions.
     * Defines the time, in seconds, after which the VitalPlayer instance is terminated
     * once a player leaves the server. A value of 0 means no session timeout.
     * Example: 0
     */
    @VitalConfigPath("vital.player-timeout")
    private int vitalPlayerTimeout = 0;

    public DefaultVitalConfig(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}

