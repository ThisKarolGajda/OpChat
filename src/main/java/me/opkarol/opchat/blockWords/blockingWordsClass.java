package me.opkarol.opchat.blockWords;

import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.entity.Player;

public class blockingWordsClass {

    public static boolean hasBlockedWord(String messageToCheck){
        String[] blockedWords = ConfigUtils.getString("blockedWords.words").toLowerCase().split(";");
        for (String blockedWord : blockedWords) {
            messageToCheck = messageToCheck.toLowerCase().replace(" ", "")
                    .replace("-", "").replace(";", "").replace(":", "").replace(".", "").replace(",", "")
                    .replace("/", "").replace("'\'", "").replace("[", "").replace("]", "").replace("@", "")
                    .replace("!", "").replace("#", "").replace("$", "").replace("%", "").replace("^", "")
                    .replace("&", "").replace("*", "").replace("(", "").replace(")", "").replace("?", "");
            if (messageToCheck.contains(blockedWord)) {
                return true;
            }
        }
        return false;
    }

    public static void sendPlayerWarning(Player player){
        player.sendMessage(ConfigUtils.getMessage("blockedWords.warning"));
    }
}
