package me.xra1ny.vital.samples.command;

import me.xra1ny.vital.commands.*;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@VitalCommandInfo(
        name = "vitalsamplecommand",
        permission = "me.xra1ny.command.vitalsamplecommand",
        requiresPlayer = true,
        args = {
                @VitalCommandArg(
                        value = "sample",
                        permission = "me.xra1ny.command.vitalsamplecommand.sample",
                        player = false
                )
        }
)
public class VitalSampleCommand extends VitalCommand {
    public VitalSampleCommand(@NotNull JavaPlugin javaPlugin) {
        super(javaPlugin);
    }

    @VitalCommandArgHandler("sample")
    public VitalCommandReturnState handleSampleArg(@NotNull CommandSender sender, @NotNull VitalCommandArg vitalCommandArg, @NotNull String[] values) {
        // Here we define any logic for when `/vitalsamplecommand sample` is executed

        return VitalCommandReturnState.SUCCESS;
    }
    
    @Override
    protected @NotNull VitalCommandReturnState executeBaseCommand(@NotNull CommandSender sender) {
        // Logic on base command execution.

        return VitalCommandReturnState.SUCCESS;
    }

    @Override
    public @NotNull List<String> onCommandTabComplete(@NotNull CommandSender sender, @NotNull String args) {
        // Logic for custom Tab Completion if not already satisfied with built-in Tab Completion.

        return List.of();
    }

    @Override
    protected void onCommandInvalidArgs(@NotNull CommandSender sender) {
        // Logic for when this command is run with invalid args.
    }

    @Override
    protected void onCommandInternalError(@NotNull CommandSender sender) {
        // Logic for when this command is run and is returned with `VitalCommandReturnState.INTERNAL_ERROR`.
    }

    @Override
    protected void onCommandError(@NotNull CommandSender sender) {
        // Logic for when this command is run and is returned with `VitalCommandReturnState.ERROR`.
    }

    @Override
    protected void onCommandRequiresPermission(@NotNull CommandSender sender) {
        // Logic for when this command is run by an entity without the required permissions.
    }

    @Override
    protected void onCommandRequiresPlayer(@NotNull CommandSender sender) {
        // Logic for when this command is run by an entity that is not of type Player.
    }

    @Override
    public Class<VitalCommandInfo> requiredAnnotationType() {
        // Currently still has to be implemented.

        return VitalCommandInfo.class;
    }
}
