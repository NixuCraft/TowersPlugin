package me.nixuge.towers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        // note: not sure what there is to do here?
        // original does this:
        // if (Blitz.currentgame.stream().anyMatch(game -> Objects.equals(game.gamestatus, "running") && game.allplayers.contains(eventer)) || eventer.getGameMode().name().equals("CREATIVE")) {
        //     return;
        // };
      
        // Player tmpplayer = (Player) eventer;
        // tmpplayer.getPlayer().setFoodLevel(20);
      
        // event.setCancelled(true);
    }
}
