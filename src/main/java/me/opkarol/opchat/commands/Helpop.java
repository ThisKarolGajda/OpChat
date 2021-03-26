package me.opkarol.opchat.commands;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import static me.opkarol.opchat.utils.FormatUtils.argBuilder;

public class Helpop implements CommandExecutor{
    public Helpop(PluginController pluginController){}

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null) {
            //if (p.hasPermission("skyisland.chatsystem.support") || p.isOp()) {
            if (args.length != 0) {
                for (Player p2 : Bukkit.getOnlinePlayers()){
                    if(p2.hasPermission("skyisland.chatsystem.support.admin") || p2.isOp()){
                        p2.sendMessage(supportMessage(sender, args));
                    }
                }
                Bukkit.getConsoleSender().sendMessage(supportMessage(sender, args));
                if (! sender.hasPermission("skyisland.chatsystem.support.admin") && ! sender.isOp()){
                    sender.sendMessage(supportMessage(sender, args));
                }
                //}
            } else sender.sendMessage("ConfigUtils.getBadUsageMessage()");
        }
        return true;
    }
    public String getPrefix(){
        return ConfigUtils.getString("support.prefix");
    }


    public String supportMessage(CommandSender p, String[] args){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = chat.getPlayerPrefix((Player) p);
        return FormatUtils.formatText(ConfigUtils.getString("support.message").replace("$prefix$", getPrefix()).replace("$nick$", p.getName()).replace("$message$", argBuilder(args, 0)).replace("$playerprefix$", prefix));
    }
}
