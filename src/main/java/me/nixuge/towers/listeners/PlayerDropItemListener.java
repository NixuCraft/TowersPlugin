package me.nixuge.towers.listeners;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {
    private static List<Material> noDrop = Arrays.asList(
        Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET
    );
    
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        if (noDrop.contains(event.getItemDrop().getItemStack().getType())) {
            event.setCancelled(true);
            return;
        }
    }
}
