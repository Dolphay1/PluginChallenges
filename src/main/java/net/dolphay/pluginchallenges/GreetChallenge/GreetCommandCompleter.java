package net.dolphay.pluginchallenges.GreetChallenge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GreetCommandCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String alias, String[] args) {
        List<String> players = new ArrayList<>();

        // Create a player iterator
        Iterator iterator = Bukkit.getOnlinePlayers().iterator();

        // Loop through each player and add their username to the arraylist
        while (iterator.hasNext()) {
            players.add(((Player)iterator.next()).getName());
        }

        // Return the players that match the beginning of the input.
        return StringUtil.copyPartialMatches(args[0], players, new ArrayList<>());
    }
}
