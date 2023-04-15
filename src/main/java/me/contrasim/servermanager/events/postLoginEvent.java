package me.contrasim.servermanager.events;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.List;

public class postLoginEvent implements Listener
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @EventHandler
    public void onPostLogin(PlayerLoginEvent event)
    {
        Player player = event.getPlayer();
        if (player.hasPermission("servermanager.playerlimit.bypass"))
        {
            List<String> exemptPlayers = plugin.config.getStringList("player-limit-bypass");
            if (!exemptPlayers.contains(player.getUniqueId().toString()))
            {
                exemptPlayers.add(player.getUniqueId().toString());
                plugin.config.set("player-limit-bypass", exemptPlayers);
                plugin.saveConfig();
            }
        }

        if (!player.hasPermission("servermanager.playerlimit.bypass"))
        {
            List<String> exemptPlayers = plugin.config.getStringList("player-limit-bypass");
            if (exemptPlayers.contains(player.getUniqueId().toString()))
            {
                exemptPlayers.remove(player.getUniqueId().toString());
                plugin.config.set("player-limit-bypass", exemptPlayers);
                plugin.saveConfig();
            }
        }

    }

}
