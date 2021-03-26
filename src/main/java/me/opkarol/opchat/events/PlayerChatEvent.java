package me.opkarol.opchat.events;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PlayerChatEvent implements Listener {

    public PlayerChatEvent(PluginController plugin){}


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        ConfigUtils.getString("chat.groupFormat." + getGroup(player));
        event.setFormat(ConfigUtils.getString("chat.groupFormat."+getGroup(player)).replace("$prefix", getPrefix(event.getPlayer())));
    }

    private String getPrefix(Player p) {
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = chat.getPlayerPrefix((Player) p);
        return FormatUtils.formatText(prefix);
    }

    private String getGroup(Player p){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        return chat.getPrimaryGroup(p);
    }

}
