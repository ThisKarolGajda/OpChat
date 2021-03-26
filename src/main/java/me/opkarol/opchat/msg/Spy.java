package me.opkarol.opchat.msg;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class Spy implements CommandExecutor {
    public Spy(PluginController chatSystem){
    }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
        Player sender = (Player) Sender;
        if(!sender.hasPermission(ConfigUtils.getString("spy.permission"))||!sender.isOp()){
            sender.sendMessage(ConfigUtils.getString("spy.withoutPermission"));
            return false;
        }
        UUID playerUuid = sender.getUniqueId();
        if(SpyHolder.isActiveSpy(playerUuid)){
            SpyHolder.removeActiveSpy(playerUuid);
            sender.sendMessage(ConfigUtils.getString("spy.deactivatedSpy"));
        } else {
            SpyHolder.setActiveSpy(playerUuid);
            sender.sendMessage(ConfigUtils.getString("spy.activatedSpy"));
        }

        return false;
    }

    public static void sendSpyMessages(String message, Player sender, Player receiver){
        String getSpyFormat = ConfigUtils.getString("spy.format");
        String messageToSend = getSpyFormat.replace("%sender%", sender.getName()).replace("%receiver%", receiver.getName()).replace("%message%", message);
        for(UUID playerId : SpyHolder.getActiveSpyList()){
            Player player = Bukkit.getPlayer(playerId);
            player.sendMessage(messageToSend);
        }
    }
}
