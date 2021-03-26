package me.opkarol.opchat;

import me.opkarol.opchat.autoMessages.AutoMessagesRunnable;
import me.opkarol.opchat.commands.Broadcast;
import me.opkarol.opchat.commands.ChatCommand;
import me.opkarol.opchat.commands.Helpop;
import me.opkarol.opchat.commands.MeCommand;
import me.opkarol.opchat.customMessages.JoinMessage;
import me.opkarol.opchat.customMessages.LeaveMessage;
import me.opkarol.opchat.events.ChatManagerEvent;
import me.opkarol.opchat.events.CheckingChatEvent;
import me.opkarol.opchat.events.PlayerChatEvent;
import me.opkarol.opchat.listeners.AdminChat;
import me.opkarol.opchat.msg.Ignore;
import me.opkarol.opchat.msg.MainMsg;
import me.opkarol.opchat.msg.Reply;
import me.opkarol.opchat.msg.Spy;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import static me.opkarol.opchat.OpChat.getInstance;

public class PluginController implements Listener {
    public static Chat chat = null;


    public PluginController(OpChat opChat) {
        initalize();
    }

    public void initalize(){
        registerCommands();
        registerEvents();
        saveConfig();
    }

    public void registerCommands(){
        Bukkit.getPluginCommand("broadcast").setExecutor(new Broadcast(this));
        Bukkit.getPluginCommand("chat").setExecutor(new ChatCommand(this));
        Bukkit.getPluginCommand("helpop").setExecutor(new Helpop(this));
        Bukkit.getPluginCommand("me").setExecutor(new MeCommand(this));
        Bukkit.getPluginCommand("ignore").setExecutor(new Ignore(this));
        Bukkit.getPluginCommand("msg").setExecutor(new MainMsg(this));
        Bukkit.getPluginCommand("reply").setExecutor(new Reply(this));
        Bukkit.getPluginCommand("spy").setExecutor(new Spy(this));
        Bukkit.getPluginCommand("adminchat").setExecutor(new AdminChat(this));

    }

    public void registerEvents(){
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new CheckingChatEvent(this), getInstance());
        pluginManager.registerEvents(new PlayerChatEvent(this), getInstance());
        pluginManager.registerEvents(new ChatManagerEvent(this), getInstance());
        pluginManager.registerEvents(new AutoMessagesRunnable(this), getInstance());
        pluginManager.registerEvents(new JoinMessage(this), getInstance());
        pluginManager.registerEvents(new LeaveMessage(this), getInstance());
        pluginManager.registerEvents(new AdminChat(this), getInstance());
    }

    public void saveConfig(){
        OpChat.getInstance().saveDefaultConfig();
    }


}
