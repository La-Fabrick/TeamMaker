package eu.lafabrick.teammaker.api.team;

import eu.lafabrick.teammaker.api.TeamPlayer;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Team base class
 */
public abstract class Team {
    public final String name;
    public final ChatColor color;
    public final String prefix;
    private final List<TeamPlayer> players;
    public final JavaPlugin plugin;

    public Team(String name, ChatColor color, String prefix, JavaPlugin plugin) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.players = new ArrayList<>();
        this.plugin = plugin;
    }

    public TeamInfo toInfo() {
        return new TeamInfo(name, color, prefix, players.stream().map(TeamPlayer::getUuid).toArray(UUID[]::new));
    }

    /**
     * Add a player to the team
     * @param player The player to add
     */
    public abstract void addPlayer(TeamPlayer player);

    /**
     * Remove a player from the team
     * @param player The player to remove
     */
    public abstract void removePlayer(TeamPlayer player);

    /**
     * Generate a string with the team prefix
     * @return the team prefix
     */
    public String generatePrefix() {
        return "["+color+prefix+ChatColor.RESET+"]";
    }

    public List<TeamPlayer> getPlayers() {
        return players;
    }

    public void saveToConfig() {
        TeamGenerator.saveTeam(this);
    }

    public void deleteFromConfig() {
        TeamGenerator.deleteTeam(this);
    }
}
