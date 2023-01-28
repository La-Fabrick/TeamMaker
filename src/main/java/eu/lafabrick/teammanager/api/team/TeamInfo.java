package eu.lafabrick.teammanager.api.team;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class TeamInfo {
    public final String name;
    public final ChatColor color;
    public final String prefix;
    public final String[] players;

    public TeamInfo(String name, ChatColor color, String prefix, String[] players) {
        this.name = name;
        this.color = color;
        this.prefix = prefix;
        this.players = players;
    }
}
