package me.nixuge.towers.runnable;

import org.bukkit.scheduler.BukkitRunnable;

import me.nixuge.towers.scoreboard.ScoreboardSidebar;

public class TowerRunnable extends BukkitRunnable {
    @Override
    public void run() {
        ScoreboardSidebar.updateSidebars();
    }
}
