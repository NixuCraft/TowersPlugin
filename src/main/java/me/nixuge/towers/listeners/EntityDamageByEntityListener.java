package me.nixuge.towers.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getEntityType() != EntityType.PLAYER || event.getDamager().getType() != EntityType.PLAYER)
            return;
        
        Player victim = (Player)event.getEntity();
        Player hitman = (Player)event.getDamager();
        TowersPlayer towersVictim = PlayersManager.getPlayer(victim);
        TowersPlayer towersHitman = PlayersManager.getPlayer(hitman);

        if (towersVictim.getTeam() == towersHitman.getTeam()) {
            event.setCancelled(true);
            return;
        }
    }
}

