package eu.lafabrick.teammaker;

import eu.lafabrick.teammaker.api.team.Team;
import eu.lafabrick.teammaker.api.team.TeamGenerator;
import eu.lafabrick.teammaker.api.team.TeamsManager;
import org.bukkit.plugin.java.JavaPlugin;


public final class TeamMaker extends JavaPlugin {

    private static TeamMaker instance;
    /**
     * Default team generator
     */
    private static TeamsManager teamsManager;

    @Override
    public void onEnable() {
        instance = this;

        try {
            teamsManager = new TeamsManager(new TeamGenerator(Team.class, this));
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        getLogger().info("TeamManager has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TeamManager has been disable!");
    }

    public static TeamMaker getInstance() {
        return instance;
    }

    public static TeamsManager getTeamsManager() {
        return teamsManager;
    }

    /**
     * Update the teams manager
     * @param teamsManager The new teams manager
     */
    public static void updateTeamsManager(TeamsManager teamsManager) {
        TeamMaker.teamsManager = teamsManager;
    }
}
