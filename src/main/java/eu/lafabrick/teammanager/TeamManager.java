package eu.lafabrick.teammanager;

import org.bukkit.plugin.java.JavaPlugin;

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
