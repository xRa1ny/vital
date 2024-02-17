package me.xra1ny.vital.configs;

import com.google.j2objc.annotations.Property;
import lombok.Data;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

/**
 * Wrapper class to store scoreboard data to a config file.
 *
 * @author xRa1ny
 */
@Data
public class ConfigObjective {
    @Property("criteria")
    private String criteria;

    @Property("slot")
    private DisplaySlot slot;

    @Property("title")
    private String title;

    @Property("lines")
    private String[] lines;

    @NonNull
    public static ConfigObjective of(@NonNull Objective objective) {
        final ConfigObjective configObjective = new ConfigObjective();

        configObjective.criteria = objective.getCriteria();
        configObjective.slot = objective.getDisplaySlot();
        configObjective.title = objective.getName();
        configObjective.lines = objective.getScoreboard().getEntries().toArray(String[]::new);

        return configObjective;
    }

    @NonNull
    public Objective toObjective() {
        final Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        final Objective objective = scoreboard.registerNewObjective(title, criteria, title);

        objective.setDisplaySlot(slot);

        // set lines...
        for (int i = 0; i < lines.length; i++) {
            final String line = lines[i];

            objective.getScore(line).setScore(i);
        }

        return objective;
    }
}
