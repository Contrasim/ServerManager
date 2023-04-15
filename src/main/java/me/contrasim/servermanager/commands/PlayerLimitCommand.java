package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class PlayerLimitCommand implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.playerlimit.use"))
        {

            if (args.length != 1)
            {
                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                        .replace("{command}", label)
                        .replace("{args}", "<amount>"));
            }
            else
            {
                try {
                    int newLimit = Integer.parseInt(args[0]);
                    plugin.serverUtil.setPlayerLimt(newLimit);
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("player-limit-message")
                            .replace("{limit}", String.valueOf(newLimit)));
                } catch (Exception e) {
                    plugin.messagesUtil.sendColoredMessage(sender, "&cInput must be an integer.");
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
