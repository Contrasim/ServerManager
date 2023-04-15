package me.contrasim.servermanager.events;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class serverPing implements Listener
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @EventHandler
    public void onPing(ServerListPingEvent event)
    {

        event.setMaxPlayers(plugin.config.getInt("player-limit"));

    }

}
