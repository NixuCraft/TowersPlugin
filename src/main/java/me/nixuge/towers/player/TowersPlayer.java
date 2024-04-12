package me.nixuge.towers.player;

import org.bukkit.entity.Player;

import lombok.Getter;
import me.nixuge.towers.teams.TowersTeam;

public class TowersPlayer {
    @Getter
    private final TowersTeam team;
    @Getter
    private Player bukkitPlayer;
    
    private long logOffTime;

    public TowersPlayer(Player p, TowersTeam t) {
        this.team = t;
        this.bukkitPlayer = p;
        this.logOffTime = -1;
    }

    public String getColouredName() {
        return team.getChatPrefix() + bukkitPlayer.getName() + "§r";
    }

    public boolean isOn() {
        return logOffTime == -1;
    }

    public void logOff() {
        this.logOffTime = System.currentTimeMillis();
    }
    public void logOn(Player p) {
        this.logOffTime = -1;
        this.bukkitPlayer = p;
    }
}
