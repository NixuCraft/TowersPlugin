package me.nixuge.towers.player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import lombok.val;
import me.nixuge.towers.teams.TowersTeam;

public class PlayersManager {
    private static HashMap<UUID, TowersPlayer> uuidToTowersPlayer;
    static {
        uuidToTowersPlayer = new HashMap<>();
    }
    // Note: this jank function is unsed until I complete the lobby to send in arguments
    // for the server launch.
    public static TowersPlayer addPlayerRandomTeam(Player p) {
        Collection<TowersPlayer> towersPlayers = uuidToTowersPlayer.values();
        int towerPlayerCount = towersPlayers.size();

        int blueTeamPlayers = (int)towersPlayers.stream().filter(player -> player.getTeam() == TowersTeam.BLUE).count();
        int redTeamPlayers = towerPlayerCount - blueTeamPlayers;

        // Choosing red before blue.
        TowersTeam choosenTeam = (blueTeamPlayers >= redTeamPlayers) ? TowersTeam.RED : TowersTeam.BLUE;
        
        return addPlayer(p, choosenTeam);
    }
    private static TowersPlayer addPlayer(Player p, TowersTeam team) {
        val newP = new TowersPlayer(p, team);
        uuidToTowersPlayer.put(p.getUniqueId(), newP);

        // Player init tasks here - not sure if they should be moved.
        p.teleport(team.getTeamMap().getSpawn());
        p.setGameMode(GameMode.SURVIVAL);

        return newP;
    }

    public static int countOnlinePlayersOnTeam(TowersTeam team) {
        return (int)uuidToTowersPlayer.values().stream().filter(player -> player.isOn() && player.getTeam() == TowersTeam.BLUE).count();
    }
    public static Collection<TowersPlayer> getAllPlayers() {
        return uuidToTowersPlayer.values();
    }

    public static TowersPlayer getPlayer(PlayerEvent event) {
        return getPlayer(event.getPlayer());
    }
    public static TowersPlayer getPlayer(Player p) {
        return uuidToTowersPlayer.get(p.getUniqueId());
    }
}
