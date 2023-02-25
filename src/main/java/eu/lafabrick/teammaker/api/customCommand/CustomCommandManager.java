package eu.lafabrick.teammaker.api.customCommand;

import org.bukkit.plugin.java.JavaPlugin;

public class CustomCommandManager {
    private static CustomCommandList commands = new CustomCommandList();

    public static void add(CustomCommand command) {
        commands.add(command);
    }

    public static void remove(CustomCommand command) {
        commands.remove(command);
    }

    public static boolean has(CustomCommand command) {
        return commands.contains(command);
    }

    public static void register(JavaPlugin plugin) {
        commands.register(plugin);
    }

    public static CustomCommandList getCommands() {
        return commands;
    }
}
