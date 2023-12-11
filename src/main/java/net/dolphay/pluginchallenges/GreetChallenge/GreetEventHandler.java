package net.dolphay.pluginchallenges.GreetChallenge;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GreetEventHandler implements Listener {
    private Player newestPlayer;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        this.newestPlayer = event.getPlayer();
    }

    public Player getNewestPlayer() {
        return newestPlayer;
    }
}
