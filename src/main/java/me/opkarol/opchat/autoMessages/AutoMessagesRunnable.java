package me.opkarol.opchat.autoMessages;

import me.opkarol.opchat.MessagesFile;
import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class AutoMessagesRunnable implements Listener {

    public AutoMessagesRunnable(PluginController plugin) {
        autoMessageStart(messagesFromConfig);
    }
    List<String> messagesFromConfig = MessagesFile.messagesConfig.getStringList("autoMessages.messages");

    public static void autoMessageStart(List<String> messagesFromConfig){
        new BukkitRunnable() {
            int i = 0;
            @Override
            public void run() {
                if (i<messagesFromConfig.size()){
                    Bukkit.broadcastMessage(messagesFromConfig.get(i));
                    i++;
                } else i=0;
            }
        }.runTaskTimerAsynchronously(OpChat.getInstance(), ConfigUtils.getInt("autoMessages.interval")*20, ConfigUtils.getInt("autoMessages.interval")*20);
    }

}
