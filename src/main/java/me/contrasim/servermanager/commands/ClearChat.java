package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ClearChat implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.clearchat.use"))
        {

            for (Player online : Bukkit.getOnlinePlayers())
            {

                if (!online.hasPermission("servermanager.clearchat.bypass"))
                {

                    for (int i = 0; i < 150; i++)
                    {

                        online.sendMessage(ChatColor.translateAlternateColorCodes('&', "&r &r"));

                    }
                }
                online.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.messages.getString("chatclear-message")
                        .replace("{player}", sender.getName())));

            }

        }
        else
        {
            plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("no-permission"));
        }

        return false;
    }

}
