package eu.lafabrick.teammaker.api.team;

import eu.lafabrick.teammaker.api.TeamPlayer;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

/**
 * Class stored in the yaml config
 * @param name The team name
 * @param color The team color
 * @param prefix The team prefix
 * @param players The players in the team
 */
public record TeamInfo(String name, ChatColor color, String prefix, UUID[] players) {
    public Team toTeam(Class<? extends Team> clazz, JavaPlugin plugin) throws Exception {
        final var ctor = clazz.getConstructor(String.class, ChatColor.class, String.class, JavaPlugin.class);
        final var team = ctor.newInstance(name, color, prefix, plugin);
        for (UUID player : players) {
            team.addPlayer(new TeamPlayer(null, player));
        }
        return team;
    }
}
