package eu.lafabrick.teammaker.api.customCommand;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Custom command manager.
 * Use {@link CustomCommandManager#register(JavaPlugin)} to register all commands.
 * Don't forget to use {@link CustomCommandManager#clear()} after registering.
 */
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

    /**
     * Clear the list
     */
    public static void clear() {
        commands.clear();
    }

    /**
     * Register all commands
     * @param plugin The plugin
     */
    public static void register(JavaPlugin plugin) {
        commands.register(plugin);
    }

    public static CustomCommandList getCommands() {
        return commands;
    }
}
