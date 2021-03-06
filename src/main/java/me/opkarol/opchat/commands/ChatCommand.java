package me.opkarol.opchat.commands;

import me.opkarol.opchat.MessagesFile;
import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.party.PartyHolder;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor{
    public static boolean chat = true;

    public ChatCommand(PluginController plugin) {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender != null) {
            if (sender.hasPermission("skyisland.chatsystem.admin") || sender.isOp()) {
                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("clear")) {
                        for (int i = 0; i < 101; ++i) {
                            for (Player p2 : Bukkit.getOnlinePlayers()) {
                                p2.sendMessage(" ");
                            }
                        }
                        Bukkit.broadcastMessage(placeHolders(ConfigUtils.getMessage("messages.chat.clear"), sender));
                    } else if (args[0].equalsIgnoreCase("on")) {
                        if (!chat) {
                            chat = true;
                            sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOn.player"), sender));
                            Bukkit.broadcastMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOn.global"), sender));
                            return false;
                        }
                        sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOn.already"), sender));
                        return false;

                    } else if (args[0].equalsIgnoreCase("off")) {
                        if (chat) {
                            chat = false;
                            sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOff.player"), sender));
                            Bukkit.broadcastMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOff.global"), sender));
                            return false;
                        }
                        sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.turnOff.already"), sender));
                        return true;
                    } else if (args[0].equalsIgnoreCase("reload")) {
                        PartyHolder.saveFile();
                        OpChat.getInstance().saveDefaultConfig();
                        OpChat.getInstance().reloadConfig();
                        MessagesFile.saveFile();
                        sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.reloadConfig"), sender));
                    } else if (args[0].equalsIgnoreCase("help")){
                        sendHelpMessage(sender);
                    } else {
                        sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.usage"), sender));
                    }
                } else {
                    sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.usage"), sender));
                }
            } else {
                sender.sendMessage(placeHolders(ConfigUtils.getMessage("messages.chat.withoutPermission"), sender));
            }
        }
        return true;
    }

    public String placeHolders(String message, CommandSender sender){
        return message.replace("$nick$", sender.getName());
    }

    private void sendHelpMessage(CommandSender sender){
        Boolean delayChat = ConfigUtils.getBoolean("delayChat.enabled");
        Integer delayCooldown = ConfigUtils.getInt("delayChat.inSeconds");
        sender.sendMessage("DelayChat enabled: " + delayChat + "\n"
                +"DelayChat cooldown: " + delayCooldown + "\n"
                +"AutoMessages interval: "
                );
    }
}
