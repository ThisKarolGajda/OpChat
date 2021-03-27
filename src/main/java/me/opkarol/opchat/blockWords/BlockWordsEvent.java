package me.opkarol.opchat.blockWords;

import me.opkarol.opchat.PluginController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class BlockWordsEvent implements Listener {

    public BlockWordsEvent(PluginController pluginController){}

    @EventHandler
    public void playerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        if (!player.hasPermission("opchat.blockwords.bypass") || !player.isOp()) {
            if (blockingWordsClass.hasBlockedWord(message)) {
                event.setCancelled(true);
                blockingWordsClass.sendPlayerWarning(player);
            }
        }
    }
}
