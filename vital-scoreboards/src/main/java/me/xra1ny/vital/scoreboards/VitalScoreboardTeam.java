package me.xra1ny.vital.scoreboards;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * Represents a team within a scoreboard in the Vital plugin framework.
 * This class manages team properties, members, and updates.
 *
 * @author xRa1ny
 */
public final class VitalScoreboardTeam {
    /**
     * The name of this scoreboard team.
     */
    @Getter(onMethod = @__(@NotNull))
    private final String name;

    /**
     * The members of this scoreboard team.
     */
    @Getter(onMethod = @__({@NotNull, @Unmodifiable}))
    private final List<Player> playerList = new ArrayList<>();

    /**
     * The scoreboard content to which this team belongs.
     */
    @Getter(onMethod = @__(@NotNull))
    private final VitalScoreboardContent scoreboard;

    /**
     * The team options for this scoreboard team.
     */
    @Getter(onMethod = @__(@NotNull))
    private final Map<Team.Option, Team.OptionStatus> options = new HashMap<>();

    /**
     * The Bukkit team instance representing this scoreboard team.
     */
    @Getter(onMethod = @__(@NotNull))
    private final Team bukkitTeam;

    /**
     * The prefix of this scoreboard team.
     */
    @Getter(onMethod = @__(@Nullable))
    @Setter(onParam = @__(@NotNull))
    private String prefix;

    /**
     * The suffix of this scoreboard team.
     */
    @Getter(onMethod = @__(@Nullable))
    @Setter(onParam = @__(@NotNull))
    private String suffix;

    /**
     * Whether friendly fire is allowed for this team.
     */
    @Getter
    @Setter
    private boolean friendlyFire;

    /**
     * When true, members of this team can see friendly invisibles.
     */
    @Getter
    @Setter
    private boolean canSeeFriendlyInvisibles;

    /**
     * Creates a new VitalScoreboardTeam with the specified name and associated scoreboard content.
     *
     * @param name      The name of the team.
     * @param scoreboard The scoreboard content to which the team belongs.
     */
    @SuppressWarnings("deprecation")
    VitalScoreboardTeam(@NotNull String name, @NotNull VitalScoreboardContent scoreboard) {
        this.name = name;
        this.scoreboard = scoreboard;
        this.bukkitTeam = scoreboard.getBukkitScoreboard().registerNewTeam(ChatColor.stripColor(name));
    }

    /**
     * Updates the properties and members of this scoreboard team.
     */
    @SuppressWarnings("deprecation")
    public void update() {
        this.bukkitTeam.setDisplayName(this.name);
        this.bukkitTeam.setAllowFriendlyFire(this.friendlyFire);
        this.bukkitTeam.setCanSeeFriendlyInvisibles(this.canSeeFriendlyInvisibles);

        if (this.prefix != null) {
            this.bukkitTeam.setPrefix(this.prefix);
        }

        if (this.suffix != null) {
            this.bukkitTeam.setSuffix(this.suffix);
        }

        // Update all options
        for (Map.Entry<Team.Option, Team.OptionStatus> entry : this.options.entrySet()) {
            final Team.Option option = entry.getKey();
            final Team.OptionStatus status = entry.getValue();

            this.bukkitTeam.setOption(option, status);
        }

        // Clear all members
        final Set<String> entries = this.bukkitTeam.getEntries();

        for (String entry : entries) {
            this.bukkitTeam.removeEntry(entry);
        }

        // Add new members
        for (Player player : this.playerList) {
            this.bukkitTeam.addPlayer(player);
        }
    }

    /**
     * Sets the option of this scoreboard team to the specified status.
     *
     * @param option The team option.
     * @param status The team option status.
     */
    public void setOption(@NotNull Team.Option option, @NotNull Team.OptionStatus status) {
        this.options.put(option, status);
    }

    /**
     * Adds a player to this scoreboard team.
     *
     * @param player The player to add.
     */
    public void addMember(@NotNull Player player) {
        if (this.playerList.contains(player)) {
            return;
        }

        this.playerList.add(player);
        update();
    }

    /**
     * Removes a player from this scoreboard team.
     *
     * @param player The player to remove.
     */
    public void removeMember(@NotNull Player player) {
        if (!this.playerList.contains(player)) {
            return;
        }

        this.playerList.remove(player);
        update();
    }
}

