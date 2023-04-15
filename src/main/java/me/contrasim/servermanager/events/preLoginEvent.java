package me.contrasim.servermanager.events;

import me.contrasim.servermanager.ServerManagerSpigot;
import me.contrasim.servermanager.utils.serverUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class preLoginEvent implements Listener
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent event)
    {

        plugin.serverUtil = new serverUtil();

        if (Bukkit.getOnlinePlayers().size() == plugin.serverUtil.getPlayerLimit())
        {
            if (!plugin.serverUtil.doesPlayerBypassLimit(event.getUniqueId()))
            {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL,
                        ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("server-full-message")));
            }
        }

        if (plugin.serverUtil.isMaintenanceMode())
        {
            if (!plugin.serverUtil.doesPlayerBypassMaintenance(event.getUniqueId()))
            {
                event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST,
                        ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("server-maintenance-message")));
            }
        }

    }

}
