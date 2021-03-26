package me.opkarol.opchat.events;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static me.opkarol.opchat.commands.ChatCommand.chat;

public class CheckingChatEvent implements Listener {
    public CheckingChatEvent(PluginController plugin) {
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if (!p.hasPermission("skyisland.chatsystem.bypass") && !chat) {
            p.sendMessage(ConfigUtils.getString("messages.chat.cantTalk"));
            e.setCancelled(true);
        }
    }

}
