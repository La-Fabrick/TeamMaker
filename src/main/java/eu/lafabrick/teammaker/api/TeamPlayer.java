package eu.lafabrick.teammaker.api;

import eu.lafabrick.teammaker.TeamMaker;
import eu.lafabrick.teammaker.api.team.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * Player class
 */
public class TeamPlayer {
    @Nullable
    private String name;
    public final UUID uuid;
    private Team team;

    public TeamPlayer(@Nullable String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    @Nullable
    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        team.removePlayer(this);
        this.team.addPlayer(this);
        this.team = team;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public boolean isConnected() {
        return Bukkit.getOnlinePlayers().stream().anyMatch(player -> player.getUniqueId().equals(uuid));
    }

    /**
     * Update the player name
     * @throws IllegalStateException if the player is not connected
     */
    public void updateName() {
        if (!isConnected()) {
            throw new IllegalStateException("Player is not connected");
        }
        name = Bukkit.getPlayer(uuid).getName();
    }

    /**
     * Get the player
     * @return the player
     * @throws IllegalStateException if the player is not connected
     */
    public Player toPlayer() {
        if (!isConnected()) {
            throw new IllegalStateException("Player is not connected");
        }
        return Bukkit.getPlayer(uuid);
    }

    /**
     * Load TeamPlayer from player
     * @param player
     * @return
     */
    public static TeamPlayer fromPlayer(Player player) {
        final var pl = new TeamPlayer(player.getName(), player.getUniqueId());
        final var manager = TeamMaker.getTeamsManager();
        if (manager.hasTeam(player)) {
            pl.setTeam(Objects.requireNonNull(manager.getTeam(player)));
        }
        return pl;
    }
}
