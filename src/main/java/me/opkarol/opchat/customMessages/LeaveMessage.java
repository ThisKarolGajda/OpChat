package me.opkarol.opchat.customMessages;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LeaveMessage implements Listener {

    public LeaveMessage(PluginController pluginController) {
    }

    @EventHandler
    public void playerJoinEvent(PlayerQuitEvent event){
        Player player = event.getPlayer();

        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = chat.getPlayerPrefix(player);

        String message = ConfigUtils.getString("chat.leaveFormat."+chat.getPrimaryGroup(player));
        message = FormatUtils.formatText(message.replace("%player%", player.getName()).replace("%prefix%", prefix));

        event.setQuitMessage(message);

    }
}
