package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KillServer implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.killserver.use"))
        {

            if (args.length != 1)
            {

                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("kill-server-message"));

            }

            if (args.length == 1)
            {

                if (args[0].equalsIgnoreCase("confirm"))
                {
                    System.exit(0);
                }
                else
                {
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                            .replace("{command}", label)
                            .replace("{args}", "confirm"));
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
