package eu.lafabrick.teammanager.api.team;

import eu.lafabrick.teammanager.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class TeamGenerator {
    public final JavaPlugin plugin;
    private final Constructor<? extends Team> ctor;

    public TeamGenerator(Class<? extends Team> ctor,JavaPlugin plugin) throws NoSuchMethodException {
        this.plugin = plugin;
        this.ctor = ctor.getConstructor();
    }

    public Team generateTeam(String name, ChatColor color, int prefixSize) throws Exception {
        return ctor.newInstance(name, color, generatePrefix(name, color, prefixSize), plugin);
    }

    /**
     * Generate a prefix from a team name
     * @param color Color of the prefix
     * @param name Name of the team
     * @param size Size of the prefix
     * @return The prefix
     */
    public static String generatePrefix(String name, ChatColor color, int size) {
        final var prefix = name.substring(0, Math.min(size, name.length()));
        return "["+color+prefix+ChatColor.RESET+"]";
    }
}
