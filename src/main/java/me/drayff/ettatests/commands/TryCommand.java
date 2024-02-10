package me.drayff.ettatests.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;

public class TryCommand implements CommandExecutor {
    JavaPlugin plugin;

    public TryCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player sender = (Player) commandSender;
        String message = String.join(" ", strings);

        int localDistance = plugin.getConfig().getInt("commands.try.distance");

        Random random = new Random();

        for (Player client : Bukkit.getOnlinePlayers()) {
            if (sender.getWorld().equals(client.getWorld())) {
                if (sender.getLocation().distanceSquared(client.getLocation()) <= Math.pow(localDistance, 2)) {
                    client.sendMessage(getFormat(
                            plugin.getConfig().getString("commands.prefix"),
                            sender.getName(),
                            message.trim(),
                            random.nextBoolean())
                    );
                }
            }
        }

        return true;
    }

    private String getFormat(String chatPrefix, String nickname, String message, Boolean result) {
        String format = plugin.getConfig().getString("commands.try.format");

        String trueResult = plugin.getConfig().getString("commands.try.results.true");
        String falseResult = plugin.getConfig().getString("commands.try.results.false");

        if (format != null) {
            format = format.replace("{chatPrefix}", chatPrefix);
            format = format.replace("{nickname}", nickname);
            format = format.replace("{message}", message);
            format = format.replace("{result}", result?trueResult:falseResult);
        } else {
            plugin.getLogger().log(Level.WARNING, "PIZDEC! PIZDEC! PIZDEC!");
        }

        return format;
    }
}
