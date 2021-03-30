package me.opkarol.opchat.msg;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.blockWords.blockingWordsClass;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
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
            sender.sendMessage(ConfigUtils.getMessage("reply.badUsage"));
            return false;
        }
        Player receiver = lastPlayer.get(sender);
        if (Ignore.ignore.containsKey(receiver)) {
            if (Ignore.ignore.get(receiver).contains(sender)) {
                sender.sendMessage(ConfigUtils.getMessage("msg.youCantMessagePlayer"));
                return false;
            }
        }

        if (!sender.hasPermission("opchat.blockwords.bypass") || !sender.isOp()) {
            if (blockingWordsClass.hasBlockedWord(FormatUtils.argBuilder(args, 1))) {
                blockingWordsClass.sendPlayerWarning(sender);
                return false;
            }
        }

        if(receiver!=null){
            sendRealMessages(args, sender, receiver);
            setLastSender(sender, receiver);
        } else {
            sender.sendMessage(ConfigUtils.getMessage("reply.lastPersonOffline"));
        }
        return false;
    }
}
