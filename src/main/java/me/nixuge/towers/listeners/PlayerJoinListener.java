package me.nixuge.towers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import lombok.val;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        val p = event.getPlayer();

        TowersPlayer returningPlayer = PlayersManager.getPlayer(p);
        if (returningPlayer != null) {
            event.setJoinMessage("Player logged back on.");
            returningPlayer.logOn(p);
            returningPlayer.getTeam().getTeamMap().getSpawn();
            return;
        }

        // Note: all player names should be in the args, as to not cause confusion & to make spec mode easier.
        event.setJoinMessage(null);
        PlayersManager.addPlayerRandomTeam(p);
    }
}
