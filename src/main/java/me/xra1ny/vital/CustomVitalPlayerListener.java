package me.xra1ny.vital;

import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerListener;
import me.xra1ny.vital.players.VitalPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class CustomVitalPlayerListener<T extends VitalPlayer> extends VitalPlayerListener<T> {
    private final Class<T> customVitalPlayerClass;

    /**
     * Creates a new instance of VitalPlayerListener.
     *
     * @param javaPlugin         The JavaPlugin instance associated with the listener.
     * @param vitalPlayerManager The VitalUserManagement instance to manage VitalPlayer components.
     */
    public CustomVitalPlayerListener(@NotNull JavaPlugin javaPlugin, @NotNull VitalPlayerManager<T> vitalPlayerManager, @NotNull Class<T> customVitalPlayerClass) {
        super(javaPlugin, vitalPlayerManager);

        this.customVitalPlayerClass = customVitalPlayerClass;
    }

    @Override
    protected Class<T> vitalPlayerType() {
        return customVitalPlayerClass;
    }
}
