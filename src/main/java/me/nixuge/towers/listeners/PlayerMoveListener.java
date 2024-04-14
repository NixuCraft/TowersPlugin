package me.nixuge.towers.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerMoveEvent;

import me.nixuge.nixutils.maths.Area;
import me.nixuge.towers.Towers;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;
import me.nixuge.towers.teams.TeamPoints;
import me.nixuge.towers.teams.TowersTeam;
import me.nixuge.towers.teams.TeamPoints.ScorePointResult;

public class PlayerMoveListener implements Listener {
    // TODO?: bound checks
    // Note: I really dislike using deprecated methods when there are alternatives available,
    // BUT here it's used for the new EntityDamageEvent and the deprecated constructors are MUCH
    // simpler than the non deprecated one, + this plugin is probably going to be 1.9.4 only.
    @SuppressWarnings("deprecation") 
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player p = event.getPlayer();
        TowersPlayer towerP = PlayersManager.getPlayer(p);

        Location to = event.getTo();
        if (to.getY() < 120) { // Bumped the limit a bit, was originally at 85. Might put in config.
            p.setLastDamageCause(new EntityDamageEvent(p, DamageCause.FALL, 20));
            p.setHealth(0);
            // If this isn't there, we may send a few more move packets and trigger this multiple times.
            // Just teleporting before the death/respawn to avoid that.
            // Edit: this does not fix it lmao, for now leaving it like that.
            p.teleport(towerP.getTeam().getTeamMap().getSpawn());
            return;
        }

        
        TowersTeam team = towerP.getTeam();

        Area enemyGoalArea = team.getEnemyTeam().getTeamMap().getGoalArea();
        if (!enemyGoalArea.containsBlock(to))
            return;

        TeamPoints teamPoints = team.getTeamPoints();
        ScorePointResult pointRes = teamPoints.scorePoint();
        switch (pointRes) {
            case WAIT:
                p.sendMessage("You stil need to wait " + teamPoints.getSecondsBeforeNewPoint() + "s before scoring again.");
                p.teleport(p.getLocation().add(0, 1, 0)); // Tp 1 block up to unstuck from the bedrock.
                return;
            case SUCCESS:
                Bukkit.broadcastMessage(towerP.getColouredName() + " scored a point.");
                p.teleport(team.getTeamMap().getSpawn());
                return;
            case MAX_POINTS:
                Bukkit.broadcastMessage(towerP.getColouredName() + " scored a point.");
                Towers.getGameManager().endGame(team);
                return;
        }
    }
}
