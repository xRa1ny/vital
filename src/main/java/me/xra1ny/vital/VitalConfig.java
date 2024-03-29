package me.xra1ny.vital;

import me.xra1ny.essentia.configs.Config;
import me.xra1ny.essentia.configs.annotation.ConfigInfo;
import me.xra1ny.essentia.configs.annotation.Property;
import me.xra1ny.essentia.configs.processor.YMLFileProcessor;
import me.xra1ny.essentia.inject.annotation.Component;

/**
 * Default config for Vital.
 * Stores configurations many plugins might want / need.
 *
 * @author xRa1ny
 */
@Component
@ConfigInfo(name = "config.yml", processor = YMLFileProcessor.class)
public final class VitalConfig extends Config {

    /**
     * Flag indicating whether the database is enabled.
     * Example: false
     *
     * @apiNote Set to true if database support should be enabled, false otherwise.
     */
    @Property(boolean.class)
    public boolean vitalDatabaseEnabled = false;

    /**
     * Prefix to be used in Vital messages.
     * Example: "§l§cVital  "
     */
    @Property(String.class)
    public String vitalPrefix = "§l§cVital  ";

    /**
     * Color for chat messages.
     * Example: "GRAY"
     */
    @Property(String.class)
    public String vitalChatColorName = "GRAY";

    /**
     * Error message to display when a player lacks permission to perform an action.
     * Example: "§l§cERROR! §r§cNo permissions!"
     */
    @Property(String.class)
    public String vitalPlayerNoPermissionErrorMessage = "§l§cERROR! §r§cNo permissions!";

    /**
     * Error message to display when a player is not found.
     * Example: "§l§cERROR! Player not found!"
     */
    @Property(String.class)
    public String vitalPlayerNotFoundErrorMessage = "§l§cERROR! Player not found!";

    /**
     * Error message displayed when a command can only be executed by a player.
     * Defines the error message shown to a user when they attempt to run a command
     * that is intended for player use only.
     * Example: "§l§cERROR! Command can only be executed by a Player!"
     *
     * @apiNote This message should inform the user that the command is exclusive to players.
     */
    @Property(String.class)
    public String vitalCommandOnlyPlayerErrorMessage = "§l§cERROR! Command can only be executed by a Player!";

    /**
     * Error message to display when a command encounters an error.
     * Example: "§l§cERROR! An error occurred while performing Command!"
     */
    @Property(String.class)
    public String vitalCommandErrorMessage = "§l§cERROR! An error occurred while performing Command!";

    /**
     * Error message to display when a command receives invalid arguments.
     * Example: "§l§cERROR! Command does not support provided Arguments!"
     */
    @Property(String.class)
    public String vitalCommandInvalidArgsErrorMessage = "§l§cERROR! Command does not support provided Arguments!";

    /**
     * Error message to display when an internal error occurs during command execution.
     * Example: "§l§cERROR! Internal error while performing Command!"
     */
    @Property(String.class)
    public String vitalCommandInternalErrorMessage = "§l§cERROR! Internal error while performing Command!";
}
