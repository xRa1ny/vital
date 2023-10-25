package me.xra1ny.vital.samples;

import me.xra1ny.vital.scoreboards.VitalGlobalScoreboard;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboard;

public interface Scoreboard {
    VitalPerPlayerScoreboard PER_PLAYER_SCOREBOARD = new VitalPerPlayerScoreboard(
            "TITLE",
            player -> "line1",
            player -> "line2",
            player -> "line3",
            player -> "line4",
            player -> "line5"
    );

    VitalGlobalScoreboard GLOBAL_SCOREBOARD = new VitalGlobalScoreboard(
            "TITLE",
            "line1",
            "line2",
            "line3",
            "line4",
            "line5"
    );
}
