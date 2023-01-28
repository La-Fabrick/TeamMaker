package eu.lafabrick.teammanager.commands;

import eu.lafabrick.teammanager.TeamManager;
import eu.lafabrick.teammanager.api.CustomCommand;
import eu.lafabrick.teammanager.api.team.TeamGenerator;
import eu.lafabrick.teammanager.utils.Colors;
import eu.lafabrick.teammanager.utils.Config;
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
        final var size = new Config(TeamManager.getInstance(), "config").get().getInt("default-prefix-size");
        player.sendMessage(Colors.INFO + "Default prefix size is "+size+" characters");
        player.sendMessage(Colors.INFO + "Prefix:" + TeamGenerator.generatePrefix(name, color, size));
        try {
            TeamManager.getTeamGenerator().generateTeam(name, color, size);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
