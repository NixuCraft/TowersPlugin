package me.nixuge.towers.game;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import com.destroystokyo.paper.Title;

import me.nixuge.towers.Towers;
import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.teams.TowersTeam;
import me.nixuge.towers.utils.TextUtils;
import me.nixuge.towers.world.IronGenerator;
import me.nixuge.velocityhandler.messages.PlayerSwitchServerMessage;
import me.nixuge.velocityhandler.messages.ShutdownSelfMessage;

public class GameManager {
    private Towers plugin;
    
    private long startTime;

    public GameManager(Towers plugin) {
        this.plugin = plugin;
    }

    public String getFormattedGameTime() {
        return TextUtils.millisecondsToMMSSms(System.currentTimeMillis() - startTime);
    }

    public void startGame() {
        this.startTime = System.currentTimeMillis();
        new IronGenerator().runTaskTimer(plugin, 60, 60);
    }
    
    public void endGame() {
        PlayersManager.getAllPlayers().forEach(towersP -> {
            Player p = towersP.getBukkitPlayer();
            p.sendTitle(new Title("Game ended in a draw."));
            p.sendMessage("Game ended in a draw.");
        });
        afterGameEnd();
    }

    public void endGame(TowersTeam team) {
        PlayersManager.getAllPlayers().forEach(towersP -> {
            Player p = towersP.getBukkitPlayer();
            String winnerStr = team.getDisplayString() + " team won.";
            p.sendTitle(new Title(winnerStr));
            p.sendMessage(winnerStr);
        });
        afterGameEnd();
    }

    private void afterGameEnd() {
        HandlerList.unregisterAll();
        PlayersManager.getAllPlayers().forEach(towersP -> {
            Player p = towersP.getBukkitPlayer();
            p.setGameMode(GameMode.SPECTATOR);
            p.getInventory().clear();
            p.sendMessage("Game took " + getFormattedGameTime());
        });


        // IMPORTANT NOTE: IF ALL PLAYERS LEFT, THIS MEANS THIS WON'T WORK !
        // THIS CAN MAKE SERVERS KEEP RUNNING FOR NO REASON !
        // COULD USE THE HTTP API DIRECTLY TO BE GOOD.
        new ShutdownSelfMessage(20).sendMessage();

        Bukkit.getServer().getScheduler().runTaskLater(Towers.getInstance(), () -> {
            new PlayerSwitchServerMessage("lobby").sendMessage();
        }, 300); // 15s
    }
}
