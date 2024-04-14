package me.nixuge.towers.teams;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;

import lombok.Getter;
import lombok.Setter;
import me.nixuge.nixutils.maths.Area;
import me.nixuge.towers.Towers;
import me.nixuge.towers.scoreboard.ScoreboardSidebar;

public class TeamPoints {
    public enum ScorePointResult {
        SUCCESS,
        WAIT,
        MAX_POINTS
    }
    @Getter
    private static final int MAX_POINTS = 3; // TODO: LOAD FROM CONFIG OR SOMEWHERE
    // private static final int MAX_POINTS = 1; // TODO: LOAD FROM CONFIG OR SOMEWHERE
    private static final int SECONDS_BETWEEN_POINTS = 30;
    private static final World world = Bukkit.getWorld("world");
    
    @Setter
    private TowersTeam team;

    private long lastPointTime;
    @Getter
    private int points;

    public TeamPoints() {
        // this.lastPointTime = System.currentTimeMillis(); // Not sure if I should keep that cooldown, technically not even possible legit.
        this.lastPointTime = 0;
        this.points = 0;
    }

    public int getSecondsBeforeNewPoint() {
        return SECONDS_BETWEEN_POINTS - (int)((System.currentTimeMillis() - this.lastPointTime) / 1000);
    }

    public ScorePointResult scorePoint() {
        // Just making sure, in case someone wants to usebug in.
        if (getSecondsBeforeNewPoint() > 0)
            return ScorePointResult.WAIT;
        
        this.points++;
        ScoreboardSidebar.updateSidebars();
        team.getEnemyTeam().getArmorStand().updateName();
        if (this.points >= MAX_POINTS) {
            return ScorePointResult.MAX_POINTS;
        }

        Area goalArea = team.getTeamMap().getGoalArea();
        goalArea.fill(world, Material.BEDROCK);
        Bukkit.getScheduler().runTaskLater(Towers.getInstance(), () -> {
            goalArea.fill(world, Material.AIR);
        }, SECONDS_BETWEEN_POINTS);

        return ScorePointResult.SUCCESS;
    }
}
