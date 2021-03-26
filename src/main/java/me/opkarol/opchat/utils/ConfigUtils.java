package me.opkarol.opchat.utils;

import me.opkarol.opchat.OpChat;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigUtils {

    public static String getString(String path){
        return FormatUtils.formatText(config().getString(path));
    }

    public static Integer getInt(String path){
        return config().getInt(path);
    }

    public static Boolean getBoolean(String path){
        return config().getBoolean(path);
    }

    public static FileConfiguration config(){ return OpChat.opChat.getConfig(); }

}
