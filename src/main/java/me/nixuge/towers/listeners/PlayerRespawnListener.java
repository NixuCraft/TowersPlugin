package me.nixuge.towers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.nixuge.towers.Towers;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;
import me.nixuge.towers.teams.TowersTeam;

public class PlayerRespawnListener implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player p = event.getPlayer();

        TowersPlayer towersP = PlayersManager.getPlayer(p);

        World w = Bukkit.getWorld("world");
        boolean isBlue = (towersP.getTeam() == TowersTeam.BLUE);

        Location respawnLoc = (isBlue) ? new Location(w, 75.5, 192, 0.5, 90, 0) : new Location(w, -74.5, 192, 0.5, -90, 0);

        event.setRespawnLocation(respawnLoc);

        Bukkit.getScheduler().runTaskLater(Towers.getInstance(), new Runnable(){
            public void run(){
                // Note sure if this *needs* to be in a runnable:
                p.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 100, 1));
                p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
                // This needs to be un a runnable tho:
                // TODO: GIVE STUFF !
            }
        }, 2);
    }
}
