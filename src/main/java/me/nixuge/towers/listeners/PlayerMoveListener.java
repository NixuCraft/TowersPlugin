package me.nixuge.towers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import me.nixuge.configurator.maths.Area;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;
import me.nixuge.towers.teams.TeamPoints;
import me.nixuge.towers.teams.TowersTeam;

public class PlayerMoveListener implements Listener {
    // TODO?: bound checks
    // Note: I really dislike using deprecated methods when there are alternatives available,
    // BUT here it's used for the new EntityDamageEvent and the deprecated constructors are MUCH
    // simpler than the non deprecated one, + this plugin is probably going to be 1.9.4 only.
    @SuppressWarnings("deprecation") 
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();

        Location to = event.getTo();
        if (to.getY() < 120) { // Bumped the limit a bit, was originally at 85. Might put in config.
            p.setLastDamageCause(new EntityDamageEvent(p, DamageCause.FALL, 20));
            p.setHealth(0);
        }

        TowersPlayer towerP = PlayersManager.getPlayer(p);
        TowersTeam team = towerP.getTeam();

        Area enemyGoalArea = team.getEnemyTeam().getTeamMap().getGoalArea();
        if (!enemyGoalArea.containsBlock(to))
            return;

        TeamPoints teamPoints = team.getTeamPoints();
        if (!teamPoints.scorePoint()) {
            p.sendMessage("You stil need to wait " + teamPoints.getSecondsBeforeNewPoint() + "s before scoring again.");
            p.teleport(p.getLocation().add(0, 1, 0)); // Tp 1 block up to unstuck from the bedrock.
            return;
        }

        p.teleport(team.getTeamMap().getSpawn());
        Bukkit.broadcastMessage(towerP.getColouredName() + " scored a point.");
    }
}
