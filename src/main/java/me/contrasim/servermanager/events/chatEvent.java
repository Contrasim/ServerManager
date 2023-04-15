package me.contrasim.servermanager.events;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class chatEvent implements Listener
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event)
    {

        Player player = event.getPlayer();

        if (plugin.serverUtil.isChatMuted())
        {
            if (!player.hasPermission("servermanager.mutechat.bypass"))
            {

                event.setCancelled(true);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("mutechat-notify")));

            }

        }

    }

}
