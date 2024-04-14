package me.nixuge.towers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import me.nixuge.towers.player.PlayersManager;
import me.nixuge.towers.player.TowersPlayer;
import me.nixuge.towers.world.PreventedRegions;

public class BlockPlaceBreakListener implements Listener {
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        TowersPlayer towersP = PlayersManager.getPlayer(event.getPlayer());
        if (!PreventedRegions.canPlayerBreakBlock(event.getBlock(), towersP)) {
            event.setCancelled(true);
            return;
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        TowersPlayer towersP = PlayersManager.getPlayer(event.getPlayer());
        if (!PreventedRegions.canPlayerBreakBlock(event.getBlock(), towersP)) {
            event.setCancelled(true);
            return;
        }
    }
}
