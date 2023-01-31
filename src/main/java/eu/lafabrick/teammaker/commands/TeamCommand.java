package eu.lafabrick.teammaker.commands;

import eu.lafabrick.teammaker.TeamMaker;
import eu.lafabrick.teammaker.api.CustomCommand;
import eu.lafabrick.teammaker.api.team.TeamGenerator;
import eu.lafabrick.teammaker.utils.Colors;
import eu.lafabrick.teammaker.utils.Config;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

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
                "/team join <name> - Join a team" +
                "/team leave - Leave your team" +
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
            throw new RuntimeException(e);
        }
    }
}
