package me.opkarol.opchat.listeners;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.UUID;

public class AdminChat implements Listener, CommandExecutor {

    public AdminChat(PluginController pluginController){}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(ConfigUtils.getBoolean("adminChat.enabled")) {

            if (args.length == 0) {
                sender.sendMessage(ConfigUtils.getMessage("adminChat.badUsage"));
                return false;
            }
            if (args[0].equalsIgnoreCase("toggle")) {
                Player player = (Player) sender;
                UUID playerId = player.getUniqueId();
                if (AdminChatHolder.isActiveAC(playerId)) {
                    sender.sendMessage(ConfigUtils.getMessage("adminChat.messageToggle"));
                    AdminChatHolder.removeActiveAC(playerId);
                } else {
                    AdminChatHolder.setActiveAC(playerId);
                    sender.sendMessage(ConfigUtils.getMessage("adminChat.messageToggle"));
                }
                return false;
            }
            if (sender.hasPermission("opchat.adminchat") || sender.isOp()) {
                String message = FormatUtils.argBuilder(args, 0);
                sendMessage(message, sender);
            }
        }
        return false;
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        if(AdminChatHolder.isActiveAC(player.getUniqueId())){
            sendMessage(message, player);
            event.setCancelled(true);
        }

    }


    public void sendMessage(String message, CommandSender sender){
        for(Player player : Bukkit.getOnlinePlayers()){
            if (player.hasPermission("opchat.adminchat") || player.isOp()) {
                String format = ConfigUtils.getMessage("adminChat.messageFormat");
                String messageToSend = format.replace("%player%", sender.getName()).replace("%message%", message);
                player.sendMessage(FormatUtils.formatText(messageToSend));
            }
        }
    }
}
