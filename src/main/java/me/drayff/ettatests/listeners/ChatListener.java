package me.drayff.ettatests.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public class ChatListener implements Listener {
    JavaPlugin plugin;

    public ChatListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDrayffChurka(AsyncPlayerChatEvent event) {
        event.getRecipients().clear();

        Player sender = event.getPlayer();
        String message = event.getMessage();

        boolean isGlobal = message.startsWith("!");

        int localDistance = plugin.getConfig().getInt("chat.local.distance");

        if (isGlobal) {
            if (message.length() > 1) {
                for (Player client : Bukkit.getOnlinePlayers()) {
                    client.sendMessage(getFormat(
                            plugin.getConfig().getString("chat.global.prefix"),
                            sender.getName(),
                            message.substring(1).trim())
                    );
                }
            }
        } else {
            for (Player client : Bukkit.getOnlinePlayers()) {
                if (sender.getWorld().equals(client.getWorld())) {
                    if (sender.getLocation().distanceSquared(client.getLocation()) <= Math.pow(localDistance, 2)) {
                        client.sendMessage(getFormat(
                                plugin.getConfig().getString("chat.local.prefix"),
                                sender.getName(),
                                message.trim())
                        );
                    }
                }
            }

            event.setCancelled(true);
        }
    }

    private String getFormat(String chatPrefix, String nickname, String message) {
        String format = plugin.getConfig().getString("chat.format");

        if (format != null) {
            format = format.replace("{chatPrefix}", chatPrefix);
            format = format.replace("{nickname}", nickname);
            format = format.replace("{message}", message);
        } else {
            plugin.getLogger().log(Level.WARNING, "PIZDEC! PIZDEC! PIZDEC!");
        }

        return format;
    }
}
