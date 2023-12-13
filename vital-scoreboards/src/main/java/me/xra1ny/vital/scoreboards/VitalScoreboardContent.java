package me.xra1ny.vital.scoreboards;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the content of a scoreboard in the Vital plugin framework.
 * This class manages the scoreboard's title, teams, and updates.
 *
 * @author xRa1ny
 */
final class VitalScoreboardContent {
    /**
     * The title of this scoreboard content.
     */
    @Getter
    @NonNull
    private String title;

    /**
     * The Bukkit scoreboard instance associated with this content.
     */
    @Getter
    @NonNull
    private final Scoreboard bukkitScoreboard = Bukkit.getScoreboardManager().getNewScoreboard();

    /**
     * The scoreboard teams belonging to this content.
     */
    @Getter
    @NonNull
    private final List<VitalScoreboardTeam> teams = new ArrayList<>();

    /**
     * Creates a new VitalScoreboardContent with the specified title.
     *
     * @param title The title of the scoreboard content.
     */
    VitalScoreboardContent(@NonNull String title) {
        this.title = title;
    }

    /**
     * Updates the scoreboard content, including its title and teams.
     */
    @SuppressWarnings("deprecation")
    public void update() {
        Objective objective = this.bukkitScoreboard.getObjective(ChatColor.stripColor(this.title));

        if (objective == null) {
            objective = this.bukkitScoreboard.registerNewObjective(ChatColor.stripColor(this.title), "dummy", this.title);
        }

        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(this.title);

        // Reset scores for existing entries
        for (String entry : this.bukkitScoreboard.getEntries()) {
            this.bukkitScoreboard.resetScores(entry);
        }

        // Update each scoreboard team
        for (VitalScoreboardTeam team : this.teams) {
            team.update();
        }
    }

    /**
     * Sets the title of this scoreboard content and triggers an update.
     *
     * @param title The new title for the scoreboard content.
     */
    public void setTitle(@NonNull String title) {
        this.title = title;
        update();
    }

    /**
     * Adds a scoreboard team to this scoreboard content.
     *
     * @param team The scoreboard team to add.
     */
    public void addTeam(@NonNull VitalScoreboardTeam team) {
        if (this.teams.contains(team)) {
            return;
        }

        this.teams.add(team);
        update();
    }

    /**
     * Removes a scoreboard team from this scoreboard content.
     *
     * @param team The scoreboard team to remove.
     */
    public void removeTeam(@NonNull VitalScoreboardTeam team) {
        if (!this.teams.contains(team)) {
            return;
        }

        this.teams.remove(team);
        update();
    }
}

