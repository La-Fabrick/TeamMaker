package eu.lafabrick.teammaker.api.team;

import eu.lafabrick.teammaker.TeamMaker;
import eu.lafabrick.teammaker.api.TeamPlayer;
import eu.lafabrick.teammaker.utils.Config;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class TeamsManager<T extends Team> {
    private static TeamGenerator teamGenerator;

    private final List<T> teams;

    public TeamsManager(TeamGenerator teamGenerator) {
        TeamsManager.teamGenerator = teamGenerator;
        teams = new ArrayList<>();
        reloadFromConfig(new Config(TeamMaker.getInstance(), "teams"));
    }

    public TeamGenerator getTeamGenerator() {
        return teamGenerator;
    }

    public List<T> getTeams() {
        return teams;
    }

    public void addTeam(T team) {
        teams.add(team);
    }

    public void removeTeam(T team) {
        teams.remove(team);
    }

    public void saveToConfig() {
        for (T team : teams) {
            TeamGenerator.saveTeam(team);
        }
    }

    public boolean hasTeam(TeamPlayer player) {
        for (T team : teams) {
            if (team.getPlayers().contains(player)) return true;
        }
        return false;
    }

    public boolean hasTeam(Player player) {
        return hasTeam(TeamPlayer.fromPlayer(player));
    }

    @Nullable
    public T getTeam(TeamPlayer player) {
        for (T team : teams) {
            if (team.getPlayers().contains(player)) return team;
        }
        return null;
    }

    @Nullable
    public T getTeam(Player player) {
        return getTeam(TeamPlayer.fromPlayer(player));
    }

    public void reloadFromConfig(Config config) {
        teams.clear();
        for (Object o : config.get().getValues(false).values()) {
            if (o.getClass() != TeamInfo.class) throw new RuntimeException("Invalid team info");
            final var info = (TeamInfo) o;

            try {
                final var team = TeamMaker.getTeamsManager().getTeamGenerator().generateTeam(info);
                if (team == null) throw new RuntimeException("Team is null");
                // TODO: Check if team is type of T
                teams.add((T) team);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
