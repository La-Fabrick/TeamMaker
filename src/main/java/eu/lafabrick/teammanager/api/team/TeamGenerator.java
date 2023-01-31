package eu.lafabrick.teammanager.api.team;

import eu.lafabrick.teammanager.TeamManager;
import eu.lafabrick.teammanager.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class TeamGenerator {
    public final JavaPlugin plugin;
    private final Constructor<? extends Team> ctor;

    public TeamGenerator(Class<? extends Team> clazz,JavaPlugin plugin) throws NoSuchMethodException {
        this.plugin = plugin;
        this.ctor = clazz.getConstructor();
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
        final var config = new Config(team.plugin, "teams");
        config.get().set(team.name, team.toInfo());
    }

    /**
     * Delete a team from the config
     * @param team The team to delete
     */
    public static void deleteTeam(Team team) {
        final var config = new Config(team.plugin, "teams");
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
        final var config = new Config(TeamManager.getInstance(), "teams");
        final var teamInfo = (TeamInfo) config.get().getConfigurationSection(name).getValues(false);
        return teamInfo.toTeam(clazz, TeamManager.getInstance());
    }
}
