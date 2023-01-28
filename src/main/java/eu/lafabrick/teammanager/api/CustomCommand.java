package eu.lafabrick.teammanager.api;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CustomCommand implements CommandExecutor {
    public final String name;
    public final String description;
    public final String usage;
    public final String permission;
    public final String[] aliases;

    public CustomCommand(String name, String description, String usage, String permission, String[] aliases) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.permission = permission;
        this.aliases = aliases;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof final Player player)) return false;

        if (args.length == 0 || checks(player)) {
            helpCommands(player);
            return true;
        }

        return execute(player, label, args);
    }

    protected boolean checks(Player player) {
        if (permission.length() == 0) {
            return true;
        }
        return player.hasPermission(permission);
    }

    protected boolean checkCustomPermission(Player player, String permission) {
        return player.hasPermission(permission);
    }

    protected abstract void helpCommands(Player player);

    protected abstract boolean execute(Player player, String label, String[] args);
}
