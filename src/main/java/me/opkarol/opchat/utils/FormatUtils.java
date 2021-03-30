package me.opkarol.opchat.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class FormatUtils {

    public static String formatText(String toFormat) {
        if(toFormat==null){
            return "Message isnt loaded!";
        }
        return ChatColor.translateAlternateColorCodes('&', toFormat);
    }


    public static List<String> formatList(List<String> lore){
        List<String> Lore = new ArrayList<>();
        for(String s : lore){
            Lore.add(formatText(s));
        }
        return Lore;
    }

    public static String argBuilder(String[] args, Integer removeArgumentsNumber){
        StringBuilder sb = new StringBuilder();
        for (int i = removeArgumentsNumber; i < args.length; i++) {
            sb.append(args[i]).append(" ");
        }
        return formatText(sb.toString().trim());
    }
}
