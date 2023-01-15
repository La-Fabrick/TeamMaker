package eu.lafabrick.teammanager;

import eu.lafabrick.teammanager.api.team.Team;
import eu.lafabrick.teammanager.api.team.TeamGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

public final class TeamManager extends JavaPlugin {

    private static TeamManager instance;

    @Override
    public void onEnable() {
        instance = this;
        getLogger().info("TeamManager has been enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("TeamManager has been disable!");
    }

    public static TeamManager getInstance() {
        return instance;
    }
}
