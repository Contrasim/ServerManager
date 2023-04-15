package me.contrasim.servermanager;

import com.google.common.io.ByteStreams;
import me.contrasim.servermanager.commands.*;
import me.contrasim.servermanager.events.chatEvent;
import me.contrasim.servermanager.events.postLoginEvent;
import me.contrasim.servermanager.events.preLoginEvent;
import me.contrasim.servermanager.events.serverPing;
import me.contrasim.servermanager.utils.playerUtil;
import me.contrasim.servermanager.utils.serverUtil;
import me.contrasim.servermanager.utils.messagesUtil;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

public final class ServerManagerSpigot extends JavaPlugin
{

    public Configuration config;
    public Configuration messages;
    public File configFile = new File(getDataFolder(), "config.yml");
    public File messagesFile = new File(getDataFolder(), "messages.yml");
    private static ServerManagerSpigot instance;
    public messagesUtil messagesUtil = new messagesUtil();
    public serverUtil serverUtil;
    public playerUtil playerUtil;

    public void onEnable()
    {
        setInstance(this);
        getLogger().info("Attempting to load configurations...");
        try {
            loadConfig();
        } catch (IOException e) {
            getLogger().severe("Failed to load configuration!");
        }

        Bukkit.getPluginManager().registerEvents(new preLoginEvent(), this);
        Bukkit.getPluginManager().registerEvents(new postLoginEvent(), this);
        Bukkit.getPluginManager().registerEvents(new chatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new serverPing(), this);
        getCommand("playerlimit").setExecutor(new PlayerLimitCommand());
        getCommand("clearchat").setExecutor(new ClearChat());
        getCommand("maintenance").setExecutor(new MaintenanceCommand());
        getCommand("mutechat").setExecutor(new MuteChat());
        getCommand("killserver").setExecutor(new KillServer());
        getCommand("crash").setExecutor(new CrashCommand());
        getCommand("consolecommand").setExecutor(new ConsoleCommand());
        getCommand("shutdown").setExecutor(new Shutdown());
        getLogger().info("The plugin has been successfully loaded and enabled!");
    }

    public void onDisable()
    {
        getLogger().info("The plugin has been successfully unloaded and disabled!");
    }

    public void loadConfig() throws IOException
    {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        if (!this.configFile.exists())
        {
            getLogger().warning("Configuration not found, creating a config new file...");
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream("config.yml");
                OutputStream os = new FileOutputStream(this.configFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create a new configuration file!", e);
            }
        }
        this.config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configFile);
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, this.configFile);
        if (!this.messagesFile.exists())
            try {
                InputStream is = getClass().getClassLoader().getResourceAsStream("messages.yml");
                OutputStream os = new FileOutputStream(this.messagesFile);
                ByteStreams.copy(is, os);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create the messages file!", e);
            }
        this.messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.messagesFile);
        ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.messages, this.messagesFile);
    }

    public void reloadConfig()
    {
        try {

            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.config, this.configFile);

            messages = ConfigurationProvider.getProvider(YamlConfiguration.class).load(this.messagesFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(this.messages, this.messagesFile);

        } catch (IOException e) {
            getLogger().severe("Failed to reload the configuration!");
            e.printStackTrace();
        }
    }

    public void saveConfig()
    {
        try {

            ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, configFile);
            ConfigurationProvider.getProvider(YamlConfiguration.class).save(messages, messagesFile);

        } catch (IOException e) {
            getLogger().severe("Failed to save the configuration!");
            e.printStackTrace();
        }
    }

    public static ServerManagerSpigot getInstance()
    {
        return instance;
    }

    private static void setInstance(ServerManagerSpigot instance)
    {
        ServerManagerSpigot.instance = instance;
    }

}
