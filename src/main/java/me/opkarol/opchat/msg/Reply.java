package me.opkarol.opchat.msg;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.opkarol.opchat.msg.MainMsg.*;

public class Reply implements CommandExecutor {
    public Reply(PluginController chatSystem){}

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
        Player sender = (Player) Sender;
        if(args.length == 0) {
            sender.sendMessage(ConfigUtils.getString("reply.badUsage"));
            return false;
        }
        Player receiver = lastPlayer.get(sender);
        if (Ignore.ignore.containsKey(receiver)) {
            if (Ignore.ignore.get(receiver).contains(sender)) {
                sender.sendMessage(ConfigUtils.getString("msg.youCantMessagePlayer"));
                return false;
            }
        }
        if(receiver!=null){
            sendRealMessages(args, sender, receiver);
            setLastSender(sender, receiver);
        } else {
            sender.sendMessage(ConfigUtils.getString("reply.lastPersonOffline"));
        }
        return false;
    }
}
