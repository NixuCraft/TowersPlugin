package me.nixuge.towers.teams;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import lombok.Setter;
import me.nixuge.configurator.maths.Area;
import me.nixuge.towers.Towers;

public class TeamPoints {
    // private static final int MAX_POINTS = 3; // TODO: LOAD FROM CONFIG OR SOMEWHERE
    private static final int MAX_POINTS = 1; // TODO: LOAD FROM CONFIG OR SOMEWHERE
    private static final int SECONDS_BETWEEN_POINTS = 30;
    private static final World world = Bukkit.getWorld("world");
    
    @Setter
    private TowersTeam team;

    private long lastPointTime;
    private int points;

    public TeamPoints() {
        // this.lastPointTime = System.currentTimeMillis(); // Not sure if I should keep that cooldown, technically not even possible legit.
        this.lastPointTime = 0;
        this.points = 0;
    }

    public int getSecondsBeforeNewPoint() {
        return SECONDS_BETWEEN_POINTS - (int)((System.currentTimeMillis() - this.lastPointTime) / 1000);
    }

    public boolean scorePoint() {
        // Just making sure, in case someone wants to usebug in.
        if (getSecondsBeforeNewPoint() > 0)
            return false;
        
        this.points++;
        if (this.points >= MAX_POINTS) {
            Towers.getGameManager().endGame(team);
            return true;
        }

        Area goalArea = team.getTeamMap().getGoalArea();
        goalArea.fill(world, Material.BEDROCK);
        Bukkit.getScheduler().runTaskLater(Towers.getInstance(), () -> {
            goalArea.fill(world, Material.AIR);
        }, SECONDS_BETWEEN_POINTS);

        return true;
    }
}
