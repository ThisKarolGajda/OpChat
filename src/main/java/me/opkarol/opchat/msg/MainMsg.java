package me.opkarol.opchat.msg;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.blockWords.blockingWordsClass;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class MainMsg implements CommandExecutor {
    public MainMsg(PluginController chatSystem){}
    final static String SendFormat = ConfigUtils.getString("msg.sendFormat");
    final static String ReceiveFormat = ConfigUtils.getString("msg.receiveFormat");

    public static HashMap<Player, Player> lastPlayer = new HashMap<Player, Player>();
    //                    sender  receiver

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
        Player sender = (Player) Sender;
        if(args.length <= 1) {
            sender.sendMessage(ConfigUtils.getString("msg.badUsage"));
            return false;
        }
        Player receiver = Bukkit.getPlayer(args[0]);
        if (Ignore.ignore.containsKey(receiver)) {
            if (Ignore.ignore.get(receiver).contains(sender)) {
                sender.sendMessage(ConfigUtils.getString("msg.youCantMessagePlayer"));
                return false;
            }
        }
        if(sender==receiver && !ConfigUtils.getBoolean("msg.canMessageYourself")){
            sender.sendMessage(ConfigUtils.getString("msg.cannotMsgYourselfMessage"));
            return false;

        }

        if (!sender.hasPermission("opchat.blockwords.bypass") || !sender.isOp()) {
            if (blockingWordsClass.hasBlockedWord(FormatUtils.argBuilder(args, 1))) {
                blockingWordsClass.sendPlayerWarning(sender);
                return false;
            }
        }

        if(receiver != null){
            sendRealMessages(args, sender, receiver);
            setLastSender(sender, receiver);
        } else {
            sender.sendMessage(ConfigUtils.getString("msg.playerOffline"));
        }
        return true;
    }

    public static void setLastSender(Player sender, Player receiver){
        lastPlayer.put(sender, receiver);
        lastPlayer.put(receiver, sender);
    }

    private static void sendMessages(Player sender, Player receiver, String message){
        sender.sendMessage(setSendHolders(SendFormat, sender, receiver, message));
        receiver.sendMessage(setReceiverHolders(ReceiveFormat, sender, receiver, message));
    }

    private static String setSendHolders(String string, Player sender, Player receiver, String message){
        return string.replace("%you%", sender.getDisplayName()).replace("%receiver%", receiver.getDisplayName()).replace("%message%", message);
    }

    private static String setReceiverHolders(String string, Player sender, Player receiver, String message){
        return string.replace("%you%", receiver.getDisplayName()).replace("%sender%", sender.getDisplayName()).replace("%message%", message);
    }

    public static void sendRealMessages(String[] args, Player sender, Player receiver){
        String allArgs = FormatUtils.argBuilder(args, 1);
        sendMessages(sender, receiver, allArgs);
        Spy.sendSpyMessages(allArgs, sender, receiver);
    }
}
