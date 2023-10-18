package me.xra1ny.vital;

import me.xra1ny.vital.players.VitalPlayer;
import me.xra1ny.vital.players.VitalPlayerManager;

public class CustomVitalPlayerManager<T extends VitalPlayer> extends VitalPlayerManager<T> {
    public CustomVitalPlayerManager(int vitalPlayerTimeout) {
        super(vitalPlayerTimeout);
    }
}
