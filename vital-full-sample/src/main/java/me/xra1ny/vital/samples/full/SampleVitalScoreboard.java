package me.xra1ny.vital.samples.full;

import me.xra1ny.vital.scoreboards.VitalGlobalScoreboard;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboard;

public interface SampleVitalScoreboard {
    VitalPerPlayerScoreboard PER_PLAYER_SCOREBOARD = new VitalPerPlayerScoreboard(
            "TITLE",
            player -> "line1 " + player.getName(),
            player -> "line2 " + player.getName(),
            player -> "line3 " + player.getName()
    );

    VitalGlobalScoreboard GLOBAL_SCOREBOARD = new VitalGlobalScoreboard(
            "TITLE",
            "line1",
            "line2",
            "line3"
    );
}
