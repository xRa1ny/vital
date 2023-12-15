package me.xra1ny.vital.scoreboards;

import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.*;
import java.util.function.Function;

/**
 * Represents a per-player scoreboard that can have individual contents for each player.
 * This class allows you to manage player-specific scoreboards with customizable titles and lines.
 *
 * @author xRa1ny
 */
public final class VitalPerPlayerScoreboard extends VitalScoreboard {
    /**
     * the title of this per player scoreboard
     */
    @Getter
    @NonNull
    private final String title;

    /**
     * the lines of this per player scoreboard
     */
    @Getter
    @NonNull
    private List<Function<Player, String>> lineList;

    /**
     * the scoreboard contents for each member of this per player scoreboard
     */
    @Getter
    @NonNull
    private final Map<Player, VitalScoreboardContent> vitalScoreboardContentMap = new HashMap<>();

    /**
     * Constructs a new per player scoreboard instance with the specified title and lines.
     *
     * @param title    The title to display.
     * @param lineList The {@link Function} that defines the lines each player should see on the scoreboard when it's rendered. (may contain player relevant information and be different for each player).
     */
    @SneakyThrows
    @SafeVarargs
    public VitalPerPlayerScoreboard(@NonNull String title, @NonNull Function<Player, String>... lineList) {
        this.title = title;
        this.lineList = Arrays.asList(lineList);
    }

    /**
     * sets the lines of this per player scoreboard
     *
     * @param lineList the lines
     */
    @SafeVarargs
    public final void setLineList(@NonNull Function<Player, String>... lineList) {
        this.lineList = List.of(lineList);
    }

    /**
     * updates the users specified scoreboard
     *
     * @param player the player
     */
    @SuppressWarnings("DataFlowIssue")
    public void update(@NonNull Player player) {
        if (!this.vitalScoreboardContentMap.containsKey(player)) {
            return;
        }

        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
        updateContent(player);

        final VitalScoreboardContent scoreboard = this.vitalScoreboardContentMap.get(player);

        player.getPlayer().setScoreboard(scoreboard.getBukkitScoreboard());
    }

    @SuppressWarnings({"DataFlowIssue", "deprecation"})
    private void updateContent(@NonNull Player player) {
        if (!this.vitalScoreboardContentMap.containsKey(player)) {
            return;
        }

        final VitalScoreboardContent scoreboard = this.vitalScoreboardContentMap.get(player);

        scoreboard.update();

        final Objective objective = scoreboard.getBukkitScoreboard().getObjective(ChatColor.stripColor(scoreboard.getTitle()));
        final List<String> lines = applyLines(player);

        for (int i = 0; i < lines.size(); i++) {
            final Score score = objective.getScore(lines.get(i) + String.valueOf(ChatColor.RESET).repeat(i));

            score.setScore(lines.size() - i);
        }
    }

    /**
     * adds the player specified to this per player scoreboard
     *
     * @param player the player
     */
    public void add(@NonNull Player player) {
        if (this.vitalScoreboardContentMap.containsKey(player)) {
            return;
        }

        this.vitalScoreboardContentMap.put(player, new VitalScoreboardContent(title));
        update(player);
    }

    /**
     * removes the player specified from this per player scoreboard
     *
     * @param player the player
     */
    @SuppressWarnings("DataFlowIssue")
    public void remove(@NonNull Player player) {
        if (!this.vitalScoreboardContentMap.containsKey(player)) {
            return;
        }

        this.vitalScoreboardContentMap.remove(player);
        player.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @NonNull
    private List<String> applyLines(@NonNull Player player) {
        final List<String> lines = new ArrayList<>();

        for (Function<Player, String> line : this.lineList) {
            lines.add(line.apply(player));
        }

        return lines;
    }
}
