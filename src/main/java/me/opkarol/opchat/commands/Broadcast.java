package me.opkarol.opchat.commands;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.opkarol.opchat.utils.FormatUtils.argBuilder;

public class Broadcast implements CommandExecutor {

    public Broadcast(PluginController plugin) { }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null) {
            if (sender.hasPermission("skyisland.chatsystem.broadcast") || sender.isOp()) {
                if (args.length != 0) {
                    Bukkit.broadcastMessage(ConfigUtils.getString("broadcast.prefix") + argBuilder(args, 0));
                } else sender.sendMessage(ConfigUtils.getString("messages.chat.badUsage"));
            } else sender.sendMessage(ConfigUtils.getString("messages.chat.withoutPermission"));
        }
        return true;
    }
}
