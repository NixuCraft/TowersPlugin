package me.nixuge.towers.player;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import lombok.Getter;
import me.nixuge.nixutils.item.ItemBuilder;
import me.nixuge.towers.scoreboard.ScoreboardSidebar;
import me.nixuge.towers.teams.TowersTeam;

public class TowersPlayer {
    private static final Map<Integer, ItemStack> respawnItems = new HashMap<>();
    static {
        respawnItems.put(0, new ItemStack(Material.WOOD_SWORD));
        respawnItems.put(1, new ItemStack(Material.WOOD_AXE));
        respawnItems.put(2, new ItemStack(Material.BAKED_POTATO, 12));
        respawnItems.put(3, new ItemBuilder(Material.STONE_PICKAXE).durability(111).build());
    }

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

    public void init() {
        bukkitPlayer.teleport(team.getTeamMap().getSpawn());
        bukkitPlayer.setGameMode(GameMode.SURVIVAL);
        bukkitPlayer.setFoodLevel(20);
        ScoreboardSidebar.addSidebarToPlayer(bukkitPlayer, team);
        giveStuff();
    }

    public void giveStuff() {
        PlayerInventory inv = bukkitPlayer.getInventory();
        inv.setArmorContents(team.getArmor());
        for (Entry<Integer, ItemStack> itemEntry : respawnItems.entrySet()) {
            inv.setItem(itemEntry.getKey(), itemEntry.getValue());
        }
    }
}
