package me.drayff.ettatests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.logging.Level;

public class DoCommand implements CommandExecutor {
    JavaPlugin plugin;

    public DoCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player sender = (Player) commandSender;
        String message = String.join(" ", strings);

        int localDistance = plugin.getConfig().getInt("commands.do.distance");

        for (Player client : Bukkit.getOnlinePlayers()) {
            if (sender.getWorld().equals(client.getWorld())) {
                if (sender.getLocation().distanceSquared(client.getLocation()) <= Math.pow(localDistance, 2)) {
                    client.sendMessage(getFormat(
                            plugin.getConfig().getString("commands.prefix"),
                            message.trim())
                    );
                }
            }
        }

        return true;
    }

    private String getFormat(String chatPrefix, String message) {
        String format = plugin.getConfig().getString("commands.do.format");

        if (format != null) {
            format = format.replace("{chatPrefix}", chatPrefix);
            format = format.replace("{message}", message);
        } else {
            plugin.getLogger().log(Level.WARNING, "PIZDEC! PIZDEC! PIZDEC!");
        }

        return format;
    }
}
