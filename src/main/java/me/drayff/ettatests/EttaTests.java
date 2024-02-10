package me.drayff.ettatests;

import me.drayff.ettatests.commands.DoCommand;
import me.drayff.ettatests.commands.MeCommand;
import me.drayff.ettatests.commands.TryCommand;
import me.drayff.ettatests.listeners.ChatListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class EttaTests extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getCommand("me").setExecutor(new MeCommand(this));
        getCommand("do").setExecutor(new DoCommand(this));
        getCommand("try").setExecutor(new TryCommand(this));

        getLogger().log(Level.INFO, "Plugin launched successfully!");
    }

    @Override
    public void onDisable() {
        getLogger().log(Level.INFO, "The plugin has been successfully disabled!");
    }
}

/* getCommand("mycommand").setExecutor(new MyCommandExecutor());
 * getCommand("mycommand").setTabCompleter(new MyTabCompleter());
 * getServer().getPluginManager().registerEvents(new MyEventListener(), this);
 */