package eu.lafabrick.teammanager.api.team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public abstract class Team {
    public final String name;
    public final ChatColor color;
    public final String prefix;
    public final List<Player> players;
    public final JavaPlugin plugin;

    public Team(String name, ChatColor color, String prefix, JavaPlugin plugin) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.players = new ArrayList<>();
        this.plugin = plugin;
    }

    /**
     * Add a player to the team
     * @param player The player to add
     */
    public abstract void addPlayer(Player player);

    /**
     * Remove a player from the team
     * @param player The player to remove
     */
    public abstract void removePlayer(Player player);

    /**
     * Generate a string with the team prefix
     * @return the team prefix
     */
    public String generatePrefix() {
        return "["+color+prefix+ChatColor.RESET+"]";
    }
}
