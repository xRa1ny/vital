package me.xra1ny.vital.samples.full;

import me.xra1ny.vital.scoreboards.VitalGlobalScoreboard;
import me.xra1ny.vital.scoreboards.VitalPerPlayerScoreboard;

public interface SampleVitalScoreboard {
    VitalPerPlayerScoreboard PER_PLAYER_SCOREBOARD = new VitalPerPlayerScoreboard(
            "TITLE",
            player -> "line1 " + player.getName(), // With Function<T> we define a Player to String Implementation for Player specific Data.
            player -> "line2 " + player.getName(),
            player -> "line3 " + player.getName()
    );

    VitalGlobalScoreboard GLOBAL_SCOREBOARD = new VitalGlobalScoreboard(
            "TITLE",
            () -> "line1", // With Supplier<T> we define a String implementation.
            () -> "line2",
            () -> "line3"
    );
}
