package me.opkarol.opchat.events;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ChatManagerEvent implements Listener {

    public ChatManagerEvent(PluginController controller){}
    public String path = "chat.manager.";

    HashMap<Player, String> playerMessages = new HashMap<>();

    /*
    Anti saying the same message twice.
    Creating array list, adding message to list, checking if message isnt in list
     */
    public boolean setPlayerMessages(Player player, String message){
        if(playerMessages.containsKey(player)){
            if(playerMessages.get(player).contains(message)){
                player.sendMessage(ConfigUtils.getString(path+"antiTwiceMessages.message"));
                return true;
            }
        }
        playerMessages.put(player, message);
        new BukkitRunnable(){
            @Override
            public void run(){
                playerMessages.remove(player, message);
            }
        }.runTaskLater(OpChat.getInstance(),ConfigUtils.getInt(path+"antiTwiceMessages.deleteAfterTime")*20);

        return false;
    }
    //TODO to naprawić, bo sprawdza kijowo wiadomości
    @EventHandler(priority = EventPriority.LOWEST)
    public void antiSameMessage(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String playerMessage = event.getMessage();

        //Anti saying this same message
        if(!player.hasPermission(ConfigUtils.getString(path+"antiTwiceMessages.permission")) || !player.isOp()) {
            if (ConfigUtils.getBoolean(path + "antiTwiceMessages.enabled")) {
                if (setPlayerMessages(player, playerMessage)) {
                    event.setCancelled(true);
                }
            }
        }
    }

    /*
    colorFormatting
     */
    @EventHandler
    public void colorFormatting(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        event.setMessage(colorFormatting(player, event.getMessage()));
    }
    public String colorFormatting(Player player, String message){
        if(ConfigUtils.getBoolean(path+"colorFormatting.enabled")) {
            if (player.hasPermission(path + "colorFormatting.permission") || player.isOp()) {
                return FormatUtils.formatText(message);
            }
        }
        return message;
    }


    /*
    unicode Emojis
     */
    @EventHandler(priority = EventPriority.LOW)
    public void unicodeEmojis(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        String message = event.getMessage();
        if(ConfigUtils.getBoolean(path+"unicodeEmojis.enabled")) {
            ConfigurationSection sec = ConfigUtils.config().getConfigurationSection(path + "unicodeEmojis.emojis");
            if (sec != null) {
                for (String key : sec.getKeys(false)) {
                    String emoji = ConfigUtils.getString(path + "unicodeEmojis.emojis." + key);
                    message = message.replace(key, emoji);
                    event.setMessage(message);
                }
            }
        }
    }

    /*
    First big letter
    add . on end
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void periodAndBigLetter(AsyncPlayerChatEvent event){
        Player player = event.getPlayer();
        if(ConfigUtils.getBoolean(path+"periodAndBigLetter.enabled")){
            event.setMessage(periodAndBigLetter(player, event.getMessage()));
        }
    }
    public String periodAndBigLetter(Player player, String message){
        String Message = message.toLowerCase();
        if(player.hasPermission(path+"periodAndBigLetter.permission") || player.isOp()){
            if (Message.length() > 3) {
                if (Message.endsWith(".") || Message.endsWith("?") || Message.endsWith("!")) {
                    return ("" + Message.charAt(0)).toUpperCase() + Message.substring(1);
                } else {
                    return ("" + Message.charAt(0)).toUpperCase() + Message.substring(1) + ".";
                }
            }
        }
        return message;
    }


    /*
    anti write without move on login
    TODO ogarnąć to z połączeniem pluginu na logowanie
     */


    /*
    blokowanie slow
    cooldown na msg
     */
}
