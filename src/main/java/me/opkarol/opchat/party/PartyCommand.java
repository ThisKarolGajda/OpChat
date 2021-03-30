package me.opkarol.opchat.party;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import me.opkarol.opchat.utils.FormatUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.opkarol.opchat.party.PartyUtils.*;

public class PartyCommand implements CommandExecutor {

    public PartyCommand(PluginController controller){
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission("opchat.party") || sender.isOp()) {
            if(args.length==0){
                sender.sendMessage(ConfigUtils.getMessage("party.usage"));
                return false;
            }

            if (args[0].equalsIgnoreCase("create")) {
                if (PartyUtils.hasPartyPermission(sender, "create")) {
                    if (!PartyUtils.isInParty(sender)) {
                        String partyName = FormatUtils.argBuilder(args, 1);
                        if (PartyUtils.canCreatePartyName(partyName)) {
                            PartyUtils.createParty(sender, partyName);
                        } else sender.sendMessage(ConfigUtils.getMessage("party.notAllowedName"));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.inParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

             else if (args[0].equalsIgnoreCase("invite")) {
                if (PartyUtils.hasPartyPermission(sender, "invite")) {
                    CommandSender invited = Bukkit.getPlayer(args[1]);
                    if (isLeaderParty(sender)) {
                        if(sender!=invited) {
                            askToJoinParty(invited, Integer.valueOf(getPartyInt(sender)));
                        } else sender.sendMessage(ConfigUtils.getMessage("party.cannotInviteYourself"));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.notLeader"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("leader")) {
                if (PartyUtils.hasPartyPermission(sender, "leader")) {
                    if (isInParty(sender)) {
                        if (isLeaderParty(sender)) {
                            CommandSender player = Bukkit.getPlayer(args[1]);
                            if (getPartyInt(player).equals(getPartyInt(sender))) {
                                setLeader(Integer.valueOf(getPartyInt(sender)), player.getName());
                            }
                        } else sender.sendMessage(ConfigUtils.getMessage("party.notLeader"));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.inParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("kick")) {
                if (PartyUtils.hasPartyPermission(sender, "kick")) {
                    if (isInParty(sender)) {
                        Player toKick = Bukkit.getPlayer(args[1]);
                        if (toKick != null) {
                            if (isLeaderParty(sender)) {
                                if (getPartyInt(sender).equals(getPartyInt(toKick))) {
                                    removeSenderFromParty(toKick);
                                } else sender.sendMessage(ConfigUtils.getMessage("party.inOtherParty"));
                            } else sender.sendMessage(ConfigUtils.getMessage("party.notLeader"));
                        } else sender.sendMessage(ConfigUtils.getMessage("party.playerOffline"));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.notInParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("members")) {
                if (PartyUtils.hasPartyPermission(sender, "members")) {
                    if (isInParty(sender)) {
                        membersView(sender, Integer.valueOf(getPartyInt(sender)));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.notInParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("leave")) {
                if (PartyUtils.hasPartyPermission(sender, "leave")) {
                    if (PartyUtils.isInParty(sender)) {
                        PartyUtils.removeSenderFromParty(sender);
                    } else sender.sendMessage(ConfigUtils.getMessage("party.notInParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("toggle")) {
                if (PartyUtils.hasPartyPermission(sender, "toggle")) {
                    if(isInParty(sender)) {
                        if (!PartyHolder.isActivePartyChat(sender)) {
                            PartyHolder.setActivePartyChat(sender);
                            sender.sendMessage(ConfigUtils.getMessage("party.toggle.on"));
                        } else {
                            PartyHolder.removeActivePartyChat(sender);
                            sender.sendMessage(ConfigUtils.getMessage("party.toggle.off"));
                        }
                    } else sender.sendMessage(ConfigUtils.getMessage("party.notInParty"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }

            else if (args[0].equalsIgnoreCase("accept")) {
                if (PartyUtils.hasPartyPermission(sender, "accept")) {
                    if(PartyHolder.isActiveInvite(sender.getName())) {
                        PartyUtils.addPlayerToParty(sender, PartyHolder.getPartyId(sender.getName()));
                    } else sender.sendMessage(ConfigUtils.getMessage("party.innactiveInvite"));
                } else sender.sendMessage(ConfigUtils.getMessage("party.withoutPermission"));
            }
            else if (PartyUtils.isInParty(sender)) {
                PartyUtils.sendMessageToParty(sender, FormatUtils.argBuilder(args, 0));
            } else sender.sendMessage(ConfigUtils.getMessage("party.notInParty"));
        }
        return false;
    }

}
