package eu.lafabrick.teammanager.api.team;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;

public class TeamGenerator {
    public final JavaPlugin plugin;
    private final Constructor<? extends Team> ctor;

    public TeamGenerator(Class<? extends Team> ctor,JavaPlugin plugin) throws NoSuchMethodException {
        this.plugin = plugin;
        this.ctor = ctor.getConstructor();
    }

    public Team generateTeam(String name, ChatColor color, String prefix) throws Exception {
        return ctor.newInstance(name, color, prefix, plugin);
    }
}
