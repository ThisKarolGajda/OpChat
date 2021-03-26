package me.opkarol.opchat.listeners;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

import static java.lang.System.currentTimeMillis;

public class ChatDelay implements Listener {

    HashMap<UUID, Long> delayMap = new HashMap<>();



    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(ConfigUtils.getBoolean("delayChat.enabled")) {
            if (!event.getPlayer().hasPermission("opchat.delay.bypass") || !event.getPlayer().isOp()) {
                UUID id = event.getPlayer().getUniqueId();
                if (!delayMap.containsKey(id)) {
                    delayMap.put(id, currentTimeMillis());
                    return;
                }
                long lastChat = delayMap.get(id);
                long now = currentTimeMillis();
                long diff = now - lastChat;
                if (!(diff >= SecondsDelay() * 1000L)) {
                    event.getPlayer().sendMessage(ConfigUtils.getString("messages.delayChat.delayChatWarn").replace("%secondsDelayChat%", SecondsChatDelay()));
                    event.setCancelled(true);
                }
                delayMap.put(id, currentTimeMillis());
            }
        }
    }
    private int SecondsDelay(){
        return ConfigUtils.getInt("delayChat.inSeconds");
    }

    private String SecondsChatDelay(){
        return ConfigUtils.getString("delayChat.inSeconds").replace("%secondsDelayChat%", String.valueOf(SecondsDelay()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (delayMap.containsKey(id)) {
            delayMap.remove(id);
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        UUID id = event.getPlayer().getUniqueId();
        if (delayMap.containsKey(id)) {
            delayMap.remove(id);
        }
    }
}
