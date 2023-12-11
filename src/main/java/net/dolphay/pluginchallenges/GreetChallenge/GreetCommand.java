package net.dolphay.pluginchallenges.GreetChallenge;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class GreetCommand implements CommandExecutor {
    YamlConfiguration config;
    GreetEventHandler eventHandler;

    public GreetCommand(YamlConfiguration config, GreetEventHandler eventHandler) {
        this.config = config;
        this.eventHandler = eventHandler;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String alias, String[] args) {
        // Player to send the greeting to
        Player player;

        // If args.length == 0 the player has not specified someone to greet, so we will use the newest player.
        if(args.length == 0) {
            player = eventHandler.getNewestPlayer();
        }
        // If they have defined a player, see if it is a valid player
        else if(Bukkit.getPlayer(args[0]) != null) {
            player = Bukkit.getPlayer(args[0]);
        }
        // If we cannot find the given player, send error.
        else {
            commandSender.sendMessage("Unknown player: " + args[0]);
            return false;
        }

        // If we have found a player, send the greeting to them.

        player.sendMessage("Welcome to " + config.getString("server-name") + "!");
        commandSender.sendMessage("Sent " + player.getDisplayName() + " a greeting!");

        return true;
    }
}
