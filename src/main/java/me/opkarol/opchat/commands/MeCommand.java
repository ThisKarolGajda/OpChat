package me.opkarol.opchat.commands;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.Objects;

import static me.opkarol.opchat.utils.FormatUtils.argBuilder;

public class MeCommand implements CommandExecutor{
    public MeCommand(PluginController plugin) {
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null) {
            if (sender.hasPermission("skyisland.chatsystem.me") || sender.isOp()) {
                if (args.length != 0) {
                    Bukkit.broadcastMessage(mePrefix(sender, args));
                } else sender.sendMessage(ConfigUtils.getString("messages.chat.badUsage"));
            } else sender.sendMessage(ConfigUtils.getString("messages.chat.withoutPermission"));
        }
        return true;
    }

    public String getPrefix(){
        return ConfigUtils.getString("me.prefix");
    }

    private String mePrefix(CommandSender p, String[] args){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = chat.getPlayerPrefix((Player) p);
        return Objects.requireNonNull(ConfigUtils.getString("me.message")).replace("$prefix$", getPrefix()).replace("$nick$", p.getName()).replace("$message$", argBuilder(args, 0)).replace("$playerprefix$", prefix);

    }
}
