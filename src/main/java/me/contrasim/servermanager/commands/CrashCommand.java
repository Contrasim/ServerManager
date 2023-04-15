package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.Particle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CrashCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

        if (sender.hasPermission("servermanager.crash.use"))
        {

            if (args.length != 1)
            {
                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                        .replace("{command}", label)
                        .replace("{args}", "<player>"));
            }

            if (args.length == 1)
            {
                Player target = plugin.getServer().getPlayer(args[0]);

                if (target != null)
                {
                    double x = target.getLocation().getX();
                    double y = target.getLocation().getY();
                    double z = target.getLocation().getZ();

                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("crash-message")
                            .replace("{player}", args[0]));

                    target.spawnParticle(Particle.CRIT, x, y, z, 10000000);

                }
                else
                {
                    plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("player-not-found"));
                }

            }

        }

        return false;
    }

}
