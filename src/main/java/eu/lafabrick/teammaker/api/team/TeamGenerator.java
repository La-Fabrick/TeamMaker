package eu.lafabrick.teammaker.api.team;

import eu.lafabrick.teammaker.TeamMaker;
import eu.lafabrick.teammaker.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class TeamGenerator {
    public final JavaPlugin plugin;
    private final Constructor<? extends Team> ctor;
    private final Class<? extends Team> clazz;

    public TeamGenerator(Class<? extends Team> clazz,JavaPlugin plugin) throws NoSuchMethodException {
        this.plugin = plugin;
        this.ctor = clazz.getConstructor();
        this.clazz = clazz;
    }

    /**
     * Generate a team
     * @param name Name of the team
     * @param color Color of the team
     * @param prefixSize Size of the prefix
     * @return The generated team
     * @throws Exception If the team cannot be generated
     */
    public Team generateTeam(String name, ChatColor color, int prefixSize) throws Exception {
        return ctor.newInstance(name, color, generatePrefix(name, color, prefixSize), plugin);
    }

    public Team generateTeam(TeamInfo info) throws Exception {
        return info.toTeam(clazz, plugin);
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

    /**
     * Save a team into the config
     * @param team The team to save
     */
    public static void saveTeam(Team team) {
        saveTeam(team, new Config(team.plugin, "teams"));
    }

    /**
     * Save a team into the config
     * @param team The team to save
     * @param config Config file
     */
    public static void saveTeam(Team team, Config config) {
        config.get().set(team.name, team.toInfo());
    }

    /**
     * Delete a team from the config
     * @param team The team to delete
     */
    public static void deleteTeam(Team team) {
        deleteTeam(team, new Config(team.plugin, "teams"));
    }

    /**
     * Delete a team from the config
     * @param team The team to delete
     * @param config Config file
     */
    public static void deleteTeam(Team team, Config config) {
        config.get().set(team.name, null);
    }

    /**
     * Load a team from the config
     * @param name Name of the team
     * @param clazz Class of the team
     * @return The loaded team
     * @throws Exception If the team cannot be loaded
     */
    public static Team loadTeam(String name, Class<? extends Team> clazz) throws Exception {
        return loadTeam(name, clazz, new Config(TeamMaker.getInstance(), "teams"));
    }

    /**
     * Load a team from the config
     * @param name Name of the team
     * @param clazz Class of the team
     * @param config Config file
     * @return The loaded team
     * @throws Exception If the team cannot be loaded
     */
    public static Team loadTeam(String name, Class<? extends Team> clazz, Config config) throws Exception {
        final var teamInfo = (TeamInfo) config.get().get(name, TeamInfo.class);
        return teamInfo.toTeam(clazz, TeamMaker.getInstance());
    }
}
