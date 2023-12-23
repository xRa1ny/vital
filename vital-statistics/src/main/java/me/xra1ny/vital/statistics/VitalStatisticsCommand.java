package me.xra1ny.vital.statistics;


import lombok.NonNull;
import me.xra1ny.vital.commands.VitalCommand;
import me.xra1ny.vital.commands.VitalCommandReturnState;
import me.xra1ny.vital.commands.annotation.VitalCommandArg;
import me.xra1ny.vital.commands.annotation.VitalCommandArgHandler;
import me.xra1ny.vital.commands.annotation.VitalCommandInfo;
import me.xra1ny.vital.core.annotation.VitalDI;
import me.xra1ny.vital.core.annotation.VitalManagerAutoRegistered;
import org.bukkit.command.CommandSender;

@VitalDI
@VitalManagerAutoRegistered
@VitalCommandInfo(
        value = "vital",
        requiresPlayer = false,
        args = {
                @VitalCommandArg("info"),
                @VitalCommandArg("restart")
        }
)
public class VitalStatisticsCommand extends VitalCommand {
    private final VitalStatisticsCommandService vitalStatisticsCommandService;

    public VitalStatisticsCommand(VitalStatisticsCommandService vitalStatisticsCommandService) {
        this.vitalStatisticsCommandService = vitalStatisticsCommandService;
    }

    @Override
    protected @NonNull VitalCommandReturnState executeBaseCommand(@NonNull CommandSender sender) {
        sender.sendMessage("This server is powered by Vital, the fast and versatile framework!");
        sender.sendMessage("For more information check out Vital's GitHub Page! https://github.com/xRa1ny/vital");

        return VitalCommandReturnState.SUCCESS;
    }

    @VitalCommandArgHandler("info")
    public VitalCommandReturnState handleInfo(CommandSender sender) {
        return vitalStatisticsCommandService.handleInfo(sender);
    }

    @VitalCommandArgHandler("restart")
    public VitalCommandReturnState handleRestart(CommandSender sender) {
        return vitalStatisticsCommandService.handleRestart(sender);
    }
}
