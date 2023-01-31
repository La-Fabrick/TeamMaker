package eu.lafabrick.teammaker;

import eu.lafabrick.teammaker.api.team.Team;
import eu.lafabrick.teammaker.api.team.TeamGenerator;
import org.bukkit.plugin.java.JavaPlugin;


public final class TeamMaker extends JavaPlugin {

    private static TeamMaker instance;
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

    public static TeamMaker getInstance() {
        return instance;
    }

    public static TeamGenerator getTeamGenerator() {
        return teamGenerator;
    }

    /**
     * Update the team generator
     * @param teamGenerator The new team generator
     */
    public static void updateTeamGenerator(TeamGenerator teamGenerator) {
        TeamMaker.teamGenerator = teamGenerator;
    }
}
