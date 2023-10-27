package me.xra1ny.vital.scoreboards;

import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages a global scoreboard displayed to multiple players.
 * This class allows the creation and management of scoreboards that can be displayed to multiple players simultaneously.
 *
 * @author xRa1ny
 */
public final class VitalGlobalScoreboard extends VitalScoreboard {
    /**
     * The scoreboard content of this global scoreboard.
     */
    @Getter(onMethod = @__(@NotNull))
    private final VitalScoreboardContent vitalScoreboardContent;

    /**
     * The lines of this global scoreboard.
     */
    @Getter(onMethod = @__(@NotNull))
    private List<String> lines;

    /**
     * The list of users associated with this global scoreboard.
     */
    @Getter(onMethod = @__({@NotNull, @Unmodifiable}))
    private final List<Player> playerList = new ArrayList<>();

    /**
     * Creates a new instance of the VitalGlobalScoreboard class.
     *
     * @param title The title of the scoreboard.
     * @param lines The lines of the scoreboard.
     */
    @SneakyThrows
    public VitalGlobalScoreboard(@NotNull String title, @NotNull String... lines) {
        this.vitalScoreboardContent = new VitalScoreboardContent(title);
        this.lines = Arrays.asList(lines);
    }

    /**
     * Sets the lines of this global scoreboard.
     *
     * @param lines The lines to set.
     */
    public void setLines(@NotNull String... lines) {
        this.lines = List.of(lines);
    }

    /**
     * Updates the scoreboard for a specific player.
     *
     * @param player The player for whom to update the scoreboard.
     */
    @SuppressWarnings("DataFlowIssue")
    private void update(@NotNull Player player) {
        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        player.getPlayer().setScoreboard(this.vitalScoreboardContent.getBukkitScoreboard());
    }

    /**
     * Updates the scoreboards for all users associated with this global scoreboard.
     */
    public void update() {
        updateContent();

        for (Player player : this.playerList) {
            update(player);
        }
    }

    /**
     * Updates the content of this scoreboard, including titles and scores.
     */
    @SuppressWarnings({"DataFlowIssue", "deprecation"})
    public void updateContent() {
        this.vitalScoreboardContent.update();

        final Objective objective = this.vitalScoreboardContent.getBukkitScoreboard().getObjective(ChatColor.stripColor(this.vitalScoreboardContent.getTitle()));

        for (int i = 0; i < this.lines.size(); i++) {
            final Score score = objective.getScore(this.lines.get(i) + String.valueOf(ChatColor.RESET).repeat(i));

            score.setScore(this.lines.size() - i);
        }
    }

    /**
     * Adds a player to this global scoreboard.
     *
     * @param player The player to add.
     */
    public void addPlayer(@NotNull Player player) {
        if (this.playerList.contains(player)) {
            return;
        }

        this.playerList.add(player);
        update();
    }

    /**
     * Removes a player from this global scoreboard.
     *
     * @param player The player to remove.
     */
    @SuppressWarnings("DataFlowIssue")
    public void removePlayer(@NotNull Player player) {
        if (!this.playerList.contains(player)) {
            return;
        }

        this.playerList.remove(player);
        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());

        update();
    }


}

