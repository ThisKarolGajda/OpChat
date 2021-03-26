package me.opkarol.opchat.customMessages;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class JoinMessage implements Listener {

    public JoinMessage(PluginController pluginController) {
    }

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event){
        Player player = event.getPlayer();

        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = chat.getPlayerPrefix((Player) player);

        String message = ConfigUtils.getString("chat.joinFormat."+chat.getPrimaryGroup(player));
        message = FormatUtils.formatText(message.replace("%player%", player.getName()).replace("%prefix%", prefix));

        event.setJoinMessage(message);

    }
}
