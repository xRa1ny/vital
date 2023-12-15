package me.xra1ny.vital.samples.full.command;

import lombok.NonNull;
import me.xra1ny.vital.commands.*;
import me.xra1ny.vital.commands.annotation.VitalCommandArg;
import me.xra1ny.vital.commands.annotation.VitalCommandArgHandler;
import me.xra1ny.vital.commands.annotation.VitalCommandInfo;
import org.bukkit.command.CommandSender;

import java.util.List;

// Here we specify the command meta information.
@SuppressWarnings({"unused", "DefaultAnnotationParam"})
@VitalCommandInfo(
        value = "samplevitalcommand",
        permission = "me.xra1ny.command.samplevitalcommand",
        requiresPlayer = true,
        args = {
                // Here we define our command args.
                @VitalCommandArg(value = "arg1", permission = "vital.command.samplevitalcommand.arg1", player = false),
                @VitalCommandArg(value = "arg1 arg1", permission = "vital.command.samplevitalcommand.arg1.arg1", player = true),
                @VitalCommandArg(value = "arg2", permission = "vital.command.samplevitalcommand.arg2", player = false),

                // Here we define an argument with takes in a player.
                @VitalCommandArg(value = "arg2 " + VitalCommandArg.PLAYER, permission = "vital.command.samplevitalcommand.arg2.player", player = true)
        }
)
public final class SampleVitalCommand extends VitalCommand {
    @Override
    protected @NonNull VitalCommandReturnState executeBaseCommand(@NonNull CommandSender sender) {
        // Here we define any logic that gets executed when we run `/samplevitalcommand`
        sender.sendMessage("executed base command /samplevitalcommand");

        return VitalCommandReturnState.SUCCESS;
    }

    /**
     * Here we specify any logic for when `/samplevitalcommand arg1` is executed.
     * <p>
     * WE MUST ALWAYS SPECIFY THE ARGUMENTS AS SHOWN IN THIS EXAMPLE OR ELSE VITAL COMMANDS WILL NOT WORK!!!
     */
    @VitalCommandArgHandler("arg1")
    public VitalCommandReturnState handleArg1(@NonNull CommandSender sender, @NonNull VitalCommandArg vitalCommandArg, @NonNull String[] values) {
        sender.sendMessage("arg1 executed");

        return VitalCommandReturnState.SUCCESS;
    }

    /**
     * This method is called when we execute `/samplevitalcommand arg1 arg1`
     */
    @VitalCommandArgHandler("arg1 arg1")
    public VitalCommandReturnState handleArg1Arg1(@NonNull CommandSender sender, @NonNull VitalCommandArg vitalCommandArg, @NonNull String[] values) {
        sender.sendMessage("arg1 arg1 executed");

        return VitalCommandReturnState.SUCCESS;
    }

    /**
     * This method is called when we execute `/samplevitalcommand arg2`
     */
    @VitalCommandArgHandler("arg2")
    public VitalCommandReturnState handleArg2(@NonNull CommandSender sender, @NonNull VitalCommandArg vitalCommandArg, @NonNull String[] values) {
        sender.sendMessage("arg2 executed");

        return VitalCommandReturnState.SUCCESS;
    }

    /**
     * This method is called when we execute `/samplevitalcommand arg2 [PLAYERNAME]` [PLAYERNAME] stands for any input.
     */
    @VitalCommandArgHandler("arg2 " + VitalCommandArg.PLAYER) // The same as `arg2 %PLAYER%`
    public VitalCommandReturnState handleArg2Player(@NonNull CommandSender sender, @NonNull VitalCommandArg vitalCommandArg, @NonNull String[] values) {
        sender.sendMessage("arg2 " + values[0] + " executed");

        return VitalCommandReturnState.SUCCESS;
    }

    @Override
    public @NonNull List<String> onCommandTabComplete(@NonNull CommandSender sender, @NonNull String args) {
        // Here we specify any tab completion logic if the auto tab completion of Vital is not enough for you.

        return List.of();
    }

    @Override
    protected void onCommandInvalidArgs(@NonNull CommandSender sender) {
        // Here we specify any logic for when this command is executed with invalid args.
        sender.sendMessage("Invalid arguments!");
    }

    @Override
    protected void onCommandInternalError(@NonNull CommandSender sender) {
        // Here we specify any logic for when this command is executed and returned an internal error.
        sender.sendMessage("Internal error while executing command!");
    }

    @Override
    protected void onCommandError(@NonNull CommandSender sender) {
        // Here we specify any logic for when this command is executed and returned an error.
        sender.sendMessage("Error while executing command!");
    }

    @Override
    protected void onCommandRequiresPermission(@NonNull CommandSender sender) {
        // Here we specify any logic for when this command is executed without the needed permissions.
        sender.sendMessage("You don't have enough permission to execute this command!");
    }

    @Override
    protected void onCommandRequiresPlayer(@NonNull CommandSender sender) {
        // Here we specify any logic for when this command is executed by a non player if our command is to be only run by a player.
        sender.sendMessage("This command requires a player!");
    }
}
