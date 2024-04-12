package me.nixuge.towers.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import me.nixuge.towers.world.PreventedRegions;

public class EntityExplodeListener implements Listener {
    @EventHandler
    public void onEntityExplode(EntityExplodeEvent event) {
        event.blockList().removeIf(block -> PreventedRegions.canBreakBlock(block));
    }
}
