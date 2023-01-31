package eu.lafabrick.teammanager.utils;

import org.bukkit.ChatColor;

import javax.annotation.Nullable;

/**
 * Colors of commands
 */
public enum Colors {
    ERROR(ChatColor.RED, null),
    SUCCESS(ChatColor.GREEN, null),
    INFO(ChatColor.BLUE, null),
    WARNING(ChatColor.YELLOW, null),
    FATAL(ChatColor.RED, ChatColor.BOLD);
    private final ChatColor color;
    private final ChatColor plus;
    Colors(ChatColor color, @Nullable ChatColor plus) {
        this.color = color;
        this.plus = plus;
    }
    @Override
    public String toString() {
        return color.toString() + (plus != null ? plus.toString() : "");
    }
}
