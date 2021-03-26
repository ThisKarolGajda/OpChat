package me.opkarol.opchat.msg;

import me.opkarol.opchat.PluginController;
import me.opkarol.opchat.utils.ConfigUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class Ignore implements CommandExecutor {
    public static HashMap<Player, ArrayList<Player>> ignore = new HashMap<>();
    //                    Sender  PlayersIgnored

    public Ignore(PluginController chatSystem){ }

    @Override
    public boolean onCommand(CommandSender Sender, Command cmd, String label, String[] args) {
        Player sender = (Player) Sender;
        if (args.length != 0) {
            Player playerToIgnore = Bukkit.getPlayer(args[0]);
            if(sender==playerToIgnore){
                sender.sendMessage(ConfigUtils.getString("ignore.cannotYourself"));
                return false;
            }
            if (playerToIgnore != null) {
                ArrayList<Player> list;
                list = new ArrayList<>();

                if (!ignore.containsKey(sender)) {
                    list.add(playerToIgnore);
                    ignore.put(sender, list);
                    sender.sendMessage(addedPlayer(playerToIgnore));
                    return true;
                }
                if (!ignore.get(sender).contains(playerToIgnore)) {
                    list.add(playerToIgnore);
                    sender.sendMessage(addedPlayer(playerToIgnore));
                    ignore.put(sender, list);
                    return true;
                } else {
                    sender.sendMessage(removedPlayer(playerToIgnore));
                    list.remove(playerToIgnore);
                    ignore.put(sender, list);
                    return true;
                }

            } else {
                sender.sendMessage(ConfigUtils.getString("ignore.isntOnline"));
            }
        } else {
            sender.sendMessage(ConfigUtils.getString("ignore.badUsage"));
        }

        return true;
    }

    private String addedPlayer(Player player){
        return ConfigUtils.getString("ignore.added").replace("%player%", player.getName());
    }

    private String removedPlayer(Player player){
        return ConfigUtils.getString("ignore.removed").replace("%player%", player.getName());
    }

}
