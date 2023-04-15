package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import me.contrasim.servermanager.utils.playerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class MaintenanceCommand implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        plugin.playerUtil = new playerUtil();

        if (sender.hasPermission("servermanager.maintenance.use"))
        {

            if (args.length == 0 || args.length > 2)
            {
                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                        .replace("{command}", label)
                        .replace("{args}", "<on | off | exempt | unexempt>"));
            }

            if (args.length == 1)
            {

                if (args[0].equalsIgnoreCase("on"))
                {
                    plugin.serverUtil.setMaintenanceMode(true);
                    for (Player online : Bukkit.getOnlinePlayers())
                    {
                        if (!plugin.serverUtil.doesPlayerBypassMaintenance(online.getUniqueId()))
                        {
                            online.kickPlayer(ChatColor.translateAlternateColorCodes('&',
                                    plugin.messages.getString("server-maintenance-message")));
                        }
                    }

                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("maintenance-mode-message")
                            .replace("{mode}", "Enabled"));
                }
                else if (args[0].equalsIgnoreCase("off"))
                {
                    plugin.serverUtil.setMaintenanceMode(false);
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("maintenance-mode-message")
                            .replace("{mode}", "Disabled"));
                }
                else
                {
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                            .replace("{command}", label)
                            .replace("{args}", "<on | off | exempt | unexempt>"));
                }

            }

            if (args.length == 2)
            {

                if (args[0].equalsIgnoreCase("exempt"))
                {

                    String uuid = plugin.playerUtil.getUUID(args[1]);
                    List<String> maintenanceList = plugin.config.getStringList("player-maintenance-bypass");
                    maintenanceList.add(uuid);
                    plugin.config.set("player-maintenance-bypass", maintenanceList);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("maintenance-mode-added")
                            .replace("{player}", args[1]));

                }
                else if (args[0].equalsIgnoreCase("unexempt"))
                {
                    String uuid = plugin.playerUtil.getUUID(args[1]);
                    List<String> maintenanceList = plugin.config.getStringList("player-maintenance-bypass");
                    maintenanceList.remove(uuid);
                    plugin.config.set("player-maintenance-bypass", maintenanceList);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("maintenance-mode-remove")
                            .replace("{player}", args[1]));
                }
                else
                {
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                            .replace("{command}", label)
                            .replace("{args}", "<on | off | exempt | unexempt>"));
                }

            }
        }
        else
        {
            plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("no-permission"));
        }
        return false;
    }

}
