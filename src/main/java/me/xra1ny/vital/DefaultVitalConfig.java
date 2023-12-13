package me.xra1ny.vital;

import lombok.EqualsAndHashCode;
import lombok.NonNull;
import me.xra1ny.vital.configs.VitalConfig;
import me.xra1ny.vital.configs.VitalConfigInfo;
import me.xra1ny.vital.configs.VitalConfigPath;
import me.xra1ny.vital.players.VitalPlayer;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Default config for Vital.
 * Stores configurations many plugins might want / need.
 *
 * @author xRa1ny
 */
@EqualsAndHashCode(callSuper = true)
@VitalConfigInfo("config.yml")
public final class DefaultVitalConfig extends VitalConfig {

    /**
     * Flag indicating whether the database is enabled.
     * Example: false
     *
     * @apiNote Set to true if database support should be enabled, false otherwise.
     */
    @VitalConfigPath("vital.database-enabled")
    public boolean vitalDatabaseEnabled = false;

    /**
     * Prefix to be used in Vital messages.
     * Example: "§l§cVital  "
     */
    @VitalConfigPath("vital.prefix")
    public String vitalPrefix = "§l§cVital  ";

    /**
     * Color for chat messages.
     * Example: "GRAY"
     */
    @VitalConfigPath("vital.chat-color")
    public String vitalChatColorName = "GRAY";

    /**
     * Error message to display when a player lacks permission to perform an action.
     * Example: "§l§cERROR! §r§cNo permissions!"
     */
    @VitalConfigPath("vital.player-no-permission-error-message")
    public String vitalPlayerNoPermissionErrorMessage = "§l§cERROR! §r§cNo permissions!";

    /**
     * Error message to display when a player is not found.
     * Example: "§l§cERROR! Player not found!"
     */
    @VitalConfigPath("vital.player-not-found-error-message")
    public String vitalPlayerNotFoundErrorMessage = "§l§cERROR! Player not found!";

    /**
     * Error message displayed when a command can only be executed by a player.
     * Defines the error message shown to a user when they attempt to run a command
     * that is intended for player use only.
     * Example: "§l§cERROR! Command can only be executed by a Player!"
     *
     * @apiNote This message should inform the user that the command is exclusive to players.
     */
    @VitalConfigPath("vital.command-only-player-error-message")
    public String vitalCommandOnlyPlayerErrorMessage = "§l§cERROR! Command can only be executed by a Player!";

    /**
     * Error message to display when a command encounters an error.
     * Example: "§l§cERROR! An error occurred while performing Command!"
     */
    @VitalConfigPath("vital.command-error-message")
    public String vitalCommandErrorMessage = "§l§cERROR! An error occurred while performing Command!";

    /**
     * Error message to display when a command receives invalid arguments.
     * Example: "§l§cERROR! Command does not support provided Arguments!"
     */
    @VitalConfigPath("vital.command-invalid-args-error-message")
    public String vitalCommandInvalidArgsErrorMessage = "§l§cERROR! Command does not support provided Arguments!";

    /**
     * Error message to display when an internal error occurs during command execution.
     * Example: "§l§cERROR! Internal error while performing Command!"
     */
    @VitalConfigPath("vital.command-internal-error-message")
    public String vitalCommandInternalErrorMessage = "§l§cERROR! Internal error while performing Command!";

    /**
     * Session timeout duration in seconds for user sessions.
     * Defines the time, in seconds, after which the {@link VitalPlayer} instance is terminated once a player leaves the server.
     * Example: 0
     *
     * @apiNote A value of 0 means no session timeout.
     */
    @VitalConfigPath("vital.player-timeout")
    public int vitalPlayerTimeout = 0;

    /**
     * Constructs the default vital config using the supplied {@link JavaPlugin}.
     *
     * @param javaPlugin The {@link JavaPlugin}.
     */
    public DefaultVitalConfig(@NonNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }
}

