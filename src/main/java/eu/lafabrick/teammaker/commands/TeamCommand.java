package eu.lafabrick.teammaker.commands;

import eu.lafabrick.teammaker.TeamMaker;
import eu.lafabrick.teammaker.api.TeamPlayer;
import eu.lafabrick.teammaker.api.customCommand.CustomCommand;
import eu.lafabrick.teammaker.api.team.Team;
import eu.lafabrick.teammaker.api.team.TeamGenerator;
import eu.lafabrick.teammaker.utils.Colors;
import eu.lafabrick.teammaker.utils.Config;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

public class TeamCommand extends CustomCommand {
    public final static List<String> subCommands = List.of("create", "delete", "join", "leave", "list", "info");

    public TeamCommand() {
        super("team", "tm.command.team");
    }

    @Override
    protected void helpCommands(Player player) {
        player.sendMessage(Colors.INFO + "Team commands:" +
                "/team create <name> <color> - Create a team" +
                "/team delete <name> - Delete a team" +
                "/team join <name> [player] - Join a team" +
                "/team leave [player] - Leave a team" +
                "/team list - List all teams" +
                "/team info <name> - Get info about a team");
    }

    @Override
    protected boolean execute(Player player, String label, List<String> args) {
        if (args == null) {
            helpCommands(player);
            return true;
        }
        switch (args.get(0)) {
            case "create" -> createTeam(player, args);
            case "delete" -> deleteTeam(player, args);
            case "join" -> joinTeam(player, args);
            case "leave" -> leaveTeam(player, args);
            case "list" -> listTeam(player);
            case "info" -> infoTeam(player, args);
            default -> helpCommands(player);
        }
        return true;
    }

    private void createTeam(Player player, List<String> args) {
        if (args.size() != 3) {
            player.sendMessage(Colors.WARNING + "/team create <name> <color>");
            return;
        }
        final var name = args.get(1);
        ChatColor color;
        try {
            color = ChatColor.valueOf(args.get(2));
        } catch (IllegalArgumentException e) {
            player.sendMessage(Colors.ERROR + "Invalid color");
            return;
        }
        final var size = new Config(TeamMaker.getInstance(), "config").get().getInt("default-prefix-size");
        player.sendMessage(Colors.INFO + "Default prefix size is "+size+" characters");
        player.sendMessage(Colors.INFO + "Prefix:" + TeamGenerator.generatePrefix(name, color, size));
        try {
            TeamMaker.getTeamsManager().getTeamGenerator().generateTeam(name, color, size);
        } catch (Exception e) {
            Colors.errorOccurred(player);
            throw new RuntimeException(e);
        }
        player.sendMessage(Colors.INFO + "Team created");
    }

    private void deleteTeam(Player player, List<String> args) {
        if (args.size() != 2) {
            player.sendMessage(Colors.WARNING + "/team delete <name> ");
            return;
        }
        final var manager = TeamMaker.getTeamsManager();
        final var name = args.get(1);
        final var team = getTeam(name);
        if (team == null) {
            player.sendMessage(Colors.ERROR + "Team not found");
            return;
        }
        try {
            manager.removeTeam(team);
        } catch (Exception e) {
            Colors.errorOccurred(player);
            throw new RuntimeException(e);
        }
        player.sendMessage(Colors.INFO + "Team deleted");
    }

    private void joinTeam(Player player, List<String> args) {
        final var size = args.size();
        if (!(size == 2 || size == 3)) {
            player.sendMessage(Colors.WARNING + "/team join <name> [player]");
            return;
        }
        final var manager = TeamMaker.getTeamsManager();
        final var name = args.get(1);
        final var team = getTeam(name);
        if (team == null) {
            player.sendMessage(Colors.ERROR + "Team not found");
            return;
        }
        var target = player;
        if (size == 3) {
            target = Bukkit.getPlayer(args.get(2));
            if (target == null) {
                player.sendMessage(Colors.ERROR + "Player not found");
                return;
            }
        }
        team.addPlayer(TeamPlayer.fromPlayer(target));
        player.sendMessage(Colors.INFO + target.getName() + " joined the team");
    }

    private void leaveTeam(Player player, List<String> args) {
        final var size = args.size();
        if (!(size == 1 || size == 2)) {
            player.sendMessage(Colors.WARNING + "/team leave [player]");
            return;
        }
        final var manager = TeamMaker.getTeamsManager();
        var target = player;
        if (size == 2) {
            target = Bukkit.getPlayer(args.get(1));
            if (target == null) {
                player.sendMessage(Colors.ERROR + "Player not found");
                return;
            }
        }
        final var team = manager.getTeam(player);
        if (team == null) {
            player.sendMessage(Colors.ERROR + "This player doesn't have a team");
            return;
        }
        team.removePlayer(TeamPlayer.fromPlayer(target));
        player.sendMessage(Colors.INFO + target.getName() + " left the team");
    }

    private void listTeam(Player player) {
        final var manager = TeamMaker.getTeamsManager();
        final var teams = (List<Team>) manager.getTeams();
        if (teams.isEmpty()) {
            player.sendMessage(Colors.INFO + "No team found");
            return;
        }
        player.sendMessage(Colors.INFO + "Teams:");
        for (final var team : teams) {
            player.sendMessage(Colors.INFO + team.name + " - " + team.generatePrefix());
        }
    }

    private void infoTeam(Player player, List<String> args) {
        if (args.size() != 2) {
            player.sendMessage(Colors.WARNING + "/team info <name>");
            return;
        }
        final var name = args.get(1);
        final var team = getTeam(name);
        if (team == null) {
            player.sendMessage(Colors.ERROR + "Team not found");
            return;
        }
        player.sendMessage(Colors.INFO + "Team info:\n" +
                "Name: " + team.name + "\n"+
                "Prefix: " + team.generatePrefix() +"\n"+
                "Players: " + team.getPlayers().stream().map(TeamPlayer::getName).collect(Collectors.joining(", ")));
    }

    @Nullable
    private Team getTeam(String name) {
        return TeamMaker.getTeamsManager().getTeam(name);
    }
}
