package me.opkarol.opchat.party;

import me.opkarol.opchat.OpChat;
import me.opkarol.opchat.blockWords.blockingWordsClass;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static me.opkarol.opchat.party.PartyHolder.*;

public class PartyUtils {


    public static boolean hasPartyPermission(CommandSender player, String permission){
        return player.hasPermission("opchat.party."+permission);
    }

    public static void sendMessageToParty(CommandSender sender, String message){
        String format;
        format = ConfigUtils.getString("party.format").replace("%player%", sender.getName()).replace("%message%", message);


        for(Player recevier : Bukkit.getOnlinePlayers()){
            if(getPartyInt(recevier).equals(getPartyInt(sender))){
                recevier.sendMessage(format);
            }
        }

    }

    public static boolean isInParty(CommandSender target){
        return partyConfig.get("Players."+target.getName())!=null;
    }

    public static boolean isLeaderParty(CommandSender target){
        int partyId = partyConfig.getInt("Players."+target.getName());
        return Objects.equals(partyConfig.getString("partyList." + partyId + ".leader"), target.getName());
    }

    public static boolean canCreatePartyName(String name){
        return !blockingWordsClass.hasBlockedWord(name) && !name.equals("");
    }

    public static void createParty(CommandSender leader, String partyName) {

        leader.sendMessage(ConfigUtils.getMessage("party.createdParty").replace("%partyName%", partyName));
        int maxParties = ConfigUtils.getInt("party.maxNumberOfParties");
        for (int i = 0; i <= maxParties; i++) {
            if (partyConfig.get("partyList." + i) == null) {
                String path = "partyList." + i + ".";
                setValues(path, partyName, leader);
                setPlayerPartyNumber(i, leader);
                return;
            }
            if(i==maxParties){
                leader.sendMessage(FormatUtils.formatText("&cCONTACT ADMINISTRATORS OF SERVER; ERROR 01;A"));
            }
        }

    }
    private static void setValues(String path, String partyName, CommandSender leader){
        Date date = new Date();
        String createDate = new SimpleDateFormat("yyyy.MM.dd HH:mm").format(date);

        addDefault(path+"name", partyName);
        addDefault(path+"createdTime", createDate);
        addDefault(path+"leader", leader.getName());
        ArrayList<String> members = new ArrayList<>();
        members.add(leader.getName());
        addDefault(path+"members", members);
        saveFile();

    }

    private static void addDefault(String path, Object object) {
        partyConfig.set(path, object);
        partyConfig.addDefault(path, object);

    }

    private static void setPlayerPartyNumber(Integer partyId, CommandSender player){
        String path = "Players.";
        partyConfig.set(path+player.getName(), partyId);
        partyConfig.addDefault(path+player.getName(), partyId);
        saveFile();

    }

    public static void removeSenderFromParty(CommandSender target){
        int partyId = partyConfig.getInt("Players."+target.getName());


        Player leader = Bukkit.getPlayer(partyConfig.getString("partyList."+partyId+".leader"));

        List<String> members = partyConfig.getStringList("partyList."+partyId+".members");


        if(leader==target){
            disbandParty(partyId);
            return;
        }
        partyConfig.set("Players."+target.getName(), null);

        partyConfig.set("partyList."+partyId+".members", members.stream().filter(s -> !s.equals(target.getName())).collect(Collectors.toList()));

        saveFile();
    }



    public static String getPartyInt(CommandSender player){
        partyConfig.getInt("Players." + player.getName());
        return String.valueOf(partyConfig.getInt("Players."+player.getName()));
    }

    public static void askToJoinParty(CommandSender toSend, Integer partyId){
        if(!PartyHolder.isActiveInvite(toSend.getName())) {
            String partyName = partyConfig.getString("partyList." + partyId + ".name");
            assert partyName != null;
            toSend.sendMessage(ConfigUtils.getMessage("party.invite.partyAsk").replace("%partyName%", partyName));
            setActiveInvite(toSend.getName(), partyId);


            new BukkitRunnable(){
                @Override
                public void run(){
                    removeActiveInvite(toSend.getName());
                    toSend.sendMessage(ConfigUtils.getMessage("party.invite.expired"));
                }
            }.runTaskLater(OpChat.getInstance(), 20*60);


        }

    }

    public static void addPlayerToParty(CommandSender toAdd, Integer partyId){
        partyConfig.set("Players."+toAdd.getName(), partyId);
        List<String> list =  partyConfig.getStringList("partyList." + partyId + ".members");
        list.add(toAdd.getName());
        partyConfig.set("partyList." + partyId + ".members", list);
        saveFile();

        for(Player recevier : Bukkit.getOnlinePlayers()){
            if(getPartyInt(recevier).equals(partyId.toString())){
                recevier.sendMessage(ConfigUtils.getMessage("party.addPlayer").replace("%player%", toAdd.getName()));
            }
        }

    }


    public static void disbandParty(Integer partyId){

        for(Player recevier : Bukkit.getOnlinePlayers()){
            if(getPartyInt(recevier).equals(partyId.toString())){
                recevier.sendMessage(ConfigUtils.getMessage("party.disband"));
            }
        }

        String[] strings = partyConfig.getStringList("partyList." + partyId + ".members").toArray(new String[0]);
        for(String string : strings){
            partyConfig.set("Players."+string, null);

        }

        partyConfig.set("partyList."+partyId, null);

        saveFile();
    }


    public static void setLeader(Integer partyId, String newPlayer){
        partyConfig.set("partyList."+partyId+".leader", newPlayer);
        Player player = Bukkit.getPlayer(newPlayer);
        if(player!=null){
            player.sendMessage(ConfigUtils.getMessage("party.newLeader"));
        }

        saveFile();
    }

    public static void membersView(CommandSender toShow, Integer partyId){
        String partyName = partyConfig.getString("partyList."+partyId+".name");
        String leader = partyConfig.getString("partyList."+partyId+".leader");
        String[] members = partyConfig.getStringList("partyList." + partyId + ".members").toArray(new String[0]);

        assert partyName != null;
        toShow.sendMessage(FormatUtils.formatText(ConfigUtils.getMessage("party.view").replace("%partyName%", partyName).replace("%leader%", leader).replace("%members%", Arrays.toString(members))));
    }




}
