package eu.lafabrick.teammanager;

import eu.lafabrick.teammanager.api.team.Team;
import eu.lafabrick.teammanager.api.team.TeamGenerator;
import org.bukkit.plugin.java.JavaPlugin;


public final class TeamManager extends JavaPlugin {

    private static TeamManager instance;
    /**
     * Default team generator
     */
    private static TeamGenerator teamGenerator;

    @Override
    public void onEnable() {
        instance = this;

        try {
            teamGenerator = new TeamGenerator(Team.class, this);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        getLogger().info("TeamManager has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TeamManager has been disable!");
    }

    public static TeamManager getInstance() {
        return instance;
    }

    public static TeamGenerator getTeamGenerator() {
        return teamGenerator;
    }

    public static void updateTeamGenerator(TeamGenerator teamGenerator) {
        TeamManager.teamGenerator = teamGenerator;
    }
}
