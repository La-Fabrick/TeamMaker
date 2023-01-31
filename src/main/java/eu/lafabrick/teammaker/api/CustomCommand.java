package eu.lafabrick.teammaker.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create rapidly a command
 */
public abstract class CustomCommand implements CommandExecutor {
    public final String name;
    public final String permission;

    public CustomCommand(String name, String permission) {
        this.name = name;
        this.permission = permission;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof final Player player)) return false;

        if (args.length == 0 || checks(player)) {
            helpCommands(player);
            return true;
        }

        if (args.length != 1) {
            if (args[1].equals("help")) {
                helpCommands(player);
                return true;
            }
            final var list = new ArrayList<>(Arrays.stream(args).toList());
            list.remove(0);
            return execute(player, label, list);
        }
        return execute(player, label, null);
    }

    /**
     * Do checks before executing the command
     * Automatically executed when the command comes
     * @param player Player who executes the command
     * @return true if the player passed every check
     */
    protected boolean checks(Player player) {
        if (permission.length() == 0 || player.isOp()) {
            return true;
        }
        return player.hasPermission(permission);
    }

    /**
     * Check if the player has a custom permission
     * @param player Player to check
     * @param permission Permission to check
     * @return true if the player has the permission
     */
    protected boolean checkCustomPermission(Player player, String permission) {
        return player.isOp() || player.hasPermission(permission);
    }

    /**
     * Send the help commands to the player
     * @param player Player to send the help commands
     */
    protected abstract void helpCommands(Player player);

    /**
     * Execute the command
     * @param player Player who executes the command
     * @param label Command label
     * @param args Command arguments
     * @return true if the command was executed successfully
     */
    protected abstract boolean execute(Player player, String label, @Nullable List<String> args);
}
