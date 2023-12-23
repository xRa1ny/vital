package me.xra1ny.vital.statistics;

import lombok.NonNull;
import me.xra1ny.vital.commands.VitalCommandReturnState;
import me.xra1ny.vital.core.VitalComponent;
import me.xra1ny.vital.core.VitalComponentListManager;
import me.xra1ny.vital.core.VitalCore;
import me.xra1ny.vital.core.VitalSubModuleIdentification;
import me.xra1ny.vital.core.annotation.VitalAutoRegistered;
import me.xra1ny.vital.core.annotation.VitalDI;
import org.bukkit.command.CommandSender;

@VitalDI
@VitalAutoRegistered
public class VitalStatisticsCommandService implements VitalComponent {
    private final VitalCore<?> vitalCore;

    public VitalStatisticsCommandService(VitalCore<?> vitalCore) {
        this.vitalCore = vitalCore;
    }

    @Override
    public void onRegistered() {

    }

    @Override
    public void onUnregistered() {

    }

    public VitalCommandReturnState handleInfo(@NonNull CommandSender sender) {
        sender.sendMessage("--- Vital-Info ---");
        sender.sendMessage("Components registered: " + vitalCore.getVitalComponentList().size());
        sender.sendMessage("Managers   registered: " + vitalCore.getVitalComponentList(VitalComponentListManager.class).size());

        for(VitalSubModuleIdentification vitalSubModuleIdentification : vitalCore.getVitalComponentList(VitalSubModuleIdentification.class)) {
            sender.sendMessage("Using " + vitalSubModuleIdentification.getName());
        }

        return VitalCommandReturnState.SUCCESS;
    }

    public VitalCommandReturnState handleRestart(@NonNull CommandSender sender) {
        return VitalCommandReturnState.SUCCESS;
    }
}
