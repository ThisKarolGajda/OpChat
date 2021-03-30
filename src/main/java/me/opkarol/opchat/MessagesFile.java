package me.opkarol.opchat;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.ArrayList;

public class MessagesFile {

    public static final File messages = new File(OpChat.opChat.getDataFolder()+"/messages.yml");
    public static FileConfiguration messagesConfig = YamlConfiguration.loadConfiguration(messages);

    public static void saveFile(){
        saveDefaults();
    }

    public static void saveDefaults(){
        if(messages.exists()) return;

        //AutoMessages
        ArrayList<String> stringList = new ArrayList<>();
        stringList.add("This is a message1");
        stringList.add("This is a message2");
        stringList.add("This is a message2");
        addDefault("autoMessages.messages", stringList);

        //Chat
        addDefault("messages.chat.clear", "&8» &7Chat was cleared by &f&l$nick$");
        addDefault("messages.chat.turnOn.global", "&8» &7Chat was turned on by &f&l$nick$!");
        addDefault("messages.chat.turnOn.player","&8» &7You turn on chat!");
        addDefault("messages.chat.turnOn.already","&8» &cChat is already turned on!");
        addDefault("messages.chat.turnOff.global", "&8» &7Chat was turned off by &f&l$nick$!");
        addDefault("messages.chat.turnOff.player","&8» &7You turn off chat!");
        addDefault("messages.chat.turnOff.already","&8» &cChat is already turned off!");
        addDefault("messages.chat.reloadConfig", "&8» &7Config reloaded!");
        addDefault("messages.chat.cantTalk", "&8» &cYou cant write beacause &lchat is turned off&c!");
        addDefault("messages.chat.usage", "&8» &7/chat <&f&oclear&7, &f&ooff&7, &f&oon&7, &f&oreload&7, &f&ohelp&7>");
        addDefault("messages.chat.withoutPermission", "&8» &cYou do not have permission to use this command!");
        addDefault("messages.chat.badUsage", "&8» &cBad usage; &7/chat <&f&oclear&7, &f&ooff&7, &f&oon&7, &f&oreload&7>");

        //DelayChat
        addDefault("delayChat.delayChatWarn", "&8» &cYou can write every &f&l%secondsDelayChat% &cseconds!");

        //Broadcast
        addDefault("broadcast.prefix", "&8[&6&lBroadcast&8]&r ");

        //blockedWords
        addDefault("blockedWords.warning", "&8» &cDo not use this word!");

        //support
        addDefault("support.prefix", "&8[&b&lHelpop&8]&r");
        addDefault("support.message", "$prefix$$playerprefix$ &f&l$nick$&f: $message$");

        //me
        addDefault("me.prefix", "&c&lME");
        addDefault("me.message", "$prefix$$playerprefix$ &f&l$nick$&f: $message$");

        //msg
        addDefault("msg.receiveFormat", "&6[&8%sender%&6] &7→ &6[&8Ty&6]&7: &f%message%");
        addDefault("msg.sendFormat", "&6[&8Ty&6] &7→ &6[&8%receiver%&6]&7: &f%message%");
        addDefault("msg.cannotMsgYourselfMessage", "&8» &cYou can not write to yourself!");
        addDefault("msg.youCantMessagePlayer", "&8» &cYou can not write messages to this player!");
        addDefault("msg.badUsage", "&8» &cBad usage; &7/msg &oplayer message&7");
        addDefault("msg.playerOffline", "&8» &cPlayer is offline!");


        //reply
        addDefault("reply.badUsage", "&8» &cBad usage; &7/reply &omessage&7");
        addDefault("reply.lastPersonOffline", "&8» &cLast person who talk with you, is offline!");

        //ignore
        addDefault("ignore.added", "&8» &7Added &f&l%player% &7to ignored persons");
        addDefault("ignore.removed", "&8» &7Removed &f&l%player% &7from ignored persons!");
        addDefault("ignore.isntOnline", "&8» &cPlayer is offline!");
        addDefault("ignore.badUsage", "&8» &cBad usage; &7/ignore &oplayer&7");
        addDefault("ignore.cannotYourself", "&8» &cCannot ignore yourself!");

        //spy
        addDefault("spy.format", "&6[&cSpy&6] &6[&8%sender%&6] &7→ &6[&8%receiver%&6]&7: &f%message%");
        addDefault("spy.withoutPermission", "&8» &cYou do not have permission!");
        addDefault("spy.activatedSpy", "&8» &7Activated Spy mode!");
        addDefault("spy.deactivatedSpy", "&8» &7Deactivated Spy mode!");


        //admin Chat
        addDefault("adminChat.messageFormat", "&6[AC&6] &7- &f&l%player%&7: &f%message%");
        addDefault("adminChat.messageToggle", "&8» &7Toggled Admin Chat");
        addDefault("adminChat.badUsage", "&8» &cBad usage!");


        //chat manager
        addDefault("chat.manager.antiTwiceMessages.message", "&8» &cYou can not write the same message twice!");

        //party
        addDefault("party.usage", "&8» &7/party");
        addDefault("party.createdParty", "&8» &7Created party: &f&l%partyName%");
        addDefault("party.inParty", "&8» &cYou are in party!");
        addDefault("party.notInParty", "&8» &cYou are not in party!");
        addDefault("party.addPlayer", "&8» &7Invited &f&l%player% &7to this party!");
        addDefault("party.disband", "&8» &c&lParty has been Disbanded!");
        addDefault("party.newLeader", "&8» &7You are a new party Leader!");
        addDefault("party.view", "&8» &7Party name: &f&l%partyName%\n" +
                                             "&8» &7Leader: &f&l%leader% \n" +
                                             "&8» &7Members: &f&l%members%");
        addDefault("party.invite.expired", "&8» &7The invitation has expired!");
        addDefault("party.invite.partyAsk", "&8» &7Party: &f&l%partyName%&7, invite you! Write /party accept to join this party!");
        addDefault("party.invite.accept", "&8» &7You accepted the invite!");
        addDefault("party.toggle.on", "&8» &7Turned on sending message to party!");
        addDefault("party.toggle.off", "&8» &7Turned off sending message to party!");
        addDefault("party.notLeader", "&8» &cYou are not a party Leader!");
        addDefault("party.withoutPermission", "&8» &cYou do not have permission to use this command!");
        addDefault("party.playerOffline", "&8» &cThis player is offline!");
        addDefault("party.inOtherParty", "&8» &cThis player isn't in your party!");
        addDefault("party.innactiveInvite", "&8» &cYou do not have active intite to party!");
        addDefault("party.notAllowedName", "&8» &cThis name of party isn't allowed!");
        addDefault("party.cannotInviteYourself", "&8» &cYou can not invite yourself!");



        try {
            messagesConfig.save(messages);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addDefault(String path, Object object) {
        messagesConfig.set(path, object);
        messagesConfig.addDefault(path, object);

    }

}
