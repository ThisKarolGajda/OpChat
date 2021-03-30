package me.opkarol.opchat;

import org.bukkit.plugin.java.JavaPlugin;

public final class OpChat extends JavaPlugin {
    public static OpChat opChat;
    private static PluginController controller;


    @Override
    public void onEnable() {
        opChat = this;
        controller = new PluginController(this);
        int pluginId = 10827; // bStats
        Metrics metrics = new Metrics(this, pluginId);


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static OpChat getInstance(){
        return OpChat.opChat;
    }

    public static PluginController getController(){
        return controller;
    }
}
