package me.contrasim.servermanager.utils;

import me.contrasim.servermanager.ServerManagerSpigot;

import java.util.UUID;

public class serverUtil
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    public void setMaintenanceMode(boolean mode)
    {
        plugin.config.set("maintenance-mode", mode);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public boolean isMaintenanceMode()
    {
        return plugin.config.getBoolean("maintenance-mode");
    }

    public void setPlayerLimt(int limit)
    {
        plugin.config.set("player-limit", limit);
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public int getPlayerLimit()
    {
        return plugin.config.getInt("player-limit");
    }

    public boolean doesPlayerBypassLimit(UUID uuid)
    {
        return plugin.config.getStringList("player-limit-bypass").contains(uuid.toString());
    }

    public boolean doesPlayerBypassMaintenance(UUID uuid)
    {
        return plugin.config.getStringList("player-maintenance-bypass").contains(uuid.toString());
    }

    public void muteChat()
    {
        if (isChatMuted())
        {
            plugin.config.set("mute-chat", false);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
        else if (!isChatMuted())
        {
            plugin.config.set("mute-chat", true);
            plugin.saveConfig();
            plugin.reloadConfig();
        }
    }

    public boolean isChatMuted()
    {
        return plugin.config.getBoolean("mute-chat");
    }

}
