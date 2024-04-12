package me.nixuge.towers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;

public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        TowersPlayer towerP = PlayersManager.getPlayer(event);
        towerP.logOff();
        if (PlayersManager.countOnlinePlayersOnTeam(towerP.getTeam()) == 0) {
            // TODO: ANNOUNCE WIN (or not?)
        }
            
    }
}
