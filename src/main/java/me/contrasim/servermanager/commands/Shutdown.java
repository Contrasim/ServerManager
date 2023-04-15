package me.contrasim.servermanager.commands;

import me.contrasim.servermanager.ServerManagerSpigot;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.scheduler.BukkitRunnable;

public class Shutdown implements CommandExecutor
{

    ServerManagerSpigot plugin = ServerManagerSpigot.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {

        if (sender.hasPermission("servermanager.shutdown.use"))
        {

            if (args.length != 1)
            {
                plugin.messagesUtil.sendColoredMessage(sender, plugin.messages.getString("invalid-arguments")
                        .replace("{command}", label)
                        .replace("{args}", "<time in seconds>"));
            }


            if (args.length == 1)
            {

                try {

                    int time = Integer.parseInt(args[0]);

                for (String shutdownMessage : plugin.messages.getStringList("shutdown-message"))
                {

                    plugin.messagesUtil.sendColoredMessage(sender, shutdownMessage
                            .replace("{time}", String.valueOf(time)));

                }

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        Bukkit.shutdown();
                    }

                }.runTaskLater(plugin, 20L* time);

                new BukkitRunnable()
                {
                    @Override
                    public void run()
                    {
                        for (String shutdownMessage : plugin.messages.getStringList("shutdown-message"))
                        {

                            plugin.messagesUtil.sendColoredMessage(sender, shutdownMessage
                                    .replace("{time}", String.valueOf(3)));
                        }
                    }
                }.runTaskLater(plugin, 20L*time-(3*20L));

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (String shutdownMessage : plugin.messages.getStringList("shutdown-message"))
                            {

                                plugin.messagesUtil.sendColoredMessage(sender, shutdownMessage
                                        .replace("{time}", String.valueOf(2)));

                            }
                        }

                    }.runTaskLater(plugin, 20L*time-(2*20L));

                    new BukkitRunnable()
                    {
                        @Override
                        public void run()
                        {
                            for (String shutdownMessage : plugin.messages.getStringList("shutdown-message"))
                            {

                                plugin.messagesUtil.sendColoredMessage(sender, shutdownMessage
                                        .replace("{time}", String.valueOf(1)));

                            }
                        }

                    }.runTaskLater(plugin, 20L*time-(20L));

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
