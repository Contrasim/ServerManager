package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConsoleCommand implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.consolecommand.use"))
        {

            if (args.length == 0)
            {
                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                        .replace("{command}", label)
                        .replace("{args}", "<command>"));
            }

            if (args.length >= 1)
            {

                StringBuilder commandBuilder = new StringBuilder();
                for (String arg : args)
                {
                    commandBuilder.append(arg).append(" ");
                }
                String consoleCommand = commandBuilder.toString();

                if (args[0].startsWith("/"))
                {
                    plugin.messagesUtil.sendColoredMessage(sender, "&c&lWARNING &7- &cMost console commands do not start with a slash. If the command doesn't work, try running it without the slash");
                }

                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("consolecommand-message")
                        .replace("{command}", consoleCommand));

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), consoleCommand);

            }

        }
        else
        {
            plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("no-permission"));
        }

        return false;
    }

}
