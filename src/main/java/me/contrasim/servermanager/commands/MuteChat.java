package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MuteChat implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.mutechat.use"))
        {

            for (Player online : Bukkit.getOnlinePlayers())
            {

                if (plugin.serverUtil.isChatMuted())
                {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("mutechat-message")
                            .replace("{mode}", "unmuted"))
                            .replace("{player}", sender.getName()));
                }
                else if (!plugin.serverUtil.isChatMuted())
                {
                    online.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("mutechat-message")
                            .replace("{mode}", "muted"))
                            .replace("{player}", sender.getName()));
                }

            }
            plugin.serverUtil.muteChat();

        }
        else
        {
            plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("no-permission"));
        }

        return false;
    }

}
