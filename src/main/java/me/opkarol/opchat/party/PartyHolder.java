package me.opkarol.opchat.party;

import me.opkarol.opchat.OpChat;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class PartyHolder {

    public static final File party = new File(OpChat.opChat.getDataFolder()+"/players/party.yml");
    public static FileConfiguration partyConfig = YamlConfiguration.loadConfiguration(party);

    public static void saveFile(){
        try {
            partyConfig.save(party);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static HashMap<String, Integer> invitedToParty = new HashMap<>();

    public static void setActiveInvite(String playerName, Integer partyId) {
        invitedToParty.put(playerName, partyId);
    }

    public static void removeActiveInvite(String playerName) {
        invitedToParty.remove(playerName);
    }

    public static boolean isActiveInvite(String playerName) {
        return invitedToParty.containsKey(playerName);
    }

    public static Integer getPartyId(String playerName){ return invitedToParty.get(playerName); }

    public static HashMap<String, Integer> getActiveInviteList() {
        return new HashMap<>(invitedToParty);
    }




    private static final Set<CommandSender> activePartyChat = new HashSet<>();

    public static void setActivePartyChat(CommandSender player) {
        activePartyChat.add(player);
    }

    public static void removeActivePartyChat(CommandSender player) {
        activePartyChat.remove(player);
    }

    public static boolean isActivePartyChat(CommandSender player) {
        return activePartyChat.contains(player);
    }

    public static Set<CommandSender> getActivePartyChatList() {
        return new HashSet<>(activePartyChat);
    }


}
