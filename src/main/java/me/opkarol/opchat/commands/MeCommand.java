package me.opkarol.opchat.commands;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.blockWords.blockingWordsClass;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
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
                    if (!sender.hasPermission("opchat.blockwords.bypass") || !sender.isOp()) {
                        if (blockingWordsClass.hasBlockedWord(mePrefix(sender, args))) {
                            blockingWordsClass.sendPlayerWarning((Player) sender);
                            return false;
                        }
                    }
                    Bukkit.broadcastMessage(mePrefix(sender, args));
                } else sender.sendMessage(ConfigUtils.getString("messages.chat.badUsage"));
            } else sender.sendMessage(ConfigUtils.getString("messages.chat.withoutPermission"));
        }
        return true;
    }

    public String getPrefix(){
        return ConfigUtils.getString("me.prefix");
    }

    private String mePrefix(CommandSender player, String[] args){
        RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        Chat chat = chatProvider.getProvider();
        String prefix = FormatUtils.formatText(chat.getPlayerPrefix((Player) player));
        return Objects.requireNonNull(ConfigUtils.getString("me.message")).replace("$prefix$", getPrefix()).replace("$nick$", player.getName()).replace("$message$", argBuilder(args, 0)).replace("$playerprefix$", prefix);

    }
}
