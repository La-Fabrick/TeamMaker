package eu.lafabrick.teammaker.api.customCommand;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class CustomCommandList extends ArrayList<CustomCommand> {
    /**
     * Register each command
     * @param plugin The plugin
     */
    public void register(JavaPlugin plugin) {
        for (CustomCommand command : this) {
            command.register(plugin);
        }
    }
}
