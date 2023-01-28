package eu.lafabrick.teammanager.api.team;

import eu.lafabrick.teammanager.api.TeamPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.UUID;

public class TeamInfo {
    public final String name;
    public final ChatColor color;
    public final String prefix;
    public final UUID[] players;

    public TeamInfo(String name, ChatColor color, String prefix, UUID[] players) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.players = players;
    }

    public Team toTeam(Class<? extends Team> clazz, JavaPlugin plugin) throws Exception {
        final var ctor = clazz.getConstructor(String.class, ChatColor.class, String.class, JavaPlugin.class);
        final var team = ctor.newInstance(name, color, prefix, plugin);
        for (UUID player : players) {
            team.addPlayer(new TeamPlayer(null, player));
        }
        return team;
    }
}
